package com.dicoding.gardenguard.ui.disease

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import androidx.lifecycle.LiveData
import com.bumptech.glide.Glide
import com.dicoding.gardenguard.ViewModelFactory
import com.dicoding.gardenguard.data.Result
import com.dicoding.gardenguard.data.response.DiseaseResponse
import com.dicoding.gardenguard.databinding.ActivityDiseaseDetailBinding

class DiseaseDetailActivity : AppCompatActivity() {
    private val viewModel by viewModels<DiseaseDetailViewModel> {
        ViewModelFactory.getInstance(this)
    }

    private lateinit var binding: ActivityDiseaseDetailBinding

    private lateinit var diseaseImageUrl : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDiseaseDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        val fruitName = intent.getStringExtra("FRUIT_NAME")?.lowercase()
        val vegetableName = intent.getStringExtra("VEGETABLE_NAME")?.lowercase()
        val diseaseName = intent.getStringExtra("DISEASE_NAME")
        diseaseImageUrl = intent.getStringExtra("DISEASE_IMAGE_URL").toString()

        when {
            !fruitName.isNullOrBlank() -> observeDiseaseData(viewModel.getFruitDisease(fruitName, diseaseName!!))
            !vegetableName.isNullOrBlank() -> observeDiseaseData(viewModel.getVegetableDisease(vegetableName, diseaseName!!))
        }
    }

    // Function to observe disease data and update the UI
    private fun observeDiseaseData(liveData: LiveData<Result<DiseaseResponse>>) {
        liveData.observe(this) { result ->
            when (result) {
                is Result.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
                is Result.Success -> {
                    binding.progressBar.visibility = View.GONE
                    updateUIWithDiseaseData(result.data)
                }
                is Result.Error -> {
                    binding.progressBar.visibility = View.GONE
                    showError(result.error)
                }
            }
        }
    }

    // Function to update the UI with disease data
    private fun updateUIWithDiseaseData(data: DiseaseResponse) {
        binding.apply {
            tvPlantName.text = data.tanaman
            tvDiseaseName.text = data.penyakit
            tvDiseaseScientificName.text = data.namaIlmiahPenyakit
            tvTreatment.text = data.pengobatan
            Glide.with(this@DiseaseDetailActivity)
                .load(diseaseImageUrl)
                .into(ivDiseaseImage)
        }
    }

    // Function to show error message
    private fun showError(message: String?) {
        // Handle showing error message, you can add a Toast or Snackbar
        Toast.makeText(this, message ?: "An error occurred", Toast.LENGTH_SHORT).show()
    }
}