package com.example.pharmaecomapp.apiHelper

import android.content.Context
import com.example.pharmaecomapp.repository.UserRepository

object ApiModule {

    // Provides an instance of ApiService
    fun provideApiService(context: Context): ApiService {
        return ApiClient().getClient(context).create(ApiService::class.java)
    }

    // Provides an instance of UserRepository
    fun provideUserRepository(context: Context): UserRepository {
        return UserRepository(provideApiService(context))
    }
}