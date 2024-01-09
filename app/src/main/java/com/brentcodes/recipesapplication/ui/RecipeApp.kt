package com.brentcodes.recipesapplication.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.brentcodes.recipesapplication.ui.screens.HomeScreen
import com.brentcodes.recipesapplication.ui.vm.RecipesViewModel

@Composable
fun RecipeApp(viewModel: RecipesViewModel, modifier: Modifier = Modifier) {
    HomeScreen(recipesUiState = viewModel.recipesUiState, viewModel = viewModel, modifier = modifier)
}