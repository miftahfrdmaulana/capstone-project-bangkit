package com.dicoding.gardenguard.ui.diagnose

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.dicoding.gardenguard.R
import com.dicoding.gardenguard.ViewModelFactory
import com.dicoding.gardenguard.data.Result
import com.dicoding.gardenguard.databinding.ActivityUploadImageBinding
import com.dicoding.gardenguard.ui.MainActivity
import com.dicoding.gardenguard.ui.diagnose.ResultActivity.Companion.RESULT
import com.dicoding.gardenguard.ui.diagnose.ResultActivity.Companion.URI
import com.dicoding.gardenguard.utils.getImageUri
import com.dicoding.gardenguard.utils.reduceFileImage
import com.dicoding.gardenguard.utils.uriToFile

class UploadImageActivity : AppCompatActivity() {
    private val viewModel by viewModels<UploadImageViewModel> {
        ViewModelFactory.getInstance(application)
    }
    private lateinit var binding: ActivityUploadImageBinding
    private var currentImageUri: Uri? = null
    private lateinit var fruitName: String
    private lateinit var vegetableName: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUploadImageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        fruitName = intent.getStringExtra("FRUIT_NAME") ?: ""
        vegetableName = intent.getStringExtra("VEGETABLE_NAME") ?: ""

        binding.galleryButton.setOnClickListener { startGallery() }
        binding.cameraButton.setOnClickListener { checkCameraPermissionAndStartCamera() }
        binding.diagnoseButton.setOnClickListener { diagnoseDisease() }
    }

    private fun startCamera() {
        currentImageUri = getImageUri(this)
        launcherIntentCamera.launch(currentImageUri!!)
    }

    private val requestCameraPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            startCamera()
        } else {
            showToast("Camera permission is required to take pictures")
        }
    }

    private fun checkCameraPermissionAndStartCamera() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            startCamera()
        } else {
            requestCameraPermissionLauncher.launch(Manifest.permission.CAMERA)
        }
    }

    private val launcherIntentCamera = registerForActivityResult(
        ActivityResultContracts.TakePicture()
    ) { isSuccess ->
        if (isSuccess) {
            showImage()
        }
    }

    private fun startGallery() {
        launcherGallery.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
    }

    private val launcherGallery = registerForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ) { uri: Uri? ->
        if (uri != null) {
            currentImageUri = uri
            showImage()
        } else {
            Log.d("Photo Picker", "No media selected")
        }
    }

    private fun showImage() {
        currentImageUri?.let {
            binding.previewImageView.setImageURI(it)
        }
    }

    private fun diagnoseDisease() {
        currentImageUri?.let { uri ->
            val imageFile = uriToFile(uri, this).reduceFileImage()

            viewModel.diagnoseDisease(imageFile, setFruitOrVegetableName(fruitName,vegetableName)).observe(this) { result ->
                if (result != null) {
                    when (result) {
                        is Result.Loading -> {
//                            showLoading(true)
                        }

                        is Result.Success -> {
                            showToast("Success")
//                            showLoading(false)



                            val intent = Intent(this, ResultActivity::class.java)
                            intent.putExtra(RESULT, result.data)
                            intent.putExtra(URI, uri.toString())
                            startActivity(intent)
                        }

                        is Result.Error -> {
                            showToast(result.error)
//                            showLoading(false)
                        }
                    }
                }
            }
        } ?: showToast(getString(R.string.empty_image_warning))

        startActivity(Intent(this, ResultActivity::class.java))
    }

    private fun setFruitOrVegetableName(fruitName: String, vegetableName: String): String {
        return fruitName.ifEmpty { vegetableName }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    companion object {
        private const val TAG = "UploadImageActivity"
    }
}
