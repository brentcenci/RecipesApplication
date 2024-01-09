package com.brentcodes.recipesapplication.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.brentcodes.recipesapplication.ui.vm.RecipesSelection
import com.brentcodes.recipesapplication.ui.vm.RecipesViewModel

@Composable
fun RecipeSummaryScreen(modifier: Modifier = Modifier, viewModel: RecipesViewModel) {
    Box(modifier = modifier) {
        if (viewModel.recipesSelection is RecipesSelection.Selected) {
            Text("Summary Screen for {${(viewModel.recipesSelection as RecipesSelection.Selected).recipe.title}")
        }
    }
}

@Composable
fun RecipeNutritionScreen() {
    Box() {
        Text("Nutrition Screen")
    }
}

@Composable
fun RecipeInstructionsScreen() {
    Box() {
        Text("Instructions Screen")
    }
}