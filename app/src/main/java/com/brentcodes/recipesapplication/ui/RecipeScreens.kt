package com.brentcodes.recipesapplication.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import com.brentcodes.recipesapplication.ui.vm.RecipesSelection
import com.brentcodes.recipesapplication.ui.vm.RecipesViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopNavBar(navController: NavController, viewModel: RecipesViewModel) {
    var title = viewModel.topAppBarTitle.value

    CenterAlignedTopAppBar(
        colors = TopAppBarDefaults.smallTopAppBarColors(
            containerColor = Color.Blue,
            titleContentColor = Color.White,
            navigationIconContentColor = Color.White,
            actionIconContentColor = Color.White
        ),
        navigationIcon = {
            if (navController.currentDestination?.route != NestedScreens.Search.route) {
                IconButton(onClick = {

                    /*Please bear in mind, that you should always prefer to use popUpTo over popBackStack,
                    because with popBackStack you must never forget to call navigate function afterwards,
                    otherwise you can end up in having empty back stack.*/

                    //navController.navigate(NestedScreens.Search.route)
                    viewModel.topAppBarTitle.value = "Search"
                    navController.popBackStack(NestedScreens.Search.route, false)

                }) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = Color.White
                    )
                }
            }
        },
        title = { androidx.compose.material3.Text(viewModel.topAppBarTitle.value) }
    )
}

@Composable
fun RecipeBottomNavBar(navController: NavController) {
    BottomNavigation {

        var selectedRoute by remember { mutableStateOf(navController.currentDestination?.route)}
        navController.addOnDestinationChangedListener { _, destination, _ ->
             selectedRoute = destination.route
        }
        BottomNavigationItem(
            selected = selectedRoute == NestedScreens.Recipe.Summary.route,
            onClick = {
                 navController.navigate(NestedScreens.Recipe.Summary.route)
            },
            icon = { Icon(imageVector = Icons.Filled.Info, contentDescription = "Summary Icon") },
            label = { Text("Summary") },
            selectedContentColor = Color.White,
            unselectedContentColor = Color.LightGray
        )

        BottomNavigationItem(
            selected = selectedRoute == NestedScreens.Recipe.Nutrition.route,
            onClick = {
                navController.navigate(NestedScreens.Recipe.Nutrition.route)
            },
            icon = { Icon(imageVector = Icons.Filled.ShoppingCart, contentDescription = "Nutritional Information Icon") },
            label = { Text("Nutrition") },
            selectedContentColor = Color.White,
            unselectedContentColor = Color.LightGray
        )

        BottomNavigationItem(
            selected = selectedRoute == NestedScreens.Recipe.Instructions.route,
            onClick = {
                navController.navigate(NestedScreens.Recipe.Instructions.route)
            },
            icon = { Icon(imageVector = Icons.Filled.List, contentDescription = "Steps Icon") },
            label = { Text("Steps") },
            selectedContentColor = Color.White,
            unselectedContentColor = Color.LightGray
        )
    }
}

@Composable
fun RecipeSummaryScreen(modifier: Modifier = Modifier, viewModel: RecipesViewModel) {
    Box(modifier = modifier) {
        if (viewModel.recipesSelection is RecipesSelection.Selected) {
            Text("Summary Screen for {${(viewModel.recipesSelection as RecipesSelection.Selected).recipe.title}")
        }
    }
}

@Composable
fun RecipeNutritionScreen(modifier: Modifier = Modifier, viewModel: RecipesViewModel) {
    Box(modifier = modifier) {
        if (viewModel.recipesSelection is RecipesSelection.Selected) {
            Text("Nutrition Screen for {${(viewModel.recipesSelection as RecipesSelection.Selected).recipe.title}")
        }
    }
}

@Composable
fun RecipeInstructionsScreen(modifier: Modifier = Modifier, viewModel: RecipesViewModel) {
    Box(modifier = modifier) {
        if (viewModel.recipesSelection is RecipesSelection.Selected) {
            Text("Instructions Screen for {${(viewModel.recipesSelection as RecipesSelection.Selected).recipe.title}")
        }
    }
}