package com.brentcodes.recipesapplication.model.data

import com.google.gson.annotations.SerializedName


data class Hits(

    @SerializedName("recipe") var recipe: Recipe? = Recipe(),
    @SerializedName("_links") var Links: Links? = Links()

)