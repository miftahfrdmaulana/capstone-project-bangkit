package com.dicoding.gardenguard.ui.diagnose

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import com.dicoding.gardenguard.data.response.DiagnoseResponse
import com.dicoding.gardenguard.databinding.ActivityResultBinding
import com.dicoding.gardenguard.ui.MainActivity

class ResultActivity : AppCompatActivity() {
    private lateinit var binding: ActivityResultBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        val result = intent.getParcelableExtra<DiagnoseResponse>(RESULT)
        val uri = intent.getStringExtra(URI)
        if (result != null && uri != null) {
            with(binding) {
                tvPenyakit.text = result.penyakit
                tvLatin.text = result.namaIlmiahPenyakit
                textView2.text = result.pengobatan
                imageView3.setImageURI(uri.toUri())
            }
        }

        binding.button.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
            finish()
        }
    }

    companion object {
        const val RESULT = "result"
        const val URI = "uri"
    }
}