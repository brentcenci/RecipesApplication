package com.brentcodes.recipesapplication.ui.vm

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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

    init {
        getRecipes()
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

    fun selectRecipe(recipe : Results) {
        recipesSelection = RecipesSelection.Selected(recipe = recipe)
    }
}