package com.brentcodes.recipesapplication.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.brentcodes.recipesapplication.ui.vm.RecipesSelection
import com.brentcodes.recipesapplication.ui.vm.RecipesViewModel

@Composable
fun RecipeBottomNavBar(navController: NavController) {
    BottomNavigation {
        BottomNavigationItem(
            selected = true,
            onClick = {
                 navController.navigate(NestedScreens.Recipe.Summary.route)
            },
            icon = { Icon(imageVector = Icons.Filled.Info, contentDescription = "Summary Icon") },
            label = { Text("Summary") }
        )

        BottomNavigationItem(
            selected = false,
            onClick = {
                navController.navigate(NestedScreens.Recipe.Nutrition.route)
            },
            icon = { Icon(imageVector = Icons.Filled.ShoppingCart, contentDescription = "Nutritional Information Icon") },
            label = { Text("Nutrition") }
        )

        BottomNavigationItem(
            selected = false,
            onClick = {
                navController.navigate(NestedScreens.Recipe.Instructions.route)
            },
            icon = { Icon(imageVector = Icons.Filled.List, contentDescription = "Steps Icon") },
            label = { Text("Steps") }
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
fun RecipeNutritionScreen() {
    Box() {
        Text("Nutrition Screen")
    }
}

@Composable
fun RecipeInstructionsScreen() {
    Box() {
        Text("Instructions Screen")
    }
}