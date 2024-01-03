package com.brentcodes.recipesapplication.ui

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import com.brentcodes.recipesapplication.ui.screens.HomeScreen
import com.brentcodes.recipesapplication.ui.vm.RecipesViewModel

@Composable
fun RecipeApp() {
    val recipeViewModel: RecipesViewModel = viewModel()
    HomeScreen(recipesUiState = recipeViewModel.recipesUiState, viewModel = recipeViewModel)
}