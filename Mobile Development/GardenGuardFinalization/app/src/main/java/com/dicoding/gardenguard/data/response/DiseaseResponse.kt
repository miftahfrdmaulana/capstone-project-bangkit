package com.dicoding.gardenguard.data.response

import com.google.gson.annotations.SerializedName

data class DiseaseResponse(

	@field:SerializedName("pengobatan")
	val pengobatan: String? = null,

	@field:SerializedName("penyakit")
	val penyakit: String? = null,

	@field:SerializedName("nama ilmiah penyakit")
	val namaIlmiahPenyakit: String? = null,

	@field:SerializedName("tanaman")
	val tanaman: String? = null
)
