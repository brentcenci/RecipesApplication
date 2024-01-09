package com.brentcodes.recipesapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.brentcodes.recipesapplication.ui.NestedScreens
import com.brentcodes.recipesapplication.ui.RecipeApp
import com.brentcodes.recipesapplication.ui.RecipeScaffold
import com.brentcodes.recipesapplication.ui.RecipeSummaryScreen
import com.brentcodes.recipesapplication.ui.theme.RecipesApplicationTheme
import com.brentcodes.recipesapplication.ui.vm.RecipesViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RecipesApplicationTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    val viewModel: RecipesViewModel = viewModel()
                    viewModel.navController.value = navController

                    NavHost(
                        navController = navController,
                        startDestination = NestedScreens.Search.route
                    ) {
                        composable(route = NestedScreens.Search.route) {
                            RecipeScaffold(topBar = true, bottomBar = false, viewModel = viewModel, navController = navController) {
                                RecipeApp(modifier = Modifier.padding(it), viewModel = viewModel)
                            }
                        }
                        navigation(
                            startDestination = NestedScreens.Recipe.Summary.route,
                            route = NestedScreens.Recipe.route
                        ) {
                            composable(route = NestedScreens.Recipe.Summary.route) {
                                RecipeScaffold(topBar = true, bottomBar = true, navController = navController) {
                                    RecipeSummaryScreen(modifier = Modifier.padding(it), viewModel = viewModel)
                                }
                            }
                            composable(route = NestedScreens.Recipe.Nutrition.route) {
                                //RecipeNutritionScreen()
                            }
                            composable(route = NestedScreens.Recipe.Instructions.route) {
                                //RecipeInstructionsScreen()
                            }
                        }
                    }

                }
            }
        }
    }
}