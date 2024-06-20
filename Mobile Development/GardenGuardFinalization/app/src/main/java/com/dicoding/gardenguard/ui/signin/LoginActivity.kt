package com.dicoding.gardenguard.ui.signin

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
import com.dicoding.gardenguard.databinding.ActivityLoginBinding
import com.dicoding.gardenguard.ui.MainActivity

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding

    private val viewModel by viewModels<LoginViewModel> {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)


        setupView()
        setupAction()
        playAnimation()
    }

    private fun playAnimation() {
        ObjectAnimator.ofFloat(binding.imageView, View.TRANSLATION_X, -30f, 30f).apply {
            duration = 6000
            repeatCount = ObjectAnimator.INFINITE
            repeatMode = ObjectAnimator.REVERSE
        }.start()

        val login = ObjectAnimator.ofFloat(binding.loginButton, View.ALPHA, 2f).setDuration(300)
        val userTv = ObjectAnimator.ofFloat(binding.usernameTextView, View.ALPHA, 2f).setDuration(300)
        val title = ObjectAnimator.ofFloat(binding.titleTextView, View.ALPHA, 2f).setDuration(300)
        val passwordTv =
            ObjectAnimator.ofFloat(binding.passwordTextView, View.ALPHA, 2f).setDuration(300)
        val userEt =
            ObjectAnimator.ofFloat(binding.usernameEditTextLayout, View.ALPHA, 2f).setDuration(300)
        val passwordEt =
            ObjectAnimator.ofFloat(binding.passwordEditTextLayout, View.ALPHA, 2f).setDuration(300)


        AnimatorSet().apply {
            playSequentially(title, userTv, userEt, passwordTv, passwordEt, login)
            start()
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

    private fun setupAction() {
        with(binding) {
            loginButton.setOnClickListener {
                val username = edLoginUser.text.toString()
                val password = edLoginPassword.text.toString()

                viewModel.login(username, password).observe(this@LoginActivity) {
                    if (it != null) {
                        when (it) {
                            is Result.Loading -> {
                                progressBarLogin.visibility = View.VISIBLE
                            }

                            is Result.Success -> {
                                progressBarLogin.visibility = View.GONE
                                viewModel.saveTokenSession(it.data.token)

                                AlertDialog.Builder(this@LoginActivity).apply {
                                    setTitle(getString(R.string.selamat_datang))
                                    setMessage(getString(R.string.login_berhasil))
                                    setPositiveButton(getString(R.string.masuk)) { _, _ ->
                                        val intent =
                                            Intent(this@LoginActivity, MainActivity::class.java)
                                        intent.flags =
                                            Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                                        startActivity(intent)
                                        finish()
                                    }
                                    create()
                                    show()
                                }
                            }

                            is Result.Error -> {
                                progressBarLogin.visibility = View.GONE
                                AlertDialog.Builder(this@LoginActivity).apply {
                                    setTitle(getString(R.string.gagal_login))
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

}