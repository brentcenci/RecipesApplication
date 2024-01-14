package com.brentcodes.recipesapplication.ui.screens

import android.util.Log
import android.widget.GridLayout
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.brentcodes.recipesapplication.model.SearchFilter
import com.brentcodes.recipesapplication.model.spoonaculardata.SpoonacularResult
import com.brentcodes.recipesapplication.ui.NestedScreens
import com.brentcodes.recipesapplication.ui.vm.RecipesViewModel


@Composable
fun RoundedChip(
    text: String = "Cuisine",
    modifier: Modifier = Modifier,
    onClick: () -> Unit = { }
) {
    Button(
        onClick = onClick,
        modifier = modifier,
        shape = CircleShape,
        contentPadding = PaddingValues(20.dp, 12.dp),
        border = BorderStroke(2.dp, Color.Blue),
        colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent, contentColor = Color.Blue)
    ) {
        Text(text = text)
    }
}

@Composable
fun ChipsWithDropDown(
    modifier: Modifier = Modifier,
    label: String,
    options : List<String>,
    selectedOptions: List<String>,
    onOptionSelected: (String) -> Unit,
) {
    var expanded by remember { mutableStateOf(false) }

    Column(modifier = modifier) {
        Chip(
            text = "$label",
            onClick = { expanded = !expanded },
            isSelected = selectedOptions.isNotEmpty()
        )

        // Display the dropdown menu if the chip is expanded
        if (expanded) {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier.fillMaxWidth()
            ) {
                items(options) {option ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                onOptionSelected(option)
                            }
                            .padding(8.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Checkbox(
                            checked = selectedOptions.contains(option),
                            onCheckedChange = null // Handle the checked state as needed
                        )
                        Text(text = option)
                    }
                }
            }
        }
    }
}


@Composable
fun TestChipsWithDropDown(
    filter: SearchFilter,
    onClick: () -> Unit
) {
    Chip(
        text = filter.type,
        onClick = onClick,
        isSelected = filter.selected.isNotEmpty()
    )
}



@Composable
fun Chip(
    text: String,
    onClick: () -> Unit,
    isSelected: Boolean
) {
    // Customize the chip appearance as needed
    Box(
        modifier = Modifier
            .padding(8.dp)
            .clip(CircleShape)
            .background(if (isSelected) Color.Gray else Color.LightGray, CircleShape)
            .clickable { onClick() }
            .border(2.dp, Color.Blue, CircleShape)
        //add a clip to make a nice rounded rectangle
    ) {
        Text(text = text, modifier = Modifier.padding(10.dp), color = Color.Blue)
    }
}

// Helper function to toggle options
fun toggleOption(currentOptions: MutableList<String>, option: String) {
    if (currentOptions.contains(option)) {
        currentOptions.remove(option)
    } else {
        currentOptions.add(option)
    }
}









@Preview
@Composable
fun TestSearchScreen(
    viewModel: RecipesViewModel = androidx.lifecycle.viewmodel.compose.viewModel(),
    modifier: Modifier = Modifier,
    response: SpoonacularResult = SpoonacularResult(),
    navController: NavController = viewModel.navController.value ?: rememberNavController()
) {

    LazyColumn(modifier = modifier) {

        item { TestSearchBar(onSearch = viewModel::getRecipes, viewModel = viewModel) }

        item { FiltersBar(viewModel = viewModel) }
        
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
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TestSearchBar(
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

@Composable
fun FiltersBar(viewModel: RecipesViewModel) {

    val filtersList = viewModel.filteredList.collectAsState()

    LazyRow(
        modifier = Modifier
            .padding(5.dp)
            .fillMaxWidth()
    ) {
        items(filtersList.value) { filter ->
            TestChipsWithDropDown(
                filter = filter,
                onClick = { viewModel.toggleFilter(filter.id) }
            )
        }
    }

    val currentFilter: SearchFilter? =
        if (filtersList.value.any { it.open }) {
            filtersList.value.filter { filter ->
                filter.open
            }.first()
        } else null

    if (currentFilter != null) {
        Column {
            currentFilter.options.chunked(2).forEach {chunk ->
                Row {
                    chunk.forEach{option ->
                        Row(
                            modifier = Modifier
                                .weight(1f)
                                .clickable {
                                    toggleOption(currentFilter.selected, option)
                                }
                                .padding(8.dp),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Checkbox(
                                checked = currentFilter.selected.contains(option),
                                onCheckedChange = null // Handle the checked state as needed
                            )
                            Text(text = option)
                        }
                    }
                }
            }
        }
    }
}