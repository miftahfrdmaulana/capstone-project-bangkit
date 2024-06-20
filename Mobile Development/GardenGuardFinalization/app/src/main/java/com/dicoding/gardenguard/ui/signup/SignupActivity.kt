package com.dicoding.gardenguard.ui.signup

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.gardenguard.R
import com.dicoding.gardenguard.ViewModelFactory
import com.dicoding.gardenguard.data.Result
import com.dicoding.gardenguard.databinding.ActivitySignupBinding
import com.dicoding.gardenguard.ui.signin.LoginActivity

class SignupActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignupBinding

    private val viewModel by viewModels<SignupViewModel> {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupView()
        setupAction()
        playAnimation()
    }

    private fun setupAction() {
        with(binding) {
            signupButton.setOnClickListener {
                val username = edRegisterName.text.toString()
                val email = edRegisterEmail.text.toString()
                val password = edRegisterPassword.text.toString()

                viewModel.register(username, email, password).observe(this@SignupActivity) {
                    if (it != null) {
                        when (it) {
                            is Result.Loading -> {
                                progressBarSignUp.visibility = View.VISIBLE
                            }

                            is Result.Success -> {
                                progressBarSignUp.visibility = View.GONE

                                AlertDialog.Builder(this@SignupActivity).apply {
                                    setTitle(getString(R.string.berhasil_daftar))
                                    setMessage(getString(R.string.silahkan_login))
                                    setPositiveButton(getString(R.string.Login)) { _, _ ->
                                        val intent =
                                            Intent(this@SignupActivity, LoginActivity::class.java)
                                        intent.flags
                                        Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                                        startActivity(intent)
                                        finish()
                                    }
                                    create()
                                    show()
                                }
                            }

                            is Result.Error -> {
                                progressBarSignUp.visibility = View.GONE
                                AlertDialog.Builder(this@SignupActivity).apply {
                                    setTitle(getString(R.string.Registen_gagal))
                                    setMessage(it.error)
                                    setPositiveButton(getString(R.string.kembali)) { _, _ ->
                                    }
                                    create()
                                    show()
                                }
                            }
                        }
                    }
                }
            }


        }


    }

    private fun setupView() {
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
        supportActionBar?.hide()
    }

    private fun playAnimation() {
        ObjectAnimator.ofFloat(binding.imageView, View.TRANSLATION_X, -30f, 30f).apply {
            duration = 6000
            repeatCount = ObjectAnimator.INFINITE
            repeatMode = ObjectAnimator.REVERSE
        }.start()

        val login = ObjectAnimator.ofFloat(binding.signupButton, View.ALPHA, 2f).setDuration(300)
        val nameTv = ObjectAnimator.ofFloat(binding.nameTextView, View.ALPHA, 2f).setDuration(300)
        val nameET =
            ObjectAnimator.ofFloat(binding.nameEditTextLayout, View.ALPHA, 2f).setDuration(300)
        val emailTv = ObjectAnimator.ofFloat(binding.emailTextView, View.ALPHA, 2f).setDuration(300)
        val title = ObjectAnimator.ofFloat(binding.titleTextView, View.ALPHA, 2f).setDuration(300)
        val passwordTv =
            ObjectAnimator.ofFloat(binding.passwordTextView, View.ALPHA, 2f).setDuration(300)
        val emailEt =
            ObjectAnimator.ofFloat(binding.emailEditTextLayout, View.ALPHA, 2f).setDuration(300)
        val passwordEt =
            ObjectAnimator.ofFloat(binding.passwordEditTextLayout, View.ALPHA, 2f).setDuration(300)


        AnimatorSet().apply {
            playSequentially(title, nameTv, nameET, emailTv, emailEt, passwordTv, passwordEt, login)
            start()
        }
    }
}