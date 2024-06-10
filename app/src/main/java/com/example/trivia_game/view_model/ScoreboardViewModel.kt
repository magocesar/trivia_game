package com.example.trivia_game.view_model

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.trivia_game.room.user.User
import kotlinx.coroutines.launch

class ScoreboardViewModel(application: Application) : BaseViewModel(application) {

    val topUsers = MutableLiveData<List<User>>()
    val userPoints = MutableLiveData<Int>()

    fun getTopUsers() {
        viewModelScope.launch {
            val users = database.userDao().getTopUsers()
            if(users.isNotEmpty()){
                topUsers.postValue(users)
            }else{
                Log.d("ScoreboardViewModel", "No users found")
            }
            for(user in users){
                Log.d("ScoreboardViewModel", "User: ${user.username} Score: ${user.points}")
            }
        }
    }

    fun getPointsByUsername(username: String) {
        viewModelScope.launch {
            val user = database.userDao().getUserByUsername(username)
            if(user != null){
                userPoints.postValue(user.points)
            }else{
                Log.d("ScoreboardViewModel", "No user found")
            }
        }
    }
}