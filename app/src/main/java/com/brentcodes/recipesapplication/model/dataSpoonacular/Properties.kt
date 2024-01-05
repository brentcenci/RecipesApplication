package com.brentcodes.recipesapplication.model.dataSpoonacular

import com.google.gson.annotations.SerializedName


data class Properties(

    @SerializedName("name") var name: String? = null,
    @SerializedName("amount") var amount: Double? = null,
    @SerializedName("unit") var unit: String? = null

)