package com.dicoding.gardenguard.ui.disease

import androidx.lifecycle.ViewModel
import com.dicoding.gardenguard.data.repository.PlantRepository

class DiseaseDetailViewModel(private val plantRepository: PlantRepository) : ViewModel() {
    fun getFruitDisease(plantName: String , diseaseName: String) = plantRepository.getFruitDisease(plantName, diseaseName)
    fun getVegetableDisease(plantName: String , diseaseName: String) = plantRepository.getVegetableDisease(plantName, diseaseName)
}