package com.brentcodes.recipesapplication.model.data

import com.google.gson.annotations.SerializedName


data class Digest(

    @SerializedName("label") var label: String? = null,
    @SerializedName("tag") var tag: String? = null,
    @SerializedName("schemaOrgTag") var schemaOrgTag: String? = null,
    @SerializedName("total") var total: Float? = null,
    @SerializedName("hasRDI") var hasRDI: Boolean? = null,
    @SerializedName("daily") var daily: Float? = null,
    @SerializedName("unit") var unit: String? = null,

    )