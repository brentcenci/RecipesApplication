package com.brentcodes.recipesapplication.ui

import android.util.Log
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.brentcodes.recipesapplication.ui.vm.RecipesViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipeScaffold(
    modifier: Modifier = Modifier,
    viewModel: RecipesViewModel = viewModel(),
    navController: NavController = rememberNavController(),
    topBar: Boolean,
    bottomBar: Boolean,
    content: @Composable (PaddingValues) -> Unit)
{
    Scaffold(
        modifier = modifier,
        topBar = {
            if (topBar) TopAppBar(title = { Text(viewModel.topAppBarTitle.value) } )
                 },
        bottomBar = {
            if (bottomBar) RecipeBottomNavBar(navController = navController)
        }
    ) { paddingValues ->
        Log.d("padding", "$paddingValues")
        content(paddingValues)
    }
}