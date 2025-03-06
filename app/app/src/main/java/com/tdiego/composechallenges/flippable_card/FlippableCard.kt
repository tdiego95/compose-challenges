package com.tdiego.composechallenges.flippable_card

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun FlippableCard(modifier: Modifier = Modifier) {

    var isFlipped by remember { mutableStateOf(false) }

    val angle by animateFloatAsState(
        targetValue = if (isFlipped) 180f else 0f,
        animationSpec = tween(
            durationMillis = 1000,
            easing = FastOutSlowInEasing
        ),
        label = ""
    )

    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(90.dp)
            .padding(horizontal = 24.dp)
            .graphicsLayer(
                rotationY = angle,
                cameraDistance = 20f,
                scaleX = if (angle > 90f) -1f else 1f // ROTATES THE CONTENT OF THE BACK SIDE
            )
            .combinedClickable(
                onClick = { isFlipped = !isFlipped },
                indication = ripple(),
                interactionSource = remember { MutableInteractionSource() }
            ),
        colors = CardDefaults.cardColors(
            containerColor = if (angle < 90) Color(0xFF42A5F5)
            else Color(0xFF37474F)
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        if (angle > 90) BackSide()
        else FrontSide()
    }
}

@Composable
fun FrontSide() {
    Row(
        modifier = Modifier.fillMaxSize(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = Icons.Default.Info,
            contentDescription = "",
            tint = Color.White
        )

        Spacer(Modifier.width(16.dp))

        Text(
            text = "Did you know?",
            fontSize = 22.sp,
            fontStyle = FontStyle.Italic,
            fontWeight = FontWeight.Bold,
            color = Color.White)
    }
}

@Composable
fun BackSide() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Jetpack Compose is \uD83D\uDD25",
            fontSize = 22.sp,
            fontStyle = FontStyle.Italic,
            fontWeight = FontWeight.Bold,
            color = Color.White)
    }
}

@Preview(showBackground = true)
@Composable
fun FlippableCardPreview() {
    Scaffold {
        Box(modifier = Modifier
            .fillMaxSize()
            .padding(it),
            contentAlignment = Alignment.Center
        ) {
            FlippableCard()
        }
    }
}