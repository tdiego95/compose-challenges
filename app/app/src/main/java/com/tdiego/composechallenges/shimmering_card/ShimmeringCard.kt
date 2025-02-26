package com.tdiego.composechallenges.shimmering_card

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun ShimmeringCard(modifier: Modifier = Modifier) {

    val transition = rememberInfiniteTransition(label = "")
    val animPosition by transition.animateFloat(
        initialValue = -300f,
        targetValue = 800f,
        label = "animPosition",
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 1700,
                easing = FastOutSlowInEasing)
        )
    )

    Card(
        modifier
            .fillMaxWidth()
            .height(150.dp)
            .padding(16.dp)
    ) {
        Row(
            modifier = modifier
                .fillMaxSize()
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(
                            Color(0xFFD6D6D6),
                            Color(0xFFF5F5F5),
                            Color(0xFFD6D6D6)
                        ),
                        start = Offset(animPosition, animPosition / 2),
                        end = Offset(animPosition + 400, animPosition / 2 + 200)
                    )
                )
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Box(
                Modifier
                    .clip(CircleShape)
                    .size(70.dp)
                    .background(Color(0xFFBDBDBD))
            )

            Column {
                 Box(
                     Modifier
                         .padding(horizontal = 16.dp)
                         .fillMaxWidth()
                         .height(18.dp)
                         .clip(RoundedCornerShape(6.dp))
                         .background(Color(0xFFADADAD))
                         .padding(vertical = 2.dp)
                 )

                Spacer(Modifier.height(8.dp))

                Box(
                    Modifier
                        .padding(horizontal = 16.dp)
                        .fillMaxWidth()
                        .height(35.dp)
                        .clip(RoundedCornerShape(6.dp))
                        .background(Color(0xFFBDBDBD))
                        .padding(vertical = 2.dp)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun InfoCardPreview() {
    Scaffold { p ->
        ShimmeringCard(Modifier.padding(p))
    }
}