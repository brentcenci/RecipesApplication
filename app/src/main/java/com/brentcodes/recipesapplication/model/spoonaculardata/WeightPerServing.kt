package com.brentcodes.recipesapplication.model.spoonaculardata

import com.google.gson.annotations.SerializedName


data class WeightPerServing(

    @SerializedName("amount") var amount: Int? = null,
    @SerializedName("unit") var unit: String? = null

)