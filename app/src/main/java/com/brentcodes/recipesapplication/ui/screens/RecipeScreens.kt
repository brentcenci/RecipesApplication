package com.brentcodes.recipesapplication.ui.screens

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.brentcodes.recipesapplication.model.spoonaculardata.Results
import com.brentcodes.recipesapplication.ui.vm.RecipesSelection
import com.brentcodes.recipesapplication.ui.vm.RecipesViewModel

@Composable
fun RecipeSummaryScreen(
    modifier: Modifier = Modifier,
    viewModel: RecipesViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    val recipe = remember { viewModel.selectedRecipe.value } ?: Results()

    Column(modifier = modifier) {

        /////////////////////////////////////////////////////////////
        //Layout: Banner of Recipe Image across top, with Title below
        /////////////////////////////////////////////////////////////
        AsyncImage(
            model = recipe.image,
            contentDescription = "Cover Image of the Recipe",
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .blur(15.dp, 15.dp),
            contentScale = ContentScale.FillWidth
        )
        Text(text = "${recipe.title?: "Example Title"}",
            modifier = Modifier.align(CenterHorizontally),
            fontSize = 30.sp)
        LazyColumn(Modifier.padding(20.dp)) {

            /////////////////////////////////////////
            //Prep time and Makes numbers also at top
            /////////////////////////////////////////
            item {
                Row(modifier = Modifier.fillMaxWidth()) {
                    Text(text = "Serves ${recipe.servings}")
                    Spacer(modifier = Modifier.weight(1f))
                    Text(text = "Takes ${recipe.readyInMinutes} mins")
                }
            }
            item {
                Button(onClick = { Log.d("SummaryScreen", "Recipe State: $recipe") }) {
                    Text("Click for the Recipe State")
                }
            }
            /////////////////////////////////////////////
            //Summary of the Recipe a little further down
            /////////////////////////////////////////////
            item {
                Text(text = "${recipe.summary?.replace("<b>","")?.replace("</b>","")}")
            }

        }
        ///////////////////////////////////////////////////
        //Listed cuisines, allergies and diets it fits into
        ///////////////////////////////////////////////////


    }
}

@Composable
fun RecipeNutritionScreen(modifier: Modifier = Modifier, viewModel: RecipesViewModel) {

    val recipe = remember { viewModel.selectedRecipe.value } ?: Results()

    Column(modifier = modifier) {

    }
}

@Composable
fun RecipeInstructionsScreen(modifier: Modifier = Modifier, viewModel: RecipesViewModel) {
    Box(modifier = modifier) {
        if (viewModel.recipesSelection is RecipesSelection.Selected) {
            Text("Instructions Screen for {${(viewModel.recipesSelection as RecipesSelection.Selected).recipe.title}")
        }
    }
}