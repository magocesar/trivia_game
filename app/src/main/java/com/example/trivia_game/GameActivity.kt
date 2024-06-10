package com.example.trivia_game

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.trivia_game.composer.GameScreen
import com.example.trivia_game.view_model.GameActivityViewModel

class GameActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val username = intent.getStringExtra("username")?:""
        setContent {
            val viewModel = GameActivityViewModel(application)
            GameScreen(viewModel, username)
        }
    }
}