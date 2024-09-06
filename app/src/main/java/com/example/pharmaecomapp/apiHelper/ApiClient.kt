package com.example.pharmaecomapp.apiHelper

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.example.pharmaecomapp.utils.ApiConstants
import com.example.pharmaecomapp.utils.PrefManager
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


class ApiClient {

    private var retrofit: Retrofit? = null
    private val REQUEST_TIMEOUT = 60L // Use Long for timeouts
    private var okHttpClient: OkHttpClient? = null

    fun getClient(context: Context): Retrofit {
        if (okHttpClient == null) {
            initOkHttp(context)
        }

        if (retrofit == null) {
            retrofit = Retrofit.Builder()
                .baseUrl(ApiConstants.BaseUrl) // Ensure correct Base URL
                .client(okHttpClient!!)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
        }
        return retrofit!!
    }

    private fun initOkHttp(context: Context) {
        val httpClient = OkHttpClient.Builder()
            .connectTimeout(REQUEST_TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(REQUEST_TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(REQUEST_TIMEOUT, TimeUnit.SECONDS)

        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        // Chucker Interceptor
        val chuckerInterceptor = ChuckerInterceptor.Builder(context)
            .collector(ChuckerCollector(context, showNotification = true))
            .maxContentLength(250_000L)
            .redactHeaders("Auth-Token", "Bearer")
            .alwaysReadResponseBody(true)
            .build()

        httpClient.addInterceptor(loggingInterceptor)
        httpClient.addInterceptor(chuckerInterceptor)
        httpClient.addInterceptor(ConnectivityInterceptor(context)) // Handle network connectivity

        // Adding token-based authentication (if needed)
        httpClient.addInterceptor { chain ->
            val originalRequest = chain.request()
            val accessToken = PrefManager.getString(ApiConstants.AccessToken, null)

            val requestBuilder = originalRequest.newBuilder()
                .addHeader("Accept", "application/json")
                .addHeader("Content-Type", "application/json")

            accessToken?.let {
                requestBuilder.addHeader("Authorization", "Bearer $it")
            }

            val newRequest = requestBuilder.build()
            chain.proceed(newRequest)
        }

        okHttpClient = httpClient.build()
    }
}
