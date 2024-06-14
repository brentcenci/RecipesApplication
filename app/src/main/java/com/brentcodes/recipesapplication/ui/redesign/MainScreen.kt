package com.brentcodes.recipesapplication.ui.redesign

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(modifier: Modifier = Modifier) {
    val searchBar = remember { mutableStateOf("") }
    Column {
        //Search Bar
        Row {
            TextField(
                value = searchBar.value,
                onValueChange = { searchBar.value} ,
                maxLines = 1,
                label = { Text("Search for a recipe") },
                leadingIcon = { Icon(Icons.Rounded.Search, "Search Icon") }
            )
            Button(onClick = { /*TODO*/ },) {
                Icon(imageVector = Icons.Default.MoreVert, contentDescription = "Filters")
            }
        }
        //Cuisines
        Text("Cuisine")
        LazyRow {
            items(10) {
                Box(
                    modifier = Modifier
                        .padding(5.dp)
                        .background(Color.LightGray, RoundedCornerShape(50))
                        .width(80.dp)
                        .aspectRatio(1f)
                        .padding(10.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = it.toString())
                }
            }
        }

        //Category
        Text("Category")
        val selectedCategory = remember { mutableStateOf("Breakfast") }
        LazyRow {
            items(listOf("Breakfast", "Dinner", "Soups", "Lunch", "Sandwiches", "Sauce", "Dips", "Salad", "Beverage")) {
                val isSelected = selectedCategory.value == it
                Box(
                    modifier = Modifier
                        .padding(5.dp)
                        .background(
                            color = if (isSelected) Color(
                                100,
                                180,
                                100,
                                255
                            ) else Color.LightGray, RoundedCornerShape(50)
                        )
                        .padding(10.dp)
                        .clickable { selectedCategory.value = it },
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = it, color = if (isSelected) Color.White else Color.Black)
                }
            }
        }
        LazyRow {
            items(10) {
                Card {
                    Text(it.toString())
                }
            }
        }


    }

}