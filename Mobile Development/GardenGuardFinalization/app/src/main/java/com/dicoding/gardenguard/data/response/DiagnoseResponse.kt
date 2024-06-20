package com.dicoding.gardenguard.data.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class DiagnoseResponse(

	@field:SerializedName("pengobatan")
	val pengobatan: String? = null,

	@field:SerializedName("penyakit")
	val penyakit: String? = null,

	@field:SerializedName("createdAt")
	val createdAt: String? = null,

	@field:SerializedName("nama ilmiah penyakit")
	val namaIlmiahPenyakit: String? = null,

	@field:SerializedName("confidence")
	val confidence: String? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("tanaman")
	val tanaman: String? = null
): Parcelable
