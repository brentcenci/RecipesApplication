package com.brentcodes.recipesapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.brentcodes.recipesapplication.ui.NestedScreens
import com.brentcodes.recipesapplication.ui.RecipeApp
import com.brentcodes.recipesapplication.ui.screens.RecipeInstructionsScreen
import com.brentcodes.recipesapplication.ui.screens.RecipeNutritionScreen
import com.brentcodes.recipesapplication.ui.screens.RecipeScaffold
import com.brentcodes.recipesapplication.ui.screens.RecipeSummaryScreen
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
                    val topBar = true
                    // (navController.currentDestination?.route != NestedScreens.Search.route)
                    /*the above code needs to change to check live data for what the current destination is whenever a navigation occurs*/

                    var bottomBar by remember { mutableStateOf(false) }

                    // Observe the current destination and update bottomBar accordingly
                    navController.addOnDestinationChangedListener { _, destination, _ ->
                        bottomBar = when (destination.route) {
                            NestedScreens.Recipe.Summary.route,
                            NestedScreens.Recipe.Nutrition.route,
                            NestedScreens.Recipe.Instructions.route -> true

                            else -> false
                        }
                    }

                    RecipeScaffold(
                        topBar = topBar,
                        bottomBar = bottomBar,
                        viewModel = viewModel,
                        navController = navController
                    ) { paddingValues ->
                        NavHost(
                            navController = navController,
                            startDestination = NestedScreens.Search.route
                        ) {
                            //SEARCH SCREEN COMPOSABLE
                            composable(route = NestedScreens.Search.route) {
                                //bottomBar = false
                                RecipeApp(
                                    modifier = Modifier.padding(paddingValues),
                                    viewModel = viewModel
                                )
                            }

                            //NESTED NAVIGATION
                            navigation(
                                route = NestedScreens.Recipe.route,
                                startDestination = NestedScreens.Recipe.Summary.route
                            ) {

                                //SUMMARY SCREEN COMPOSABLE
                                composable(route = NestedScreens.Recipe.Summary.route) {
                                    //bottomBar = true
                                    RecipeSummaryScreen(
                                        modifier = Modifier.padding(paddingValues),
                                        viewModel = viewModel
                                    )
                                }

                                //NUTRITION SCREEN COMPOSABLE
                                composable(route = NestedScreens.Recipe.Nutrition.route) {
                                    //bottomBar = true
                                    RecipeNutritionScreen(
                                        modifier = Modifier.padding(paddingValues),
                                        viewModel = viewModel
                                    )
                                }

                                //INSTRUCTIONS SCREEN COMPOSABLE
                                composable(route = NestedScreens.Recipe.Instructions.route) {
                                    //bottomBar = true
                                    RecipeInstructionsScreen(
                                        modifier = Modifier.padding(
                                            paddingValues
                                        ), viewModel = viewModel
                                    )
                                }

                            }
                        }
                    }
                }
            }
        }
    }
}