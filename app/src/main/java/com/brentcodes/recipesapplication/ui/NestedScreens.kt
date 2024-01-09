package com.brentcodes.recipesapplication.ui

sealed class NestedScreens(val route: String) {
    object Search : NestedScreens("search")
    object Recipe : NestedScreens("recipe") {
        object Summary : NestedScreens("summary")
        object Nutrition : NestedScreens("nutrition")
        object Instructions : NestedScreens("instructions")
    }
}