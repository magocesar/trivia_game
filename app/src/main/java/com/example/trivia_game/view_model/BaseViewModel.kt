package com.example.trivia_game.view_model

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.trivia_game.room.user.UserDatabase

open class BaseViewModel(application: Application) : ViewModel(){
    val database = UserDatabase.getDatabase(application)
    val username = MutableLiveData<String>()
}