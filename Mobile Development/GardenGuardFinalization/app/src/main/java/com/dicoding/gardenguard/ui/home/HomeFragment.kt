package com.dicoding.gardenguard.ui.home

import FruitAdapter
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.gardenguard.ViewModelFactory
import com.dicoding.gardenguard.databinding.FragmentHomeBinding
import com.dicoding.gardenguard.ui.adapter.VegetableAdapter
import com.dicoding.gardenguard.ui.diagnose.UploadImageActivity
import com.dicoding.gardenguard.ui.disease.DiseaseActivity

class HomeFragment : Fragment() {

    private val viewModel by viewModels<HomeViewModel> {
        ViewModelFactory.getInstance(requireContext())
    }

    private var _binding: FragmentHomeBinding? = null

    private val binding get() = _binding!!

    private lateinit var fruitAdapter: FruitAdapter
    private lateinit var vegetableAdapter: VegetableAdapter


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        setupFruitRecyclerView()
        setupVegetableRecyclerView()

        viewModel.fruitList.observe(viewLifecycleOwner) { fruits ->
            fruits?.let {
                fruitAdapter.updateData(it) // Memperbarui data buah di adapter
            }
        }

        viewModel.vegetableList.observe(viewLifecycleOwner) { fruits ->
            fruits?.let {
                vegetableAdapter.updateData(it) // Memperbarui data buah di adapter
            }
        }

        return root
    }

    private fun setupFruitRecyclerView() {
        val recyclerView: RecyclerView = binding.rvFruit
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        fruitAdapter = FruitAdapter(requireContext(), emptyList()) { fruit ->
            val intent = Intent(requireContext(), DiseaseActivity::class.java).apply {
                putExtra("FRUIT_NAME", fruit.name)
                putParcelableArrayListExtra("DISEASES", ArrayList(fruit.diseases))
            }
            startActivity(intent)
        }
        recyclerView.adapter = fruitAdapter
    }

    private fun setupVegetableRecyclerView() {
        val recyclerView: RecyclerView = binding.rvVegetable
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        vegetableAdapter = VegetableAdapter(requireContext(), emptyList()) { vegetable ->
            val intent = Intent(requireContext(), DiseaseActivity::class.java).apply {
                putExtra("VEGETABLE_NAME", vegetable.name)
                putParcelableArrayListExtra("DISEASES", ArrayList(vegetable.diseases))
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