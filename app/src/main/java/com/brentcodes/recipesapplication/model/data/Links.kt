package com.brentcodes.recipesapplication.model.data

import com.google.gson.annotations.SerializedName


data class Links(

    @SerializedName("self") var self: Self? = Self(),
    @SerializedName("next") var next: Next? = Next()

)