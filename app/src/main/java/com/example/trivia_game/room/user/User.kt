package com.example.trivia_game.room.user

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(indices = [Index(value = ["username"], unique = true)])
data class User(
    @PrimaryKey(autoGenerate = true) val uid : Int = 0,
    var username: String = "",
    var points: Int = 0
) {
    fun addPoints(pointsToAdd : Int, multiplier : Int){
        points += pointsToAdd * multiplier
    }

    fun subtractPoints(pointsToSubtract : Int){
        if(points >= 10){
            points -= pointsToSubtract

        }
    }
}