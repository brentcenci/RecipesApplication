package com.brentcodes.recipesapplication.ui.screens

import android.util.Log
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.brentcodes.recipesapplication.ui.vm.RecipesViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.brentcodes.recipesapplication.ui.NestedScreens
import com.brentcodes.recipesapplication.ui.theme.ThemeBlue

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
            if (topBar) TopNavBar(navController = navController, viewModel = viewModel)
        },
        bottomBar = {
            if (bottomBar) RecipeBottomNavBar(navController = navController)
        }
    ) { paddingValues ->
        Log.d("padding", "$paddingValues")
        content(paddingValues)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopNavBar(navController: NavController, viewModel: RecipesViewModel) {
    var title = viewModel.topAppBarTitle.value

    CenterAlignedTopAppBar(
        colors = TopAppBarDefaults.smallTopAppBarColors(
            containerColor = ThemeBlue,
            titleContentColor = Color.White,
            navigationIconContentColor = Color.White,
            actionIconContentColor = Color.White
        ),
        navigationIcon = {
            if (navController.currentDestination?.route != NestedScreens.Search.route) {
                IconButton(onClick = {

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
        title = { Text(viewModel.topAppBarTitle.value) }
    )
}

@Composable
fun RecipeBottomNavBar(navController: NavController) {
    BottomNavigation(
        backgroundColor = ThemeBlue
    ) {

        var selectedRoute by remember { mutableStateOf(navController.currentDestination?.route) }
        navController.addOnDestinationChangedListener { _, destination, _ ->
            selectedRoute = destination.route
        }
        BottomNavigationItem(
            selected = selectedRoute == NestedScreens.Recipe.Summary.route,
            onClick = {
                navController.navigate(NestedScreens.Recipe.Summary.route)
            },
            icon = { Icon(imageVector = Icons.Filled.Info, contentDescription = "Summary Icon") },
            label = { androidx.compose.material.Text("Summary") },
            selectedContentColor = Color.White,
            unselectedContentColor = Color.LightGray
        )

        BottomNavigationItem(
            selected = selectedRoute == NestedScreens.Recipe.Nutrition.route,
            onClick = {
                navController.navigate(NestedScreens.Recipe.Nutrition.route)
            },
            icon = {
                Icon(
                    imageVector = Icons.Filled.ShoppingCart,
                    contentDescription = "Nutritional Information Icon"
                )
            },
            label = { androidx.compose.material.Text("Nutrition") },
            selectedContentColor = Color.White,
            unselectedContentColor = Color.LightGray
        )

        BottomNavigationItem(
            selected = selectedRoute == NestedScreens.Recipe.Instructions.route,
            onClick = {
                navController.navigate(NestedScreens.Recipe.Instructions.route)
            },
            icon = { Icon(imageVector = Icons.Filled.List, contentDescription = "Steps Icon") },
            label = { androidx.compose.material.Text("Steps") },
            selectedContentColor = Color.White,
            unselectedContentColor = Color.LightGray
        )
    }
}