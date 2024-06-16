package com.brentcodes.recipesapplication.ui.redesign

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material.icons.rounded.PlayArrow
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.brentcodes.recipesapplication.R
import com.brentcodes.recipesapplication.ui.theme.DarkGrey
import com.brentcodes.recipesapplication.ui.theme.LightGrey
import com.brentcodes.recipesapplication.ui.theme.MainGreen
import com.brentcodes.recipesapplication.ui.theme.RecipesApplicationTheme

val cuisines = mapOf(
    "african" to R.drawable.ugali,
    "asian" to R.drawable.rice,
    "american" to R.drawable.thanksgiving,
    "british" to R.drawable.scone,
    "cajun" to R.drawable.cookingpot,
    "caribbean" to R.drawable.peanutpunch,
    "chinese" to R.drawable.dumpling,
    "eastern european" to R.drawable.borscht,
    "european" to R.drawable.kielbasa,
    "french" to R.drawable.frenchbread,
    "german" to R.drawable.pretzel,
    "greek" to R.drawable.feta,
    "indian" to R.drawable.curry,
    "irish" to R.drawable.irishcoffee,
    "italian" to R.drawable.pasta,
    "japanese" to R.drawable.ramen,
    "jewish" to R.drawable.latkes,
    "korean" to R.drawable.bibimbap,
    "latin american" to R.drawable.molepoblano,
    "mediterranean" to R.drawable.lobster,
    "mexican" to R.drawable.mexicanfood,
    "middle eastern" to R.drawable.shakshuka,
    "nordic" to R.drawable.gravlax,
    "southern" to R.drawable.rib,
    "spanish" to R.drawable.seafoodpaella,
    "thai" to R.drawable.padthai,
    "vietnamese" to R.drawable.banhmi
)
@Composable
fun CleanMainScreen(modifier: Modifier = Modifier) {
    val padding = PaddingValues(horizontal = 20.dp)
    val filtersOpen = remember { mutableStateOf(false) }

    LazyColumn(
        contentPadding = PaddingValues(vertical = 20.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        item { LogoSection(paddingValues = padding) }
        item { SearchBarSection(paddingValues = padding, filtersOpen = filtersOpen.value, onFilterClick = { filtersOpen.value = !filtersOpen.value }) }
        item { CategoriesSection(paddingValues = padding) }
        item { RecipesSection(paddingValues = padding) }
        item { RandomRecipeSection(paddingValues = padding) }
    }

}

@Composable
fun LogoSection(modifier: Modifier = Modifier, paddingValues: PaddingValues) {
    Box(
        modifier
            .fillMaxWidth()
            .padding(paddingValues = paddingValues)) {
        Image(
            painter = painterResource(id = R.drawable.munch),
            "Logo",
            colorFilter = ColorFilter.tint(
                MainGreen
            ),
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBarSection(modifier: Modifier = Modifier, paddingValues: PaddingValues, filtersOpen: Boolean, onFilterClick: () -> Unit = { }) {
    val searchBar = remember { mutableStateOf("") }
    val filterIconColors = if (!filtersOpen) {
        IconButtonDefaults.iconButtonColors(containerColor = Color.Transparent, contentColor = Color.Black)
    } else {
        IconButtonDefaults.iconButtonColors(containerColor = MainGreen, contentColor = Color.White)
    }
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.padding(paddingValues = paddingValues)
    ) {
        OutlinedTextField(
            value = searchBar.value,
            onValueChange = { searchBar.value} ,
            maxLines = 1,
            label = { Text("Search for a recipe") },
            leadingIcon = { Icon(Icons.Rounded.Search, "Search Icon") },
            shape = RoundedCornerShape(20.dp),
            trailingIcon = {
                IconButton(onClick = { onFilterClick() }, modifier = Modifier.clip(
                    RoundedCornerShape(50)).size(40.dp) , colors=  filterIconColors) {
                    Icon(imageVector = Icons.Rounded.Menu, contentDescription = "Menu Icon")
                }
            },
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
fun CategoriesSection(modifier: Modifier = Modifier, paddingValues: PaddingValues) {
    val categories = mapOf(
        "main course" to R.drawable.diet,
        "side dish" to R.drawable.sidedish,
        "dessert" to R.drawable.cupcake,
        "appetizer" to R.drawable.gilda,
        "salad" to R.drawable.salad,
        "bread" to R.drawable.bread,
        "breakfast" to R.drawable.pancakes,
        "soup" to R.drawable.soup,
        "beverage" to R.drawable.cocktail,
        "sauce" to R.drawable.tomatosauce,
        "marinade" to R.drawable.sauces,
        "finger food" to R.drawable.charcuterie,
        "snack" to R.drawable.nachos,
        "drink" to R.drawable.softdrink
    )
    val selectedCategory = remember { mutableStateOf("main course") }
    MainScreenTitleText(modifier = modifier.padding(paddingValues), text = "Category")
    LazyRow(
        contentPadding = PaddingValues(horizontal = 20.dp)
    ) {
        items(categories.toList()) {
            val isSelected = selectedCategory.value == it.first.lowercase()
            Box(
                modifier = Modifier
                    .padding(top = 5.dp, end = 10.dp, bottom = 5.dp)
                    .background(
                        if (isSelected) MainGreen else LightGrey,
                        RoundedCornerShape(10.dp)
                    )
                    .width(80.dp)
                    .aspectRatio(1f)
                    .clip(RoundedCornerShape(10.dp))
                    .clickable { selectedCategory.value = it.first.lowercase() }
                    .padding(10.dp),
                contentAlignment = Alignment.Center
            ) {
                Column {
                    Image(painterResource(id = it.second), contentDescription = "", modifier = Modifier
                        .size(40.dp)
                        .align(Alignment.CenterHorizontally))
                    Text(text = it.first.replaceFirstChar { it.uppercase() }, fontSize = 10.sp, color = if (isSelected) Color.White else Color.Black, modifier = Modifier.align(alignment = Alignment.CenterHorizontally))
                }

            }
        }
    }
}

@Composable
fun RecipesSection(modifier: Modifier = Modifier, paddingValues: PaddingValues) {
    MainScreenTitleText(modifier = modifier.padding(paddingValues), text = "Recipes")
    LazyRow(
        contentPadding = PaddingValues(horizontal = 20.dp)
    ) {
        items(10) {
            Box(
                modifier = Modifier
                    .padding(top = 5.dp, end = 10.dp, bottom = 5.dp)
                    .size(width = 150.dp, height = 200.dp)
                    .background(LightGrey, RoundedCornerShape(10.dp))
                    .padding(10.dp)
            ) {
                Text(it.toString())
            }
        }
    }
}

@Composable
fun RandomRecipeSection(modifier: Modifier = Modifier, paddingValues: PaddingValues) {
    Column{
        Box(
            modifier = Modifier
                .padding(paddingValues = paddingValues)
                .background(DarkGrey, RoundedCornerShape(10.dp))
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

@Composable
fun MainScreenTitleText(modifier: Modifier = Modifier, text: String) {
    Row(modifier = modifier) {
        Text(
            text = text,
            color = Color.Black,
            fontSize = 20.sp,
            fontWeight = FontWeight.SemiBold
        )
        Spacer(modifier = Modifier.weight(1f))
        Text("View all", textDecoration = TextDecoration.Underline)
    }

}