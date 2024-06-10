package com.example.trivia_game.api

data class TriviaQuestion (
    val correctAnswer : String,
    val incorrectAnswers : List<String>,
    val question : Question
)

data class Question(
    val text : String
)