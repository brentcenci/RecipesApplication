package com.brentcodes.recipesapplication.ui.screens

import android.util.Log
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.brentcodes.recipesapplication.model.dataSpoonacular.Results
import com.brentcodes.recipesapplication.ui.vm.RecipesUiState
import com.brentcodes.recipesapplication.ui.vm.RecipesViewModel
import com.brentcodes.recipesapplication.model.dataSpoonacular.SpoonacularResult

@Composable
fun HomeScreen(
    recipesUiState: RecipesUiState,
    viewModel: RecipesViewModel,
    modifier: Modifier = Modifier
) {
    when (recipesUiState) {

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
    response: SpoonacularResult
) {

    Column {
        SearchBar(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            onSearch = viewModel::getRecipes,
            viewModel = viewModel,
        )
        LazyColumn(
            /*content = {
                items(response.results) {result ->
                    Text(text = result.title ?: "null")
                }
            }*/
            content = {
                items(response.results) { result ->
                    RecipesPanel(viewModel = viewModel, recipe = result)
                }
            }
        )
    }

}


@Composable
fun RecipesPanel(
    viewModel: RecipesViewModel,
    modifier: Modifier = Modifier,
    recipe: Results
) {
    //Need to include details of: Label (Title), Image (Does depend on size?), Source?, Yield (Makes), Diet Labels?,
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(100.dp)
            .padding(20.dp)
            .border(2.dp, color = Color.Black)
    ) {
        Text(
            text = recipe.title?:"No title",
            modifier = Modifier
                .align(Alignment.TopStart)
        )
        AsyncImage(
            model = recipe.image!!,
            contentDescription = "An image of ${recipe.title}",
            modifier = Modifier
                .align(Alignment.CenterEnd)
        )

    }
}