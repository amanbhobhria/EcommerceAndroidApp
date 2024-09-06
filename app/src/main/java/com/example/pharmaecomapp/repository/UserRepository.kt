package com.example.pharmaecomapp.repository

import com.example.pharmaecomapp.apiHelper.ApiService


class UserRepository(private val apiService: ApiService) {

//    suspend fun loginUser(mobile: String, password: String): Result<LoginResponse> {
//        return try {
//            val response = apiService.loginUser(mobile, password)
//            if (response.isSuccessful) {
//                Result.success(response.body()!!)
//            } else {
//                Result.failure(Exception("Login failed"))
//            }
//        } catch (e: Exception) {
//            Result.failure(e)
//        }
//    }

//
//    suspend fun fetchBrands(accessToken: String): Result<List<Brand>> {
//        return try {
//            // Make the API call using a coroutine and pass the token in the header
//            val response: BrandsResponse = apiService.getBrands("token $accessToken")
//
//            // Process the response directly
//            val brandsList = response.data ?: emptyList() // Assuming 'data' contains the list of brands
//
//            // Return the list of brands as a success
//            Result.success(brandsList)
//        } catch (e: Exception) {
//            // Handle any exceptions during the API call
//            Result.failure(e)
//        }
//    }



//    suspend fun fetchBrands(): Result<List<Brands>> {
//        return try {
//            val token = "token" // Use the actual token string here
//            val brandsResponse = apiService.getBrands("Bearer $token") // Pass the token in the "Bearer" format if needed
//            Result.success(brandsResponse.brandsList) // Assuming BrandsResponse contains a list of brands
//        } catch (e: Exception) {
//            Result.failure(e)
//        }
//    }

}