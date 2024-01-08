package com.brentcodes.recipesapplication.ui

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController

sealed class NestedScreen(val route: String) {
    object Search : NestedScreen("search")
    object Recipe : NestedScreen("recipe") {
        object Summary : NestedScreen("summary")
        object Nutrition : NestedScreen("nutrition")
        object Instructions : NestedScreen("instructions")
    }
}


@Composable
fun Navigation() {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = NestedScreen.Search.route)
    {
        composable(route = NestedScreen.Search.route) {
            //SearchScreen()
        }
        navigation(
            startDestination = NestedScreen.Recipe.Summary.route,
            route = NestedScreen.Recipe.route
        ) {
            composable(route = NestedScreen.Recipe.Summary.route) {
                //RecipeSummaryScreen()
            }
            composable(route = NestedScreen.Recipe.Nutrition.route) {
                //RecipeNutritionScreen()
            }
            composable(route = NestedScreen.Recipe.Instructions.route) {
                //RecipeInstructionsScreen()
            }
        }
    }
}