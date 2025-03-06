package com.tdiego.composechallenges.swipe_to_reply_card

import androidx.compose.animation.core.exponentialDecay
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.gestures.AnchoredDraggableState
import androidx.compose.foundation.gestures.DraggableAnchors
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.anchoredDraggable
import androidx.compose.foundation.gestures.animateTo
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.flow.collectLatest
import kotlin.math.roundToInt

enum class DragAnchors { Resting, Dragging }

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SwipeToReplyCard(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {

    val density = LocalDensity.current
    val colors = listOf(Color.Red, Color.Gray, Color.Blue, Color.Magenta)
    var cardColor by remember { mutableStateOf(Color.Gray) }

    val draggableState = remember {
        AnchoredDraggableState(
            initialValue = DragAnchors.Resting,
            positionalThreshold = { distance -> distance * 0.5f },
            velocityThreshold = { with(density) { 100.dp.toPx() } },
            snapAnimationSpec = tween(),
            decayAnimationSpec = exponentialDecay()
        )
    }

    SideEffect {
        draggableState.updateAnchors(
            DraggableAnchors {
                DragAnchors.Resting at 0f
                DragAnchors.Dragging at 150f
            }
        )
    }

    // Animates the card back to its original position after dragging
    LaunchedEffect(draggableState) {
        snapshotFlow { draggableState.settledValue }
            .collectLatest {
                if (it == DragAnchors.Dragging) {
                    draggableState.animateTo(DragAnchors.Resting)
                    cardColor = colors.filter { c -> c != cardColor }.random()
                }
            }
    }


    Box(
        modifier = modifier
            .height(100.dp)
            .padding(16.dp),
        contentAlignment = Alignment.CenterStart
    ) {

        // Reply icon under the card
        Box(
            modifier = modifier
                .background(Color(0xFFB6B6B6), CircleShape)
                .padding(5.dp),
            contentAlignment = Alignment.CenterStart
        ) {
            Icon(
                imageVector = Icons.Filled.Refresh,
                contentDescription = "",
                tint = Color.White
            )
        }

        // Main card
        Box(
            modifier = modifier
                .anchoredDraggable(
                    draggableState,
                    orientation = Orientation.Horizontal,
                )
                .offset {
                    IntOffset(
                        x = draggableState
                            .requireOffset()
                            .roundToInt(), y = 0
                    )
                }
                .fillMaxHeight()
                .clip(
                    RoundedCornerShape(
                        topStart = 14.dp, topEnd = 20.dp, bottomEnd = 20.dp, bottomStart = 6.dp
                    )
                )
                .background(cardColor)
                .combinedClickable(
                    onClick = { },
                    indication = ripple(),
                    interactionSource = remember { MutableInteractionSource() }
                ),
            contentAlignment = Alignment.CenterStart
        ) {
            // Card content
            content()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SwipeToReplyCardPreview() {
    Scaffold {
        SwipeToReplyCard(
            modifier = Modifier.padding(it),
            content = {
                Text(
                    modifier = Modifier.padding(horizontal = 24.dp),
                    text = "Swipe right to reply at this message!",
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        )
    }
}