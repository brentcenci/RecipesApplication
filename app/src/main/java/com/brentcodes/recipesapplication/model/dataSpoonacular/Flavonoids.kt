package com.brentcodes.recipesapplication.model.dataSpoonacular

import com.google.gson.annotations.SerializedName


data class Flavonoids(

    @SerializedName("name") var name: String? = null,
    @SerializedName("amount") var amount: Int? = null,
    @SerializedName("unit") var unit: String? = null

)