package com.brentcodes.recipesapplication.ui

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.brentcodes.recipesapplication.ui.screens.HomeScreen
import com.brentcodes.recipesapplication.ui.vm.RecipesViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipeApp(viewModel: RecipesViewModel, modifier: Modifier = Modifier) {

    HomeScreen(recipesUiState = viewModel.recipesUiState, viewModel = viewModel, modifier = modifier)

    /*Scaffold(
        topBar = {
            TopAppBar(title = { Text(viewModel.topAppBarTitle.value) } )
        },
        content = {paddingValues ->
            Log.d("padding", "$paddingValues")
            HomeScreen(recipesUiState = viewModel.recipesUiState, viewModel = viewModel, modifier = Modifier.padding(top = 64.dp))
        }
    )*/
}