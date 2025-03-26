package com.tdiego.composechallenges.swipe_to_action_card

import androidx.compose.animation.core.exponentialDecay
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.gestures.AnchoredDraggableState
import androidx.compose.foundation.gestures.DraggableAnchors
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.anchoredDraggable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.math.roundToInt

private enum class DragAnchors { Left, Center, Right }
enum class Action { DELETE, EDIT }

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SwipeToActionCard(
    modifier: Modifier = Modifier,
    onAction: (Action) -> Unit = {},
    content: @Composable () -> Unit,
) {
    val actionSize = 80.dp
    val density = LocalDensity.current

    val draggableState = remember {
        AnchoredDraggableState(
            initialValue = DragAnchors.Center,
            positionalThreshold = { distance -> distance * 0.5f },
            velocityThreshold = { with(density) { actionSize.toPx() } },
            snapAnimationSpec = tween(),
            decayAnimationSpec = exponentialDecay()
        )
    }

    SideEffect {
        val actionSizePx = with(density) { actionSize.toPx() }

        draggableState.updateAnchors(
            DraggableAnchors {
                DragAnchors.Left at -actionSizePx
                DragAnchors.Center at 0f
                DragAnchors.Right at actionSizePx
            }
        )
    }

    Box(
        modifier = modifier.height(actionSize),
        contentAlignment = Alignment.CenterStart
    ) {

        // Actions
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Delete Button
            Box(
                modifier = modifier
                    .size(actionSize)
                    .background(Color(0xFFE53935))
                    .clickable { onAction(Action.DELETE) },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Filled.Delete,
                    contentDescription = "",
                    tint = Color.White
                )
            }

            // Delete Button
            Box(
                modifier = modifier
                    .size(actionSize)
                    .background(Color(0xFFF9A825))
                    .clickable { onAction(Action.EDIT) },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Filled.Edit,
                    contentDescription = "",
                    tint = Color.White
                )
            }
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
                .fillMaxSize()
                .background(Color(0xFF2A2D3A))
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
fun SwipeToActionCardPreview() {
    Scaffold {
        SwipeToActionCard(
            modifier = Modifier.padding(it),
            content = {
                Text(
                    modifier = Modifier.padding(horizontal = 24.dp),
                    text = "Swipe to discover actions!",
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        )
    }
}