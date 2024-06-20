package com.dicoding.gardenguard.ui.diagnose

import FruitAdapter
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.gardenguard.ViewModelFactory
import com.dicoding.gardenguard.databinding.FragmentDiagnoseBinding
import com.dicoding.gardenguard.ui.adapter.VegetableAdapter

class DiagnoseFragment : Fragment() {

    private val viewModel by viewModels<DiagnoseViewModel> {
        ViewModelFactory.getInstance(requireContext())
    }

    private var _binding: FragmentDiagnoseBinding? = null

    private val binding get() = _binding!!

    private lateinit var fruitAdapter: FruitAdapter
    private lateinit var vegetableAdapter: VegetableAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentDiagnoseBinding.inflate(inflater, container, false)
        val root: View = binding.root

        setupFruitRecyclerView()
        setupVegetableRecyclerView()

        viewModel.fruitList.observe(viewLifecycleOwner) { fruits ->
            fruits?.let {
                fruitAdapter.updateData(it)
            }
        }

        viewModel.vegetableList.observe(viewLifecycleOwner) { fruits ->
            fruits?.let {
                vegetableAdapter.updateData(it)
            }
        }

        return root
    }

    private fun setupFruitRecyclerView() {
        val recyclerView: RecyclerView = binding.rvFruit
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        fruitAdapter = FruitAdapter(requireContext(), emptyList()) { fruit ->
            val intent = Intent(requireContext(), UploadImageActivity::class.java).apply {
                putExtra("FRUIT_NAME", fruit.name)
            }
            startActivity(intent)
        }
        recyclerView.adapter = fruitAdapter
    }

    private fun setupVegetableRecyclerView() {
        val recyclerView: RecyclerView = binding.rvVegetable
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        vegetableAdapter = VegetableAdapter(requireContext(), emptyList()) { vegetable ->
            val intent = Intent(requireContext(), UploadImageActivity::class.java).apply {
                putExtra("VEGETABLE_NAME", vegetable.name)
            }
            startActivity(intent)
        }
        recyclerView.adapter = vegetableAdapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}