package com.brentcodes.recipesapplication.ui.redesign

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.brentcodes.recipesapplication.R

@Composable
fun OnboardingScreens(modifier: Modifier = Modifier) {
    val state = rememberOnboardingState(numScreens = 3)
    Onboarding(
        state = state,
        { OnboardingScreen(image = painterResource(id = R.drawable.munch), title = "Find your next meal.", subtitle = "Browse the vast selection of quick and easy recipes and find the one that's calling to you.", buttonText = "Next", state = it) },
        { OnboardingScreen(image = painterResource(id = R.drawable.munch), title = "Screen 2", subtitle = "Subtitle of Screen 2", buttonText = "Next", state = it) },
        { OnboardingScreen(image = painterResource(id = R.drawable.munch), title = "Screen 3", subtitle = "Subtitle of Screen 3", buttonText = "Next", state = it) }
    )
}

@Composable
fun OnboardingScreen(
    modifier: Modifier = Modifier,
    background: Painter = painterResource(id = R.drawable.background),
    image: Painter,
    title: String,
    subtitle: String,
    buttonText: String,
    state: OnboardingState
) {
    Column(
        Modifier
            .paint(background, contentScale = ContentScale.FillBounds)
            .fillMaxSize()
            .padding(40.dp), horizontalAlignment = Alignment.CenterHorizontally) {
        Spacer(modifier = Modifier.height(100.dp))
        Image(painter = image, contentDescription = "Logo")
        Spacer(modifier = Modifier.weight(3f))

        Text(text = title.uppercase(), fontSize = 30.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(20.dp))

        Text(text = subtitle, fontSize = 16.sp, color = Color.Gray)
        Spacer(modifier = Modifier.height(60.dp))

        OnboardingIndicator(state = state, modifier = Modifier.fillMaxWidth(0.4f))
        Spacer(modifier = Modifier.weight(2f))

        Button(onClick = { state.goNext() }, colors = ButtonDefaults.buttonColors(contentColor = Color(10,150,10,255), containerColor = Color.White)) {
            Text(buttonText)
        }

        Spacer(modifier = Modifier.height(100.dp))

    }
}

@Composable
fun OnboardingIndicator(modifier: Modifier = Modifier, state: OnboardingState) {
    Row(modifier = modifier) {
        repeat(state.numScreens) {
            val weight = if (it == state.currentScreen) 3f else 1f
            val color = if (it == state.currentScreen) Color(10,200,10,255) else Color.LightGray
            Box(modifier = Modifier
                .padding(horizontal = 2.dp)
                .clip(RoundedCornerShape(50))
                .background(color)
                .height(8.dp)
                .weight(weight)
                .clickable {
                    state.goTo(it)
                }
            )
        }
    }
}