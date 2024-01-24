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
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.io.IOException

sealed interface RecipesUiState {
    data class Success(val response: SpoonacularResult) : RecipesUiState
    object Error : RecipesUiState
    object Loading : RecipesUiState
    object OnStart : RecipesUiState
}

sealed interface RecipesLoadMoreState {
    object Success : RecipesLoadMoreState
    object Error : RecipesLoadMoreState
    object Loading : RecipesLoadMoreState
}

sealed interface RecipesSelection {
    data class Selected(val recipe: Results) : RecipesSelection
    object Unselected : RecipesSelection
}

class RecipesViewModel : ViewModel() {
    private val recipesUiState: MutableStateFlow<RecipesUiState> =
        MutableStateFlow(RecipesUiState.OnStart)

    val uiState =
        recipesUiState.stateIn(viewModelScope, SharingStarted.Lazily, RecipesUiState.OnStart)
    var recipesLoadMoreState: RecipesLoadMoreState by mutableStateOf(RecipesLoadMoreState.Success)
        private set
    var recipesSelection: RecipesSelection by mutableStateOf(RecipesSelection.Unselected)
        private set
    val selectedRecipe: MutableState<Results?> = mutableStateOf(null)


    var query: MutableState<String> = mutableStateOf("")
    var searchedQuery: MutableState<String> = mutableStateOf("")
    var offset: Int = 10
    var topAppBarTitle: MutableState<String> = mutableStateOf("Search")
    var navController: MutableState<NavController?> = mutableStateOf(null)


    private val _filteredList: MutableStateFlow<List<SearchFilter>> = MutableStateFlow(emptyList())
    val filteredList = _filteredList.stateIn(viewModelScope, SharingStarted.Lazily, emptyList())



    init {
        populateFiltersList()
        getRecipes()
    }

    private fun populateFiltersList() {
        val filtersList = listOf(
            SearchFilter(
                id = 0,
                type = "Cuisine",
                options = listOf(
                    "african",
                    "asian",
                    "american",
                    "british",
                    "cajun",
                    "caribbean",
                    "chinese",
                    "eastern european",
                    "european",
                    "french",
                    "german",
                    "greek",
                    "indian",
                    "irish",
                    "italian",
                    "japanese",
                    "jewish",
                    "korean",
                    "latin american",
                    "mediterranean",
                    "mexican",
                    "middle eastern",
                    "nordic",
                    "southern",
                    "spanish",
                    "thai",
                    "vietnamese"
                ),
                open = false,
                selected = mutableListOf()
            ),
            SearchFilter(
                id = 1,
                type = "Diet",
                options = listOf(
                    "gluten free",
                    "ketogenic",
                    "vegetarian",
                    "lacto-vegetarian",
                    "ovo-vegetarian",
                    "vegan",
                    "pescetarian",
                    "paleo",
                    "primal"
                ),
                open = false,
                selected = mutableListOf()
            ),
            SearchFilter(
                id = 2,
                type = "Allergies",
                options = listOf(
                    "dairy",
                    "egg",
                    "gluten",
                    "grain",
                    "peanut",
                    "seafood",
                    "sesame",
                    "shellfish",
                    "soy",
                    "sulfite",
                    "tree nut",
                    "wheat"
                ),
                open = false,
                selected = mutableListOf()
            )
        )
        _filteredList.value = filtersList
    }

    fun toggleFilter(id: Int) {

        Log.d("RecipesViewModel", "Toggle Filter: $id")
        val copyOfFilters = _filteredList.value.toMutableList()
        val chosenFilter = copyOfFilters[id]
        val currentOpenState = chosenFilter.open
        copyOfFilters.forEach {
            if (it.id != id) {
                it.open = false
            }
        }
        copyOfFilters[id] = chosenFilter.copy(open = !currentOpenState)
        _filteredList.value = copyOfFilters

        Log.d("filter", "new state = ${chosenFilter.open}")

        Log.d("RecipesViewModel", "Updated FiltersList: ${_filteredList.value}")
    }

    fun toggleFilterOption(option: String, id: Int) {
        val copyOfList = _filteredList.value.toMutableList()
        val chosenFilter = copyOfList[id]
        val selectedOptions = chosenFilter.selected.toMutableList()

        if (chosenFilter.selected.contains(option)) {
            selectedOptions.remove(option)
        } else {
            selectedOptions.add(option)
        }

        copyOfList[id] = chosenFilter.copy(selected = selectedOptions)

        _filteredList.value = copyOfList
        Log.d("RecipesViewModel", "Toggled FiltersList Option: ${_filteredList.value}")
    }

    fun getMoreRecipes() {
        viewModelScope.launch {
            recipesLoadMoreState = RecipesLoadMoreState.Loading

            val selectedCuisines = _filteredList.value[0].selected.joinToString(separator = ",")
            val selectedDiets = _filteredList.value[1].selected.joinToString(separator = ",")
            val selectedIntolerances = _filteredList.value[2].selected.joinToString(separator = ",")

            recipesLoadMoreState = try {
                val listResult = RecipeApi.retrofitService.getResponse(
                    query = searchedQuery.value,
                    cuisine = selectedCuisines,
                    diet = selectedDiets,
                    intolerances = selectedIntolerances,
                    offset = offset
                )
                offset += 10
                (recipesUiState.value as? RecipesUiState.Success)?.response?.results?.addAll(listResult.results)

                RecipesLoadMoreState.Success
            } catch (e: IOException) {
                Log.d("error", e.message!!)
                RecipesLoadMoreState.Error
            }

        }
    }


    fun getRecipes(query: String = "chicken") {
        viewModelScope.launch {
            recipesUiState.value = RecipesUiState.Loading

            val selectedCuisines = _filteredList.value[0].selected.joinToString(separator = ",")
            val selectedDiets = _filteredList.value[1].selected.joinToString(separator = ",")
            val selectedIntolerances = _filteredList.value[2].selected.joinToString(separator = ",")

            recipesUiState.value = try {
                val listResult = RecipeApi.retrofitService.getResponse(
                    query = query,
                    cuisine = selectedCuisines,
                    diet = selectedDiets,
                    intolerances = selectedIntolerances
                )
                searchedQuery.value = query
                offset = 10
                RecipesUiState.Success(
                    listResult
                )
            } catch (e: IOException) {
                Log.d("error", e.message!!)
                RecipesUiState.Error
            }
        }
    }

    fun getSelectedRecipe() {
        recipesSelection
    }

    fun selectRecipe(recipe: Results) {
        selectedRecipe.value = recipe
        recipesSelection = RecipesSelection.Selected(recipe = recipe)
        topAppBarTitle.value =
            RecipesSelection.Selected(recipe = recipe).recipe.title ?: "Unnamed Recipe"
    }
}