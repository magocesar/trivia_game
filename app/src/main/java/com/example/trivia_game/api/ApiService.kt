package com.example.trivia_game.api

import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("questions")
    suspend fun getTriviaQuestion(
        @Query("categories") categories : String = "geography",
        @Query("limit") limit : Int = 1
    ) : List<TriviaQuestion>
}