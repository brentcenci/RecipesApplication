package com.brentcodes.recipesapplication.model.dataSpoonacular

import com.google.gson.annotations.SerializedName


data class SpoonacularResult(

    @SerializedName("results") var results: ArrayList<Results> = arrayListOf(),
    @SerializedName("offset") var offset: Int? = null,
    @SerializedName("number") var number: Int? = null,
    @SerializedName("totalResults") var totalResults: Int? = null

)