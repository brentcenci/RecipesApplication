package com.brentcodes.recipesapplication.ui.screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.ScrollableState
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.brentcodes.recipesapplication.model.spoonaculardata.Results
import com.brentcodes.recipesapplication.ui.vm.RecipesUiState
import com.brentcodes.recipesapplication.ui.vm.RecipesViewModel
import com.brentcodes.recipesapplication.model.spoonaculardata.SpoonacularResult
import com.brentcodes.recipesapplication.ui.NestedScreens

@Composable
fun HomeScreen(
    recipesUiState: RecipesUiState,
    viewModel: RecipesViewModel,
    modifier: Modifier = Modifier
) {
    when (recipesUiState) {

        is RecipesUiState.Success -> TestSearchScreen(
            response = recipesUiState.response,
            viewModel = viewModel,
            modifier = modifier,
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

@Composable
fun RecipesPanel(
    viewModel: RecipesViewModel,
    modifier: Modifier = Modifier,
    recipe: Results,
    navController: NavController = viewModel.navController.value?: rememberNavController()
) {
    //Need to include details of: Label (Title), Image (Does depend on size?), Source?, Yield (Makes), Diet Labels?,
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(100.dp)
            .padding(20.dp)
            .background(Color(0xFF00abe3))
            .clip(RoundedCornerShape(5.dp))
            .border(2.dp, Color(0xFF00abe3))
            .clickable {
                //navigate to the specific recipe page
                viewModel.selectRecipe(recipe = recipe)
                navController.navigate(NestedScreens.Recipe.route)
            }
    ) {
        Text(
            text = recipe.title?:"No title".split(" ").joinToString(separator = " ") {
                it.replaceFirstChar(Char::titlecaseChar)
            },
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(5.dp),
            fontWeight = FontWeight.Bold,
            color = Color.White
        )
        AsyncImage(
            model = recipe.image!!,
            contentDescription = "An image of ${recipe.title}",
            modifier = Modifier
                .align(Alignment.CenterEnd)
        )

    }
}

@Composable
fun RecipeTabs() {
    var tabIndex by remember { mutableStateOf(0) }
    val tabs = listOf("Summary", "Nutrition", "Instructions")

    when (tabIndex) {
        0 -> Log.d("BrentEvent",tabs[0])
        1 -> Log.d("BrentEvent",tabs[1])
        2 -> Log.d("BrentEvent",tabs[2])
    }
    Column(modifier = Modifier.fillMaxWidth()) {

    }
}
