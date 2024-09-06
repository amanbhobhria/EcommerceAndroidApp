package com.example.pharmaecomapp.apiHelper

import android.content.Context
import android.util.Log
import com.example.pharmaecomapp.data.Brand
import com.example.pharmaecomapp.data.BrandsResponse
import com.example.pharmaecomapp.utils.ApiConstants
import com.google.gson.GsonBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiController {


    var context: Context? = null
    var progressView: ProgressView? = null


    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(ApiConstants.BaseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val apiService: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }

    fun <T> getConvertIntoModel(gsonStr: String?, modelClass: Class<T>?): T {
        val gson = GsonBuilder()
            .setLenient()
            .create()
        return gson.fromJson(gsonStr, modelClass)
    }


    // Function to fetch brands from the server
    fun fetchBrands(token: String, onSuccess: (List<Brand>) -> Unit, onError: (String) -> Unit) {
        val call = apiService.getBrands(token)
        val requestUrl = call.request().url.toString()  // Log this URL
        Log.d("ApiController", "Request URL: $requestUrl")

        call.enqueue(object : Callback<BrandsResponse> {
            override fun onResponse(
                call: Call<BrandsResponse>,
                response: Response<BrandsResponse>
            ) {
                if (response.isSuccessful) {
                    val brands = response.body()?.data ?: emptyList()
                    onSuccess(brands)
                } else {
                    onError("Failed to fetch brands. Status code: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<BrandsResponse>, t: Throwable) {
                onError(t.message ?: "Network error occurred while fetching brands.")
            }
        })
    }


}
