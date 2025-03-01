package com.tdiego.composechallenges.loading_button

import android.view.animation.OvershootInterpolator
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

enum class ButtonState { IDLE, LOADING, SUCCESS }

@Composable
fun LoadingButton(modifier: Modifier = Modifier) {

    var buttonState by remember { mutableStateOf(ButtonState.IDLE) }

    val scale by animateFloatAsState(
        label = "ScaleAnimation",
        targetValue = if (buttonState == ButtonState.SUCCESS) 1f else 0f,
        animationSpec = tween(
            durationMillis = 500,
            easing = OvershootInterpolator(4f)::getInterpolation // BOUNCING ANIMATION EFFECT
        )
    )

    Button(
        modifier = modifier.fillMaxWidth().height(60.dp),
        onClick = { buttonState = ButtonState.LOADING },
        colors = ButtonDefaults.buttonColors(
            containerColor = if (buttonState == ButtonState.LOADING) Color(0xFF1565C0)
            else Color(0xFF1976D2)
        )
    ) {

        Crossfade(
            targetState = buttonState,
            label = "",
            modifier = Modifier.fillMaxWidth()
        ) { state ->

            Row(
                modifier = Modifier.fillMaxWidth().height(36.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {

                LaunchedEffect(buttonState) {
                    if (buttonState == ButtonState.LOADING) {
                        delay(3000)
                        buttonState = ButtonState.SUCCESS
                    }

                    if (buttonState == ButtonState.SUCCESS) {
                        delay(2000)
                        buttonState = ButtonState.IDLE
                    }
                }

                when (state) {
                    ButtonState.IDLE -> {
                        Text(
                            text = "Click me!",
                            fontSize = 20.sp
                        )
                    }
                    ButtonState.LOADING -> {
                        CircularProgressIndicator(
                            modifier = Modifier.size(26.dp),
                            color = Color(0xFFBBDEFB),
                            strokeCap = StrokeCap.Round,
                            strokeWidth = 4.dp
                        )
                    }
                    ButtonState.SUCCESS -> {
                        Icon(
                            modifier = Modifier
                                .size(40.dp)
                                .scale(scale) // ANIMATES THE CHECK
                                .graphicsLayer(scaleX = 1.2f, scaleY = 1.2f), // CHANGES ICON THICKNESS
                            imageVector = Icons.Rounded.Check,
                            contentDescription = "",
                            tint = Color.White)
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoadingButtonPreview() {
    Scaffold {
        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
                .padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ){
            LoadingButton(Modifier.size(270.dp, 70.dp))
        }
    }
}