package com.brentcodes.recipesapplication.model.network

import com.brentcodes.recipesapplication.model.spoonaculardata.SpoonacularResult
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Query


private const val BASE_URL =
    "https://api.spoonacular.com/"
private const val API_KEY =
    "9c6aca7ec19d4dd6b81de9c6a1076983" //remember to format query as apiKey

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
    @GET("recipes/complexSearch")
    suspend fun getResponse(
        @Query("query") query: String,
        @Query("apiKey") apiKey: String = API_KEY,
        @Query("addRecipeNutrition") addRecipeNutrition: Boolean = true,
        @Query("cuisine") cuisine: String? = null,
        @Query("diet") diet: String? = null,
        @Query("intolerances") intolerances: String? = null,
        @Query("offset") offset: Int? = null
    ) : SpoonacularResult
}

object RecipeApi {
    val retrofitService : RecipeApiService by lazy {
        retrofit.create(RecipeApiService::class.java)
    }
}