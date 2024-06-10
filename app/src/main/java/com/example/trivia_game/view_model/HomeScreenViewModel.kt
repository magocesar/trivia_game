package com.example.trivia_game.view_model

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.trivia_game.room.user.User
import kotlinx.coroutines.launch

class HomeScreenViewModel(application: Application) : BaseViewModel(application){
    val errorMessage = MutableLiveData<String>()
    val isLoggedIn = MutableLiveData<Boolean>()

    fun updateUsername(newUsername: String){
        if(newUsername.isEmpty()){
            errorMessage.value = "Please enter a username"
        } else {
            errorMessage.value = ""
            username.value = newUsername
        }
    }

    fun submit() {
        if(checkUsername()){
            login()
        }else{
            errorMessage.value = "Please enter a username"
        }
    }

    private fun login(){
        viewModelScope.launch {
            val user = database.userDao().getUserByUsername(username.value?:"")
            if(user == null){
                //Create a new user
                val newUser = User(username = username.value?: "")
                database.userDao().insert(newUser)
            }
            isLoggedIn.value = true
        }
    }

    private fun checkUsername() : Boolean {
        if(username.value.isNullOrEmpty()){
            errorMessage.value = "Please enter a username"
            return false
        }
        return true
    }
}