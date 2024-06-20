package com.dicoding.gardenguard.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import com.dicoding.gardenguard.data.Result
import com.dicoding.gardenguard.data.model.Disease
import com.dicoding.gardenguard.data.model.Fruit
import com.dicoding.gardenguard.data.model.Vegetable
import com.dicoding.gardenguard.data.preference.UserPreference
import com.dicoding.gardenguard.data.response.ErrorResponse
import com.dicoding.gardenguard.data.retrofit.ApiService
import com.google.gson.Gson
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.HttpException
import java.io.File

class PlantRepository(
    private val userPreference: UserPreference,
    private val apiService: ApiService
) {

    private val _fruitList = MutableLiveData<List<Fruit>>()

    val fruitList: LiveData<List<Fruit>>
        get() = _fruitList


    init {
        val fruits = listOf(
            Fruit(
                name = "Apple",
                scientificName = "(Malus domestica)",
                imageUrl = "https://images.unsplash.com/photo-1508706000025-b720ee541485?q=80&w=2139&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
                diseases = listOf(
                    Disease(
                        name = "Apple scab",
                        scientificName = "Venturia inaequalis",
                        imageUrl = "https://i.ibb.co.com/pKmc6KC/applescab.jpg"
                    ),
                    Disease(
                        name = "Black rot",
                        scientificName = "Botryosphaeria obtusa",
                        imageUrl = "https://i.ibb.co.com/Xjy84b4/blackrot.jpg"
                    ),
                    Disease(
                        name = "Cedar apple rust",
                        scientificName = "Gymnosporangium juniperi-virginianae",
                        imageUrl = "https://i.ibb.co.com/fDnVQL7/applerust.jpg"
                    )
                )
            ),
            Fruit(
                name = "Mango",
                scientificName = "(Mangifera indica)",
                imageUrl = "https://images.unsplash.com/photo-1622955658214-d05c1c6fcf84?q=80&w=1932&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
                diseases = listOf(
                    Disease(
                        name = "Anthracnose",
                        scientificName = "Colletotrichum gloeosporioides",
                        imageUrl = "https://i.ibb.co.com/hc6xMPS/manggoanthracnose.jpg"
                    ),
                    Disease(
                        name = "Bacterial canker",
                        scientificName = "Xanthomonas campestris pv. mangiferaeindicae",
                        imageUrl = "https://i.ibb.co.com/V96wyBG/mangobacterial.jpg"
                    ),
                    Disease(
                        name = "Dieback",
                        scientificName = "Lasiodiplodia theobromae",
                        imageUrl = "https://i.ibb.co.com/DzZbBXx/manggodieback.jpg"
                    )
                )
            ),
            Fruit(
                name = "Grape",
                scientificName = "(Vitis vinifera)",
                imageUrl = "https://images.unsplash.com/photo-1598112974331-d999b0b891cc?q=80&w=1974&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
                diseases = listOf(
                    Disease(
                        name = "Black rot",
                        scientificName = "Guignardia bidwellii",
                        imageUrl = "https://i.ibb.co.com/Jtdrv84/grapeblackrot.jpg"
                    ),
                    Disease(
                        name = "Esca",
                        scientificName = "Phaeomoniella chlamydospora",
                        imageUrl = "https://i.ibb.co.com/BtmcS5k/grapeesca.jpg"
                    ),
                    Disease(
                        name = "Leaf blight",
                        scientificName = "Pseudocercospora vitis",
                        imageUrl = "https://i.ibb.co.com/RC7vmYz/grapelightbright.jpg"
                    )
                )
            )
        )


        _fruitList.value = fruits
    }

    private val _vegetableList = MutableLiveData<List<Vegetable>>()

    val vegetableList: LiveData<List<Vegetable>>
        get() = _vegetableList


    init {
        val vegetables = listOf(
            Vegetable(
                name = "Tomato",
                scientificName = "(Solanum lycopersicum)",
                imageUrl = "https://images.unsplash.com/photo-1582284540020-8acbe03f4924?q=80&w=1935&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
                diseases = listOf(
                    Disease(
                        name = "Bacterial spot",
                        scientificName = "Xanthomonas spp. & Pseudomonas syringae pv. tomato",
                        imageUrl = "https://i.ibb.co.com/6yDnzYy/tomatbercak.jpg"
                    ),
                    Disease(
                        name = "Early blight",
                        scientificName = "Alternaria solani",
                        imageUrl = "https://i.ibb.co.com/LP38QmH/tomatlate.jpg"
                    ),
                    Disease(
                        name = "Late blight",
                        scientificName = "Phytophthora infestans",
                        imageUrl = "https://i.ibb.co.com/2ZbSfsX/tomatearly.jpg"
                    )
                )
            ),
            Vegetable(
                name = "Potato",
                scientificName = "(Solanum tuberosum)",
                imageUrl = "https://images.unsplash.com/photo-1518977676601-b53f82aba655?q=80&w=2070&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
                diseases = listOf(
                    Disease(
                        name = "Early blight",
                        scientificName = "Alternaria solani",
                        imageUrl = "https://i.ibb.co.com/Y3KqzyP/potatoeraly.jpg"
                    ),
                    Disease(
                        name = "Late blight",
                        scientificName = "Phytophthora infestans",
                        imageUrl = "https://i.ibb.co.com/LJ6bm77/potatolate.jpg"
                    )
                )
            ),
            Vegetable(
                name = "Corn",
                scientificName = "(Zea mays)",
                imageUrl = "https://images.unsplash.com/photo-1634467524884-897d0af5e104?q=80&w=2070&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
                diseases = listOf(
                    Disease(
                        name = "Common rust",
                        scientificName = "Puccinia sorghi",
                        imageUrl = "https://i.ibb.co.com/3m797rJ/jagungkarat.jpg"
                    ),
                    Disease(
                        name = "Gray leaf spot",
                        scientificName = "Cercospora zeae-maydis",
                        imageUrl = "https://i.ibb.co.com/QHSKmF8/jagungbercak.jpg"
                    ),
                    Disease(
                        name = "Northern leaf blight",
                        scientificName = "Setosphaeria turcica",
                        imageUrl = "https://i.ibb.co.com/FbD1bxJ/jagungblight.jpg"
                    )
                )
            )
        )

        _vegetableList.value = vegetables
    }

    fun diagnoseDisease(imageFile: File, plantName: String) = liveData {
        emit(Result.Loading)

        // Buat request body dari file gambar
        val requestImageFile = imageFile.asRequestBody("image/jpeg".toMediaType())
        val multipartBody = MultipartBody.Part.createFormData(
            "image",
            imageFile.name,
            requestImageFile
        )

        val plant = plantName.lowercase()

        try {
            // Lakukan pemanggilan API dengan Retrofit
            val token = runBlocking { userPreference.getToken().first() }
            val finalToken = "Bearer $token"
            val successResponse = apiService.diagnoseDisease(plant, multipartBody, finalToken)
            Log.d(TAG, "diagnoseDisease success: $plant")
            Log.d(TAG, "diagnoseDisease success: $successResponse")
            emit(Result.Success(successResponse))
        } catch (e: HttpException) {
            // Tangani kesalahan HTTP
            val errorBody = e.response()?.errorBody()?.string()
            val errorResponse = Gson().fromJson(errorBody, ErrorResponse::class.java)
            Log.d(TAG, "diagnoseDisease error: $errorResponse")
            emit(Result.Error(errorResponse.message ?: "Unknown error"))
        } catch (e: Exception) {
            // Tangani kesalahan umum
            Log.e(TAG, "diagnoseDisease exception: ${e.message}", e)
            emit(Result.Error("Failed to communicate with server"))
        }
    }

    fun getFruitDisease(plantName: String, diseaseName: String) = liveData {
        emit(Result.Loading)
        Log.d(TAG, "getFruitDisease: test")
        try {
            val token = runBlocking { userPreference.getToken().first() }
            val finalToken = "Bearer $token"
            val successResponse = apiService.getFruitDisease(plantName, diseaseName, finalToken)
            emit(Result.Success(successResponse))
        } catch (e: HttpException) {
            // Tangani kesalahan HTTP
            val errorBody = e.response()?.errorBody()?.string()
            val errorResponse = Gson().fromJson(errorBody, ErrorResponse::class.java)
            emit(Result.Error(errorResponse.message ?: "Unknown error"))
        } catch (e: Exception) {
            // Tangani kesalahan umum
            Log.e(TAG, "diagnoseDisease exception: ${e.message}", e)
        }
    }

    fun getVegetableDisease(plantName: String, diseaseName: String) = liveData {
        emit(Result.Loading)

        try {
            val token = runBlocking { userPreference.getToken().first() }
            val finalToken = "Bearer $token"
            val successResponse = apiService.getVegetableDisease(plantName, diseaseName, finalToken)
            Log.d(TAG, "getFruitDisease:")
            emit(Result.Success(successResponse))
        } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            val errorResponse = Gson().fromJson(errorBody, ErrorResponse::class.java)
            Log.d(TAG, "diagnoseDisease error: $errorResponse")
            emit(Result.Error(errorResponse.message ?: "Unknown error"))
        } catch (e: Exception) {
            Log.e(TAG, "diagnoseDisease exception: ${e.message}", e)
            emit(Result.Error("Failed to communicate with server"))
        }
    }


    companion object {
        private const val TAG = "PlantRepository"

        @Volatile
        private var instance: PlantRepository? = null
        fun getInstance(
            userPreference: UserPreference,
            apiService: ApiService
        ): PlantRepository =
            instance ?: synchronized(this) {
                instance ?: PlantRepository(userPreference, apiService)
            }.also { instance = it }
    }

}