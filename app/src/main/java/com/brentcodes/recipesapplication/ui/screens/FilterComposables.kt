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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.brentcodes.recipesapplication.model.SearchFilter
import com.brentcodes.recipesapplication.ui.theme.ThemeBlue
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
        shape = /*if (!filter.open)
        {
            RoundedCornerShape(50)
        }
        else {
            RoundedCornerShape(topStartPercent = 50, topEndPercent = 50, bottomStartPercent = 0, bottomEndPercent = 0)
             }*/
        RoundedCornerShape(50),
        contentPadding = PaddingValues(20.dp, 12.dp),
        border = BorderStroke(2.dp, ThemeBlue),
        colors = if (filter.open or filter.selected.isNotEmpty()) {
            ButtonDefaults.buttonColors(
                containerColor = ThemeBlue,
                contentColor = Color.White

            ) } else {
            ButtonDefaults.buttonColors(
                containerColor = Color.Transparent,
                contentColor = ThemeBlue

            )
        }
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
            .border(2.dp, ThemeBlue, CircleShape)
        //add a clip to make a nice rounded rectangle
    ) {
        Text(text = text, modifier = Modifier.padding(10.dp), color = ThemeBlue)
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
) {
    val currentFiltersList by viewModel.filteredList.collectAsState()
    Log.d("FiltersBar", "Recomposing FiltersBar with FiltersList: ${currentFiltersList}")

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
        if (currentFiltersList.any { it.open }) {
            currentFiltersList.first { filter ->
                filter.open
            }
        } else null

    if (currentFilter != null) {
        Column {
            currentFilter.options.chunked(2).forEach { chunk ->
                Row {
                    chunk.forEach { option ->
                        Row(
                            modifier = Modifier
                                .weight(1f)
                                .clickable(
                                    onClick = {
                                        viewModel.toggleFilterOption(
                                            option = option,
                                            id = currentFilter.id
                                        )
                                    }
                                )
                                .padding(end = 8.dp),
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
            Divider()
        }
    }
}