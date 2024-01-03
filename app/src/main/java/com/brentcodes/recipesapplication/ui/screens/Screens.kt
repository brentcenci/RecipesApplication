package com.brentcodes.recipesapplication.ui.screens

import android.util.Log
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import coil.compose.AsyncImagePainter
import com.brentcodes.recipesapplication.model.data.EdamamResponse
import com.brentcodes.recipesapplication.ui.vm.RecipesUiState
import com.brentcodes.recipesapplication.ui.vm.RecipesViewModel

@Composable
fun HomeScreen(
    recipesUiState: RecipesUiState,
    viewModel: RecipesViewModel,
    modifier: Modifier = Modifier
) {
    when (recipesUiState) {
        //is RecipesUiState.Success -> RecipesListScreen(
        //    response = recipesUiState.response
        //)
        is RecipesUiState.Success -> SearchScreen(
            response = recipesUiState.response,
            viewModel = viewModel,
            modifier = modifier
        )
        is RecipesUiState.Error -> ErrorScreen(
            viewModel = viewModel, 
            modifier = modifier.fillMaxSize()
        )
        
        is RecipesUiState.Loading -> LoadingScreen(
            modifier = modifier.fillMaxSize()
        )
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

@Composable
fun ErrorScreen(
    viewModel: RecipesViewModel,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier,
        contentAlignment = Alignment.Center
    )
    {
        Column {
            Text(text = "Error loading content")
            Button(
                onClick = {viewModel.getRecipes()}) {
                Text(text = "Click to try again")
            }
        }

    }
}

@Composable
fun LoadingScreen(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CircularProgressIndicator()
            Text(text = "Loading!")
        }

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(
    modifier: Modifier, 
    onSearch: (String) -> Unit, 
    viewModel: RecipesViewModel) {
    var text by remember {
        viewModel.query
    } 
    Row {
        TextField(
            value = text,
            maxLines = 1,
            keyboardOptions = KeyboardOptions(autoCorrect = false),
            onValueChange = {
                text = it
                viewModel.query.value = it
                Log.d("myTag", "Executed onValueChange")
            },
            label = { Text("Search") },
            leadingIcon = { Icon(Icons.Filled.Search, contentDescription = null)},
            modifier = modifier
                .fillMaxWidth(0.9F)
        )
        Button(
            onClick = {onSearch(text)}
        ) {
            Text(text = "Search")
        }
    }

}

@Composable
fun SearchScreen(
    viewModel: RecipesViewModel,
    modifier: Modifier = Modifier,
    response: EdamamResponse
) {

    Column {
        SearchBar(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            onSearch = viewModel::getRecipes,
            viewModel = viewModel,
        )
        LazyColumn(
            content = {
                items(response.hits) {hit ->
                    Text(text = hit.recipe?.label ?: "null")
                }
            }
        )
    }

}