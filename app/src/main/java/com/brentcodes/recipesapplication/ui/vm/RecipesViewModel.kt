package com.brentcodes.recipesapplication.ui.vm

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.brentcodes.recipesapplication.model.SearchFilter
import com.brentcodes.recipesapplication.model.network.RecipeApi
import com.brentcodes.recipesapplication.model.spoonaculardata.Results
import com.brentcodes.recipesapplication.model.spoonaculardata.SpoonacularResult
import kotlinx.coroutines.launch
import java.io.IOException

sealed interface RecipesUiState {
    data class Success(val response : SpoonacularResult) : RecipesUiState
    object Error : RecipesUiState
    object Loading : RecipesUiState
}

sealed interface RecipesSelection {
    data class Selected(val recipe : Results) : RecipesSelection
    object Unselected : RecipesSelection
}

class RecipesViewModel : ViewModel() {
    var recipesUiState: RecipesUiState by mutableStateOf(RecipesUiState.Loading)
        private set
    var recipesSelection: RecipesSelection by mutableStateOf(RecipesSelection.Unselected)
        private set
    var query : MutableState<String> = mutableStateOf("")
    var topAppBarTitle: MutableState<String> = mutableStateOf("Search")
    var navController: MutableState<NavController?> = mutableStateOf(null)

    var filtersList : MutableState<List<SearchFilter>> = mutableStateOf(emptyList<SearchFilter>())
        private set


    init {
        populateFiltersList()
        getRecipes()
    }

    fun populateFiltersList() {
        filtersList.value = listOf(
            SearchFilter(
                type = "Cuisine",
                options = listOf("african", "asian", "american", "british", "cajun", "caribbean", "chinese", "eastern european", "european", "french",
                "german", "greek", "indian", "irish", "italian", "japanese", "jewish", "korean", "latin american", "mediterranean", "mexican", "middle eastern",
                "nordic", "southern", "spanish", "thai", "vietnamese"),
                open = false,
                selected = mutableListOf()
            ),
            SearchFilter(
                type = "Diet",
                options = listOf("gluten free", "ketogenic", "vegetarian", "lacto-vegetarian", "ovo-vegetarian", "vegan", "pescetarian", "paleo", "primal"),
                open = false,
                selected = mutableListOf()
            ),
            SearchFilter(
                type = "Allergies",
                options = listOf("dairy", "egg", "gluten", "grain", "peanut", "seafood", "sesame", "shellfish", "soy", "sulfite", "tree nut", "wheat"),
                open = false,
                selected = mutableListOf()
            )
        )
    }

    fun getRecipes(query: String = "chicken") {
        viewModelScope.launch {
            recipesUiState = RecipesUiState.Loading
            recipesUiState = try {
                val listResult = RecipeApi.retrofitService.getResponse(query = query)
                RecipesUiState.Success(
                    listResult
                )
            } catch(e: IOException) {
                Log.d("error", e.message!!)
                RecipesUiState.Error
            }
        }
    }

    fun getSelectedRecipe() {
        recipesSelection
    }
    fun selectRecipe(recipe : Results) {
        recipesSelection = RecipesSelection.Selected(recipe = recipe)
        topAppBarTitle.value = RecipesSelection.Selected(recipe = recipe).recipe.title ?: "Unnamed Recipe"
    }
}