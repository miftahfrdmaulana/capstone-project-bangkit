package com.dicoding.gardenguard.data.retrofit

import com.dicoding.gardenguard.data.model.Disease
import com.dicoding.gardenguard.data.response.DiagnoseResponse
import com.dicoding.gardenguard.data.response.DiseaseResponse
import com.dicoding.gardenguard.data.response.LoginResponse
import com.dicoding.gardenguard.data.response.ProfileResponse
import com.dicoding.gardenguard.data.response.RegisterResponse
import okhttp3.MultipartBody
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path

interface ApiService {

    @Multipart
    @POST("predict/{plant}")
    suspend fun diagnoseDisease(
        @Path("plant") plant : String,
        @Part file: MultipartBody.Part,
        @Header("Authorization") token: String
    ): DiagnoseResponse

    @FormUrlEncoded
    @POST("signup")
    suspend fun signup(
        @Field("email") email: String,
        @Field("username") name: String,
        @Field("password") password: String
    ): RegisterResponse

    @FormUrlEncoded
    @POST("signin")
    suspend fun signin(
        @Field("username") username: String,
        @Field("password") password: String
    ): LoginResponse

    @GET("profile")
    suspend fun getProfile(
        @Header("Authorization") token: String
    ): ProfileResponse

    @GET("/home/buah/{plant}/{disease}")
    suspend fun getFruitDisease(
        @Path("plant") plant: String,
        @Path("disease") disease : String,
        @Header("Authorization") token: String
    ) : DiseaseResponse

    @GET("/home/sayur/{plant}/{disease}")
    suspend fun getVegetableDisease(
        @Path("plant") plantName: String,
        @Path("disease") diseaseName : String,
        @Header("Authorization") token: String
    ) : DiseaseResponse
}