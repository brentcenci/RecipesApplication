package com.brentcodes.recipesapplication.ui.screens

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.brentcodes.recipesapplication.model.SearchFilter
import com.brentcodes.recipesapplication.ui.vm.RecipesViewModel


@Composable
fun RoundedChip(
    filter: SearchFilter,
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
        Text(text = filter.type)
    }
}


@Composable
fun TestChipsWithDropDown(
    filter: SearchFilter,
    onClick: () -> Unit,
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


@Composable
fun FiltersBar(
    viewModel: RecipesViewModel,
    filtersList: List<SearchFilter>
) {
    val currentFiltersList by rememberUpdatedState(newValue = filtersList)
    Log.d("FiltersBar", "Recomposing FiltersBar with FiltersList: ${filtersList}")

    Row(
        modifier = Modifier
            .padding(5.dp)
            .fillMaxWidth()
    ) {
        currentFiltersList.forEach { filter ->
            RoundedChip(
                filter = filter,
                onClick = { viewModel.toggleFilter(filter.id) },
            )
        }
    }

    val currentFilter: SearchFilter? =
        if (filtersList.any { it.open }) {
            filtersList.filter { filter ->
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