package com.dicoding.gardenguard.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.gardenguard.R
import com.dicoding.gardenguard.data.model.Fruit
import com.dicoding.gardenguard.data.model.Vegetable
import com.dicoding.gardenguard.data.repository.PlantRepository

class HomeViewModel(private val plantRepository: PlantRepository) : ViewModel() {

    val fruitList: LiveData<List<Fruit>> = plantRepository.fruitList
    val vegetableList: LiveData<List<Vegetable>> = plantRepository.vegetableList
}