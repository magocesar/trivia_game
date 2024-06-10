package com.example.trivia_game.view_model

import android.app.Application
import android.util.Log
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.trivia_game.api.RetrofitClient
import kotlinx.coroutines.launch

class GameActivityViewModel(application: Application) : BaseViewModel(application) {
    val question = MutableLiveData<String>()
    val option1 = MutableLiveData<String>()
    val option2 = MutableLiveData<String>()
    val option3 = MutableLiveData<String>()
    val option4 = MutableLiveData<String>()

    val option1Color = MutableLiveData<Color>(Color.Blue)
    val option2Color = MutableLiveData<Color>(Color.Blue)
    val option3Color = MutableLiveData<Color>(Color.Blue)
    val option4Color = MutableLiveData<Color>(Color.Blue)

    private val buttonsEnabled = MutableLiveData<Boolean>(true)

    private lateinit var correctAnswer : String
    private lateinit var incorrectAnswers : List<String>

    fun getQuestionsFromApi(){

        //Reset the colors of the options
        option1Color.value = Color.Blue
        option2Color.value = Color.Blue
        option3Color.value = Color.Blue
        option4Color.value = Color.Blue

        //Enable the buttons
        buttonsEnabled.value = true

        viewModelScope.launch {
            val triviaQuestionList = RetrofitClient.getTriviaQuestion()
            correctAnswer = triviaQuestionList[0].correctAnswer
            incorrectAnswers = triviaQuestionList[0].incorrectAnswers

            // Randomize the order of the answers
            val answers = mutableListOf<String>()
            answers.add(correctAnswer)
            answers.addAll(incorrectAnswers)
            answers.shuffle()

            question.postValue(triviaQuestionList[0].question.text)
            option1.postValue(answers[0])
            option2.postValue(answers[1])
            option3.postValue(answers[2])
            option4.postValue(answers[3])
        }
    }

    fun checkAnswer(answer : String, username: String){

        if(!buttonsEnabled.value!!){
            return
        }

        viewModelScope.launch{
            val user = database.userDao().getUserByUsername(username?:"")
            if(user != null){
                if(answer == correctAnswer) {
                    user.addPoints(10, 1)
                }else{
                    user.subtractPoints(10)
                }
                database.userDao().update(user)
            }
            Log.d("GameActivityViewModel", "User points: ${user?.points}, User username: ${user?.username}")
        }

        option1Color.value = if (option1.value == correctAnswer) Color.Green else Color.Red
        option2Color.value = if (option2.value == correctAnswer) Color.Green else Color.Red
        option3Color.value = if (option3.value == correctAnswer) Color.Green else Color.Red
        option4Color.value = if (option4.value == correctAnswer) Color.Green else Color.Red

        buttonsEnabled.value = false
    }
}