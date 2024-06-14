package com.brentcodes.recipesapplication.ui.redesign

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.rounded.PlayArrow
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.brentcodes.recipesapplication.ui.theme.DarkGrey
import com.brentcodes.recipesapplication.ui.theme.LightGrey
import com.brentcodes.recipesapplication.ui.theme.MainGreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(modifier: Modifier = Modifier) {
    val paddingValues = PaddingValues(horizontal = 20.dp)
    val searchBar = remember { mutableStateOf("") }
    Column(
        modifier = Modifier
            .fillMaxSize(),
    ) {
        Spacer(modifier = Modifier.height(20.dp))
        //Search Bar
        Column(Modifier.padding(paddingValues)) {
            Row {
                TextField(
                    value = searchBar.value,
                    onValueChange = { searchBar.value} ,
                    maxLines = 1,
                    label = { Text("Search for a recipe") },
                    leadingIcon = { Icon(Icons.Rounded.Search, "Search Icon") }
                )
                Button(onClick = { /*TODO*/ }, colors = ButtonDefaults.buttonColors(containerColor = MainGreen)) {
                    Icon(imageVector = Icons.Default.MoreVert, contentDescription = "Filters")
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))
        //Cuisines
        Column(
        ) {
            val cuisines = mapOf(
                "Italian" to Color.Red.copy(alpha = 0.2f),
                "Spanish" to Color.Yellow.copy(alpha = 0.2f),
                "Japanese" to Color.Green.copy(alpha = 0.2f),
                "Chinese" to Color.Blue.copy(alpha = 0.2f),
                "French" to Color.Cyan.copy(alpha = 0.2f),
                "Australian" to Color.Magenta.copy(alpha = 0.2f),
                "American" to Color.Gray.copy(alpha = 0.2f)
            )
            MainScreenTitleText(modifier = Modifier.padding(paddingValues), text = "Cuisine")
            LazyRow(
                contentPadding = paddingValues
            ) {
                items(cuisines.toList()) {
                    Box(
                        modifier = Modifier
                            .padding(top = 5.dp, end = 10.dp, bottom = 5.dp)
                            .background(LightGrey, RoundedCornerShape(50))
                            .width(80.dp)
                            .aspectRatio(1f)
                            .padding(10.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = it.first, fontSize = 12.sp)
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))
        //Category
        Column(
            //Modifier.padding(vertical = 20.dp)
        ) {
            MainScreenTitleText(modifier = Modifier.padding(paddingValues), text = "Category")
            val selectedCategory = remember { mutableStateOf("Breakfast") }
            LazyRow(
                contentPadding = paddingValues
            ) {
                items(listOf("Breakfast", "Dinner", "Soups", "Lunch", "Sandwiches", "Sauce", "Dips", "Salad", "Beverage")) {
                    val isSelected = selectedCategory.value == it
                    Box(
                        modifier = Modifier
                            .padding(top = 5.dp, end = 10.dp, bottom = 5.dp)
                            .background(
                                color = if (isSelected) MainGreen else LightGrey,
                                RoundedCornerShape(50)
                            )
                            .padding(10.dp)
                            .clickable { selectedCategory.value = it },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = it, color = if (isSelected) Color.White else Color.Black)
                    }
                }
            }
            LazyRow(
                contentPadding = paddingValues
            ) {
                items(10) {
                    Box(
                        modifier = Modifier
                            .padding(top = 5.dp, end = 10.dp, bottom = 5.dp)
                            .size(width = 150.dp, height = 200.dp)
                            .background(LightGrey, RoundedCornerShape(20.dp))
                            .padding(10.dp)
                    ) {
                        Text(selectedCategory.value + " " + it.toString())
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))
        
        //Find Random
        Column{
            Box(
                modifier = Modifier
                    .padding(paddingValues)
                    .background(DarkGrey, RoundedCornerShape(20.dp))
                    .fillMaxWidth()
                    .height(140.dp)
                    .padding(10.dp)
            ){
                Column {
                    Text(text = "Can't decide?", color = Color.White, fontSize = 30.sp)
                    Text(text = "Let us make your life easier.", color = Color.White, fontSize = 16.sp)
                    Button(onClick = { /*TODO*/ }, modifier = Modifier
                        .padding(10.dp)
                        .fillMaxWidth()
                        .height(70.dp), shape = RoundedCornerShape(10.dp), colors = ButtonDefaults.buttonColors(containerColor = MainGreen, contentColor = Color.White)) {
                        Text("Random Recipe")
                        Icon(Icons.Rounded.PlayArrow, "Arrow Icon")
                    }
                }
            }
        }
    }

}

@Composable
fun MainScreenTitleText(modifier: Modifier = Modifier, text: String) {
    Column(modifier = modifier) {
        Text(
            text = text,
            color = Color.Black,
            fontSize = 20.sp,
            fontWeight = FontWeight.SemiBold
        )
    }

}