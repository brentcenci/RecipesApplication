package com.brentcodes.recipesapplication.model.network

import com.brentcodes.recipesapplication.model.data.EdamamResponse
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Query

private const val BASE_URL =
    "https://api.edamam.com/"
private const val APP_KEY =
    "634b93f76e9feec92f58d3ff37e54571"
private const val APP_ID =
    "dd1c0d38"

private val gson = GsonBuilder().setLenient().create()

private val loggingInterceptor = HttpLoggingInterceptor().apply {
    level = HttpLoggingInterceptor.Level.BODY
}

val okHttpClient = OkHttpClient.Builder()
    .addInterceptor(loggingInterceptor)
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(GsonConverterFactory.create(gson))
    .client(okHttpClient)
    .baseUrl(BASE_URL)
    .build()


interface RecipeApiService {
    @GET("/api/recipes/v2/")
    suspend fun getResponse(
        @Query("type") type: String,
        @Query("q") query: String,
        @Query("app_id") appId: String = APP_ID,
        @Query("app_key") appKey: String = APP_KEY
    ) : EdamamResponse
}

object RecipeApi {
    val retrofitService : RecipeApiService by lazy {
        retrofit.create(RecipeApiService::class.java)
    }
}