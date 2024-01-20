package com.brentcodes.recipesapplication.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.brentcodes.recipesapplication.model.spoonaculardata.Results
import com.brentcodes.recipesapplication.model.spoonaculardata.SpoonacularResult
import com.brentcodes.recipesapplication.ui.vm.RecipesUiState
import com.brentcodes.recipesapplication.ui.vm.RecipesViewModel
import com.brentcodes.recipesapplication.ui.NestedScreens
import com.brentcodes.recipesapplication.ui.vm.RecipesLoadMoreState

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
            .padding(5.dp)
            .background(Color(0xFF00abe3))
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
fun TestRecipesPanel(
    viewModel: RecipesViewModel = androidx.lifecycle.viewmodel.compose.viewModel(),
    modifier: Modifier = Modifier,
    recipe: Results = Results(),
    navController: NavController = viewModel.navController.value?: rememberNavController()
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(5.dp)
            .clip(RoundedCornerShape(20))
            .border(2.dp, Color.Blue, RoundedCornerShape(20))
            .clickable {
                viewModel.selectRecipe(recipe = recipe)
                navController.navigate(NestedScreens.Recipe.route)
            }
    ) {
        AsyncImage(
            model = recipe.image!!,
            contentDescription = "Image of ${recipe.title}",
            modifier = Modifier.fillMaxWidth(),
            contentScale = ContentScale.Fit
        )
        Text(
            text = recipe.title?.split(" ")?.joinToString(separator = " ") {
                it.replaceFirstChar(Char::titlecaseChar)
            }?: "No Title",
            fontWeight = FontWeight.Bold,
            color = Color.Black

        )
    }

}


@Composable
fun SearchScreen(
    viewModel: RecipesViewModel,
    modifier: Modifier = Modifier,
    response: SpoonacularResult = SpoonacularResult()
) {

    val filtersList = viewModel.filteredList.collectAsState()

    LazyColumn(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {

        item {
            SearchBar(onSearch = viewModel::getRecipes, viewModel = viewModel)
        }

        item {
            FiltersBar(viewModel = viewModel, filtersList = filtersList.value)
        }


        val listOfResults = response.results

        //If no Search Results
        if (listOfResults.size == 0) {
            item {
                Box(modifier = Modifier.fillMaxSize()) {
                    Text(
                        text = "No results found for ${viewModel.query.value}\nTry searching something else!",
                        modifier = Modifier
                            .align(Alignment.Center)
                    )
                }
            }
        //If there are Search Results
        } else {
            items(response.results) { result ->
                RecipesPanel(viewModel = viewModel, recipe = result)
            }
            if (viewModel.recipesLoadMoreState is RecipesLoadMoreState.Loading) {
                item {
                    CircularProgressIndicator()
                }
            } else {
                item {
                    Button(onClick = { viewModel.getMoreRecipes() }) {
                        Text(text = "Load more recipes")
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(
    modifier: Modifier = Modifier,
    onSearch: (String) -> Unit = { },
    viewModel: RecipesViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {

    var text by remember {
        viewModel.query
    }

    Column(modifier = modifier.fillMaxWidth()) {

        //Row to contain the Search Text Bar and the Search Button
        Row(modifier = Modifier.padding(20.dp, 20.dp, 20.dp, 2.dp)) {

            //Text Field for Entering Search Text
            TextField(
                value = text,
                maxLines = 1,
                singleLine = true,
                keyboardOptions = KeyboardOptions(autoCorrect = false),
                onValueChange = {
                    if (!it.contains("\n")) {
                        text = it
                        viewModel.query.value = it
                    }
                },
                label = { Text("Search") },
                leadingIcon = { Icon(Icons.Filled.Search, contentDescription = null) },
                modifier = modifier
                    .fillMaxWidth(0.8F)
                    .padding(0.dp, 0.dp, 20.dp, 0.dp)
            )

            //Search Icon Button
            IconButton(
                onClick = {
                    onSearch(text)
                    //also pass selected filters here
                },
                modifier = Modifier
                    .background(Color(0xFF00abe3), CircleShape)
            ) {
                Icon(
                    imageVector = Icons.Rounded.Search,
                    contentDescription = null,
                    tint = Color.White
                )
            }
        }
    }


}