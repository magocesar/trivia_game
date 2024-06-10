package com.example.trivia_game.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val BASE_URL = "https://the-trivia-api.com/v2/"

    private val instance : ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }

    suspend fun getTriviaQuestion() : List<TriviaQuestion>{
        return instance.getTriviaQuestion()
    }
}