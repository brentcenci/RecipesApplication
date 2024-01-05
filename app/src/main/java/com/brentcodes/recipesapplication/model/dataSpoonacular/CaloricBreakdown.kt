package com.brentcodes.recipesapplication.model.dataSpoonacular

import com.google.gson.annotations.SerializedName


data class CaloricBreakdown(

    @SerializedName("percentProtein") var percentProtein: Double? = null,
    @SerializedName("percentFat") var percentFat: Double? = null,
    @SerializedName("percentCarbs") var percentCarbs: Double? = null

)