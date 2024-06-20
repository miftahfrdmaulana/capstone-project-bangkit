package com.dicoding.gardenguard.ui.disease

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.gardenguard.data.model.Disease
import com.dicoding.gardenguard.databinding.ActivityDiseaseBinding
import com.dicoding.gardenguard.ui.adapter.DiseaseAdapter

class DiseaseActivity : AppCompatActivity() {
    private val binding: ActivityDiseaseBinding by lazy {
        ActivityDiseaseBinding.inflate(layoutInflater)
    }

    private lateinit var diseaseAdapter: DiseaseAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        supportActionBar?.hide()

        val (title, diseases) = getIntentData()
        setupTitle(title)
        setupRecyclerView(diseases)
    }

    // Function to get data from the intent
    private fun getIntentData(): Pair<String, ArrayList<Disease>> {
        val fruitName = intent.getStringExtra("FRUIT_NAME")
        val vegetableName = intent.getStringExtra("VEGETABLE_NAME")
        val diseases = intent.getParcelableArrayListExtra<Disease>("DISEASES") ?: arrayListOf()

        val title = when {
            !fruitName.isNullOrBlank() -> "List Penyakit Tanaman $fruitName"
            !vegetableName.isNullOrBlank() -> "List Penyakit Tanaman $vegetableName"
            else -> "List Penyakit Tanaman"
        }

        return Pair(title, diseases)
    }

    // Function to set the title of the activity
    private fun setupTitle(title: String) {
        binding.tvDiseaseTitle.text = title
    }

    // Function to setup RecyclerView
    private fun setupRecyclerView(diseases: ArrayList<Disease>) {
        val recyclerView: RecyclerView = binding.rvDisease
        recyclerView.layoutManager = LinearLayoutManager(this)

        diseaseAdapter = DiseaseAdapter(this, diseases) { disease ->
            navigateToDiseaseDetail(disease)
        }
        recyclerView.adapter = diseaseAdapter
    }

    // Function to navigate to DiseaseDetailActivity
    private fun navigateToDiseaseDetail(disease: Disease) {
        val fruitName = intent.getStringExtra("FRUIT_NAME")
        val vegetableName = intent.getStringExtra("VEGETABLE_NAME")

        val intent = Intent(this, DiseaseDetailActivity::class.java).apply {
            putExtra("FRUIT_NAME", fruitName)
            putExtra("VEGETABLE_NAME", vegetableName)
            putExtra("DISEASE_NAME", disease.scientificName)
            putExtra("DISEASE_IMAGE_URL", disease.imageUrl)
        }
        startActivity(intent)
    }
}