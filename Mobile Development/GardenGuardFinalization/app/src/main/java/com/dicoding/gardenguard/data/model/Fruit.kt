package com.dicoding.gardenguard.data.model
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Fruit(
    val name: String,
    val scientificName : String,
    val imageUrl: String,
    val diseases: List<Disease>
) : Parcelable

