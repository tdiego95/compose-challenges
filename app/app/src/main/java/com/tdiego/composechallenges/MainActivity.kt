package com.tdiego.composechallenges

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.tdiego.composechallenges.shimmering_card.ShimmeringCard

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            App()
        }
    }
}

@Composable
fun App() {
    Scaffold(modifier = Modifier.fillMaxSize()) { p ->
        Column {
            repeat(4) {
                ShimmeringCard(Modifier.padding(p))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AppPreview() {
    App()
}