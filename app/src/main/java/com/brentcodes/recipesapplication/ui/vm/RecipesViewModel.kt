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
import com.brentcodes.recipesapplication.ui.NestedScreens
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
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


    private val _filteredList: MutableStateFlow<List<SearchFilter>> = MutableStateFlow(emptyList())
    val filteredList = _filteredList.stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    private val _selectedFilter: MutableStateFlow<SearchFilter?> = MutableStateFlow(null)
    val selectedFilter = _filteredList.stateIn(viewModelScope, SharingStarted.Lazily, null)


    private val _cuisineFilterOpen: MutableStateFlow<Boolean> = MutableStateFlow(false)
    private val _dietFilterOpen: MutableStateFlow<Boolean> = MutableStateFlow(false)
    private val _allergiesFilterOpen: MutableStateFlow<Boolean> = MutableStateFlow(false)

    val cuisineFilterOpen: StateFlow<Boolean> = _cuisineFilterOpen.asStateFlow()
    val dietFilterOpen: StateFlow<Boolean> = _dietFilterOpen.asStateFlow()
    val allergiesFilterOpen: StateFlow<Boolean> = _allergiesFilterOpen.asStateFlow()

    fun toggleCuisineFilter() {
        _cuisineFilterOpen.value = !_cuisineFilterOpen.value
    }

    fun toggleDietFilter() {
        _dietFilterOpen.value = !_dietFilterOpen.value
    }

    fun toggleAllergiesFilter() {
        _allergiesFilterOpen.value = !_allergiesFilterOpen.value
    }



    init {
        populateFiltersList()
        getRecipes()
    }

    private fun populateFiltersList() {
        val filtersList = listOf(
            SearchFilter(
                id = 0,
                type = "Cuisine",
                options = listOf("african", "asian", "american", "british", "cajun", "caribbean", "chinese", "eastern european", "european", "french",
                "german", "greek", "indian", "irish", "italian", "japanese", "jewish", "korean", "latin american", "mediterranean", "mexican", "middle eastern",
                "nordic", "southern", "spanish", "thai", "vietnamese"),
                open = false,
                selected = mutableListOf()
            ),
            SearchFilter(
                id = 1,
                type = "Diet",
                options = listOf("gluten free", "ketogenic", "vegetarian", "lacto-vegetarian", "ovo-vegetarian", "vegan", "pescetarian", "paleo", "primal"),
                open = false,
                selected = mutableListOf()
            ),
            SearchFilter(
                id = 2,
                type = "Allergies",
                options = listOf("dairy", "egg", "gluten", "grain", "peanut", "seafood", "sesame", "shellfish", "soy", "sulfite", "tree nut", "wheat"),
                open = false,
                selected = mutableListOf()
            )
        )
        _filteredList.value = filtersList
    }

    fun toggleFilter(id: Int) {

        Log.d("RecipesViewModel", "Toggle Filter: $id")
        val chosenFilter = _filteredList.value.find { it.id == id }
        chosenFilter!!.open = !chosenFilter.open
        //_filteredList.value = _filteredList.value // Notify the change
        val temp = _filteredList.value
        _filteredList.value = temp
        Log.d("RecipesViewModel", "Updated FiltersList: ${_filteredList.value}")
    }

    /*fun selectFilter(id: Int) {
        val selected = _filteredList.value.find {
            it.id == id
        }
        if (_selectedFilter.value == null) {
            _selectedFilter.value = selected
        }
        else if (_selectedFilter.value.id == id) {
            _selectedFilter.value.
        }

    }*/



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