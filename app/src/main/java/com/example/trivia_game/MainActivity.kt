package com.example.trivia_game

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.trivia_game.composer.HomeScreen
import com.example.trivia_game.view_model.HomeScreenViewModel

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val viewModel = HomeScreenViewModel(application)
            HomeScreen(viewModel)
        }
    }
}