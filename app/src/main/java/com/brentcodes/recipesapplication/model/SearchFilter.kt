package com.brentcodes.recipesapplication.model

data class SearchFilter(
    val type: String,
    val options: List<String>,
    var open: Boolean = false,
    var selected: MutableList<String>
)