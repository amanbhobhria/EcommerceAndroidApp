package com.example.pharmaecomapp.apiHelper

import com.example.pharmaecomapp.data.BrandsResponse
import com.example.pharmaecomapp.data.LoginResponse
import com.example.pharmaecomapp.utils.ApiConstants
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Header
import retrofit2.http.POST

interface ApiService {
    @FormUrlEncoded
    @POST(ApiConstants.login)  // Replace with your actual login endpoint
    fun loginUser(
        @Field(ApiConstants.mobile) mobile: String,
        @Field(ApiConstants.password) password: String
    ): Call<LoginResponse> // Replace LoginResponse with your response data class




    @POST(ApiConstants.get_brand)  // Correct endpoint path
    fun getBrands(
        @Header("token") token: String
    ): Call<BrandsResponse>

}




