package com.brentcodes.recipesapplication.ui.screens

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.brentcodes.recipesapplication.model.spoonaculardata.Ingredients
import com.brentcodes.recipesapplication.model.spoonaculardata.Nutrients
import com.brentcodes.recipesapplication.model.spoonaculardata.Results
import com.brentcodes.recipesapplication.model.spoonaculardata.Steps
import com.brentcodes.recipesapplication.ui.theme.ThemeBlue
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
        LazyColumn(Modifier.padding(20.dp)) {

            /////////////////////////////////////////
            //Prep time and Makes numbers also at top
            /////////////////////////////////////////
            item {
                Row(modifier = Modifier.fillMaxWidth()) {
                    Column(horizontalAlignment = CenterHorizontally) {
                        Text(text = "${recipe.servings}", fontSize = 20.sp)
                        Text(text = "Servings")
                    }
                    Text(
                        text = "${recipe.title?: "Example Title"}",
                        modifier = Modifier.weight(1f),
                        fontSize = 30.sp,
                        overflow = TextOverflow.Ellipsis,
                        softWrap = false,
                        textAlign = TextAlign.Center)
                    Column(horizontalAlignment = CenterHorizontally) {
                        Text(text = "${recipe.readyInMinutes}", fontSize = 20.sp)
                        Text(text = "Minutes")
                    }
                }
            }
            /*item {
                Button(onClick = { Log.d("SummaryScreen", "Recipe State: $recipe") }) {
                    Text("Click for the Recipe State")
                }
            }*/
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
fun RecipeHeader(text: String) {
    Row(modifier = Modifier
        .fillMaxWidth()
        .background(ThemeBlue)) {
        Text(text = text, color = Color.White)
    }
}

@Composable
fun RecipeNutrientSection(nutrient: Nutrients) {
    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(10.dp, 0.dp)) {
        Row(modifier = Modifier.fillMaxWidth()) {
            Text(text = "${nutrient.name?: "Unnamed Nutrient"}", modifier = Modifier.weight(1f))
            Text(text = "${nutrient.amount?: "-1"}${nutrient.unit?: ""}", modifier = Modifier.weight(1f))
            Text(text = "${nutrient.percentOfDailyNeeds?: "-1"}%", modifier = Modifier.weight(1f))
        }
        Divider(color = Color.LightGray)
    }

}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun RecipeNutritionScreen(modifier: Modifier = Modifier, viewModel: RecipesViewModel) {

    val recipe = remember { viewModel.selectedRecipe.value } ?: Results()

    LazyColumn(modifier = modifier.padding(top = 20.dp)) {



        item {
            Row(modifier = Modifier.padding(10.dp, 0.dp)) {
                Text(text = "Nutrient", modifier = Modifier.weight(1f), fontWeight = FontWeight.Bold, fontSize = 20.sp)
                Text(text = "Qty per Serve", modifier = Modifier.weight(1f), fontWeight = FontWeight.Bold, fontSize = 20.sp)
                Text(text = "% Daily Intake", modifier = Modifier.weight(1f), fontWeight = FontWeight.Bold, fontSize = 20.sp)
            }
        }
        stickyHeader {
            RecipeHeader("Energy")
        }
        item {
            RecipeNutrientSection(nutrient = recipe.nutrition!!.nutrients.first { it.name == "Calories" })
        }
        stickyHeader {
            RecipeHeader("Fats")
        }
        item {
            RecipeNutrientSection(nutrient = recipe.nutrition!!.nutrients.first { it.name == "Fat" })
        }
        item {
            RecipeNutrientSection(nutrient = recipe.nutrition!!.nutrients.first { it.name == "Saturated Fat" })
        }
        stickyHeader {
            RecipeHeader("Carbohydrates")
        }
        item {
            RecipeNutrientSection(nutrient = recipe.nutrition!!.nutrients.first { it.name == "Carbohydrates" })
        }
        item {
            RecipeNutrientSection(nutrient = recipe.nutrition!!.nutrients.first { it.name == "Net Carbohydrates" })
        }
        item {
            RecipeNutrientSection(nutrient = recipe.nutrition!!.nutrients.first { it.name == "Sugar" })
        }
        stickyHeader {
            RecipeHeader("Proteins")
        }
        item {
            RecipeNutrientSection(nutrient = recipe.nutrition!!.nutrients.first { it.name == "Protein" })
        }
        stickyHeader {
            RecipeHeader("Cholesterol")
        }
        item {
            RecipeNutrientSection(nutrient = recipe.nutrition!!.nutrients.first { it.name == "Cholesterol" })
        }
        stickyHeader {
            RecipeHeader("Nutrients")
        }
        item {
            RecipeNutrientSection(nutrient = recipe.nutrition!!.nutrients.first { it.name == "Sodium" })
        }
        item {
            RecipeNutrientSection(nutrient = recipe.nutrition!!.nutrients.first { it.name == "Potassium" })
        }
        item {
            RecipeNutrientSection(nutrient = recipe.nutrition!!.nutrients.first { it.name == "Iron" })
        }
        item {
            RecipeNutrientSection(nutrient = recipe.nutrition!!.nutrients.first { it.name == "Calcium" })
        }
        item {
            RecipeNutrientSection(nutrient = recipe.nutrition!!.nutrients.first { it.name == "Copper" })
        }
        item {
            RecipeNutrientSection(nutrient = recipe.nutrition!!.nutrients.first { it.name == "Folate" })
        }
        item {
            RecipeNutrientSection(nutrient = recipe.nutrition!!.nutrients.first { it.name == "Magnesium" })
        }
        item {
            RecipeNutrientSection(nutrient = recipe.nutrition!!.nutrients.first { it.name == "Manganese" })
        }
        item {
            RecipeNutrientSection(nutrient = recipe.nutrition!!.nutrients.first { it.name == "Zinc" })
        }
        stickyHeader {
            RecipeHeader("Vitamins")
        }
        item {
            RecipeNutrientSection(nutrient = recipe.nutrition!!.nutrients.first { it.name == "Vitamin A" })
        }
        item {
            RecipeNutrientSection(nutrient = recipe.nutrition!!.nutrients.first { it.name == "Vitamin B1" })
        }
        item {
            RecipeNutrientSection(nutrient = recipe.nutrition!!.nutrients.first { it.name == "Vitamin B2" })
        }
        item {
            RecipeNutrientSection(nutrient = recipe.nutrition!!.nutrients.first { it.name == "Vitamin B3" })
        }
        item {
            RecipeNutrientSection(nutrient = recipe.nutrition!!.nutrients.first { it.name == "Vitamin B5" })
        }
        item {
            RecipeNutrientSection(nutrient = recipe.nutrition!!.nutrients.first { it.name == "Vitamin B6" })
        }
        item {
            RecipeNutrientSection(nutrient = recipe.nutrition!!.nutrients.first { it.name == "Vitamin B12" })
        }
        item {
            RecipeNutrientSection(nutrient = recipe.nutrition!!.nutrients.first { it.name == "Vitamin C" })
        }
        item {
            RecipeNutrientSection(nutrient = recipe.nutrition!!.nutrients.first { it.name == "Vitamin E" })
        }
        item {
            RecipeNutrientSection(nutrient = recipe.nutrition!!.nutrients.first { it.name == "Vitamin K" })
        }



    }
}

@Composable
fun RecipeIngredientSection(ingredient: Ingredients) {
    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(10.dp, 0.dp)) {
        Row(modifier = Modifier.fillMaxWidth()) {
            Text(text = "${ingredient.name?: "Unnamed Ingredient"}", modifier = Modifier.weight(1f))
        }
        Divider(color = Color.LightGray)
    }
}


@Composable
fun RecipeStepSection(step: Steps) {
    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(10.dp, 0.dp)) {
        Row(modifier = Modifier.fillMaxWidth()) {
            Text(text = "Step ${step.number}", modifier = Modifier.weight(1f))
            Text(text = "${step.step}", modifier = Modifier.weight(5f))
        }
        Divider(color = Color.LightGray)
    }
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun RecipeInstructionsScreen(modifier: Modifier = Modifier, viewModel: RecipesViewModel) {

    val recipe = remember { viewModel.selectedRecipe.value } ?: Results()

    LazyColumn(modifier = modifier) {
        item {
            Row(modifier = Modifier.padding(10.dp, 0.dp)) {
                Text(text = "List of Ingredients", modifier = Modifier.weight(1f), fontWeight = FontWeight.Bold, fontSize = 20.sp)
            }
        }
        stickyHeader {
            RecipeHeader(text = "Ingredients")
        }
        items(recipe.nutrition!!.ingredients.toSet().toList()) {ingredient ->
            RecipeIngredientSection(ingredient = ingredient)
        }
        item {
            Spacer(modifier = Modifier.height(20.dp))
        }
        item {
            Row(modifier = Modifier.padding(10.dp, 0.dp)) {
                Text(text = "Steps", modifier = Modifier.weight(1f), fontWeight = FontWeight.Bold, fontSize = 20.sp)
            }
        }
        items(recipe.analyzedInstructions) { instruction->
            RecipeHeader(instruction.name?: "")
            instruction.steps.forEach {step ->
                RecipeStepSection(step = step)
            }

        }
        /*item {
            Button(onClick = {Log.d("RecipeState", "${recipe.analyzedInstructions}")}) {
                Text("click for analyzed instructions")
            }
        }*/
    }
}