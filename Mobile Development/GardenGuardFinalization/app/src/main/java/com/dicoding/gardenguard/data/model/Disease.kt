package com.dicoding.gardenguard.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Disease(
    val name: String,
    val scientificName : String,
    val imageUrl : String,
) : Parcelable
