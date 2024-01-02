package com.brentcodes.recipesapplication.ui.screens

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.brentcodes.recipesapplication.ui.vm.RecipesUiState
import androidx.lifecycle.viewmodel.compose.viewModel
import com.brentcodes.recipesapplication.model.data.EdamamResponse
import com.brentcodes.recipesapplication.ui.vm.RecipesViewModel

@Composable
fun RecipeApp() {
    val recipeViewModel: RecipesViewModel = viewModel()
    HomeScreen(recipesUiState = recipeViewModel.recipesUiState)
}

@Composable
fun HomeScreen(
    recipesUiState: RecipesUiState,
    modifier: Modifier = Modifier
) {
    when (recipesUiState) {
        is RecipesUiState.Success -> RecipesListScreen(
            response = recipesUiState.response
        )
        is RecipesUiState.Error -> Text("Error")
            //ErrorScreen(modifier = modifier.fillMaxSize())
        is RecipesUiState.Loading -> Text("Loading")
            //LoadingScreen(modifier = modifier.fillMaxSize())
    }
}

@Composable
fun RecipesListScreen(
    response: EdamamResponse,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        content = {
            items(response.hits) {hit ->
                Text(text = hit.recipe?.label ?: "null")
            }
        }
    )
}