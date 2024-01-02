package com.brentcodes.recipesapplication.model.data

import com.google.gson.annotations.SerializedName


data class Ingredients(

    @SerializedName("text") var text: String? = null,
    @SerializedName("quantity") var quantity: Float? = null,
    @SerializedName("measure") var measure: String? = null,
    @SerializedName("food") var food: String? = null,
    @SerializedName("weight") var weight: Float? = null,
    @SerializedName("foodId") var foodId: String? = null

)