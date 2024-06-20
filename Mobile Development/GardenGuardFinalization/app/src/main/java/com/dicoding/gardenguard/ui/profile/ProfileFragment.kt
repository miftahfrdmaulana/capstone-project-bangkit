package com.dicoding.gardenguard.ui.profile

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.dicoding.gardenguard.ViewModelFactory
import com.dicoding.gardenguard.data.Result
import com.dicoding.gardenguard.databinding.FragmentProfileBinding
import com.dicoding.gardenguard.ui.welcome.WelcomeActivity


class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null

    private val viewModel by viewModels<ProfileViewModel> {
        ViewModelFactory.getInstance(requireContext())
    }

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {

        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val root: View = binding.root


        viewModel.getProfile().observe(viewLifecycleOwner) { profile ->
            if (profile != null) {
                when (profile) {
                    is Result.Error -> {
                        binding.progressBarProfile.visibility = View.GONE
                        Toast.makeText(this.requireContext(), profile.error, Toast.LENGTH_SHORT).show()
                    }
                    Result.Loading -> binding.progressBarProfile.visibility = View.VISIBLE
                    is Result.Success -> {
                        binding.progressBarProfile.visibility = View.GONE
                        binding.tvUsername.text = profile.data.user.username
                        binding.tvEmail.text = profile.data.user.email
                    }
                }
            }
        }

        binding.btLogout.setOnClickListener {
            viewModel.logout()
            val intent = Intent(this@ProfileFragment.requireContext(), WelcomeActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}