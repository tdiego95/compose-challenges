package com.tdiego.composechallenges.confetti_explosion

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.tooling.preview.Preview
import kotlinx.coroutines.delay
import kotlin.random.Random

data class Confetti(
    var x: Float,
    var y: Float,
    val color: Color,
    var velocityX: Float = 0f,
    var velocityY: Float = 0f
)

@Composable
fun ConfettiExplosion(modifier: Modifier = Modifier) {
    var confettiList by remember { mutableStateOf(List(100) { createConfetti() }) }

    LaunchedEffect(Unit) {
        while (true) {
            confettiList = confettiList.map { confetti ->
                confetti.copy(
                    x = confetti.x + confetti.velocityX,
                    y = confetti.y + confetti.velocityY,
                    velocityY = confetti.velocityY + 0.5f // add 0.5 to simulate gravity effect
                ).takeIf { it.y <= 2000f } ?: createConfetti() // keep the confetti till they are inside the screen, then create a new ones
            }
            delay(16) // simulates a frame rate around 60 FPS
        }
    }

    Canvas(modifier = modifier.fillMaxSize()) {
        confettiList.forEach { confetti ->
            drawCircle(
                color = confetti.color,
                radius = 8f,
                center = Offset(confetti.x, confetti.y),
                style = Fill
            )
        }
    }
}

fun createConfetti(): Confetti {
    return Confetti(
        x = Random.nextFloat() * 1000f, // 1000f simulates the width of the screen
        y = -50f, // just a bit over the top of the screen
        color = Color(Random.nextInt()), // takes a random color using integers
        velocityX = Random.nextFloat() * 20 - 10, // takes a random value between -10.0 and 10.0 (so that confetti can move left and right)
        velocityY = Random.nextFloat() * 10 - 5 // takes a random value between -5.0 and 5.0 (so that confetti can move up and down giving more variety)
    )
}

@Preview(showBackground = true)
@Composable
fun ConfettiExplosionPreview() {
    ConfettiExplosion()
}