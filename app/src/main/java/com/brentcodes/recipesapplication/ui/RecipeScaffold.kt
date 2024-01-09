package com.brentcodes.recipesapplication.ui

import android.util.Log
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.brentcodes.recipesapplication.ui.vm.RecipesViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipeScaffold(modifier: Modifier = Modifier, viewModel: RecipesViewModel, topBar: Boolean, bottomBar: Boolean, content: @Composable (PaddingValues) -> Unit) {
    Scaffold(
        modifier = modifier,
        topBar = {
            if (topBar) TopAppBar(title = { Text(viewModel.topAppBarTitle.value) } )
                 },
        bottomBar = {
            if (bottomBar) BottomAppBar {
                Text("hello")
            }//some composable here
        }
    ) { paddingValues ->
        Log.d("padding", "$paddingValues")
        content(paddingValues)
        //modifier = modifier.padding(paddingValues)
    }
}