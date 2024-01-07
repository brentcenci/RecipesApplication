package com.brentcodes.recipesapplication.model.spoonaculardata

import com.google.gson.annotations.SerializedName


data class Nutrition(

    @SerializedName("nutrients") var nutrients: ArrayList<Nutrients> = arrayListOf(),
    @SerializedName("properties") var properties: ArrayList<Properties> = arrayListOf(),
    @SerializedName("flavonoids") var flavonoids: ArrayList<Flavonoids> = arrayListOf(),
    @SerializedName("ingredients") var ingredients: ArrayList<Ingredients> = arrayListOf(),
    @SerializedName("caloricBreakdown") var caloricBreakdown: CaloricBreakdown? = CaloricBreakdown(),
    @SerializedName("weightPerServing") var weightPerServing: WeightPerServing? = WeightPerServing()

)