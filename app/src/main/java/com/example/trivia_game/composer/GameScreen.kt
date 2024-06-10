package com.example.trivia_game.composer

import android.content.Intent
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import com.example.trivia_game.R
import com.example.trivia_game.ScoreboardActivity
import com.example.trivia_game.view_model.GameActivityViewModel
import kotlin.random.Random

@Composable
fun GameScreen(vm : GameActivityViewModel, username : String){

    val context = LocalContext.current
    val question = remember { mutableStateOf("") }
    val option1 = remember { mutableStateOf("") }
    val option2 = remember { mutableStateOf("") }
    val option3 = remember { mutableStateOf("") }
    val option4 = remember { mutableStateOf("") }

    val option1Color = remember { mutableStateOf(Color.Blue) }
    val option2Color = remember { mutableStateOf(Color.Blue) }
    val option3Color = remember { mutableStateOf(Color.Blue) }
    val option4Color = remember { mutableStateOf(Color.Blue) }

    val images = listOf(
        R.drawable.one,
        R.drawable.two,
        R.drawable.three,
        R.drawable.four,
        R.drawable.five,
        R.drawable.six,
        R.drawable.seven,
        R.drawable.eight
    )

    val imageIndex = remember { mutableIntStateOf(Random.nextInt(0, 8)) }

    LaunchedEffect(vm.question) {
        vm.question.observeForever {
            question.value = it
            imageIndex.intValue = (imageIndex.intValue + 1) % images.size
        }
    }

    LaunchedEffect(vm.option1) {
        vm.option1.observeForever {
            option1.value = it
        }
    }

    LaunchedEffect(vm.option2) {
        vm.option2.observeForever {
            option2.value = it
        }
    }

    LaunchedEffect(vm.option3) {
        vm.option3.observeForever {
            option3.value = it
        }
    }

    LaunchedEffect(vm.option4) {
        vm.option4.observeForever {
            option4.value = it
        }
    }

    LaunchedEffect(vm.option1Color) {
        vm.option1Color.observeForever {
            option1Color.value = it
        }
    }

    LaunchedEffect(vm.option2Color) {
        vm.option2Color.observeForever {
            option2Color.value = it
        }
    }

    LaunchedEffect(vm.option3Color) {
        vm.option3Color.observeForever {
            option3Color.value = it
        }
    }

    LaunchedEffect(vm.option4Color) {
        vm.option4Color.observeForever {
            option4Color.value = it
        }
    }

    LaunchedEffect(key1 = Unit) {
        vm.getQuestionsFromApi()
    }

    val imageResource: Int by animateIntAsState(targetValue = images[imageIndex.intValue], label = "img")

    Box(
        modifier = Modifier.fillMaxSize().background(Color.Yellow),
        contentAlignment = Alignment.Center
    ){
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Trivia Game",
                style = TextStyle(fontSize = 30.sp),
            )

            Spacer(modifier = Modifier.height(16.dp))

            androidx.compose.animation.Crossfade(targetState = images[imageIndex.intValue],
                label = "img"
            ) { selectedImage ->
                Image(painter = painterResource(id = selectedImage), contentDescription = "Logo Trivia Game")
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = question.value?:"",
                style = TextStyle(fontSize = 20.sp),
                modifier = Modifier.padding(16.dp),
                textAlign = TextAlign.Justify

            )

            Spacer(modifier = Modifier.height(16.dp))


            Column {
                Row(
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Button(
                        onClick = { vm.checkAnswer(option1.value?:"", username)},
                        shape = RectangleShape,
                        colors = ButtonDefaults.buttonColors(
                            option1Color.value,
                            contentColor = Color.White
                        ),
                        modifier = Modifier.width(150.dp).height(50.dp)
                    ) {
                        Text(option1.value?:"")
                    }
                    Button(
                        onClick = {vm.checkAnswer(option2.value?:"", username)},
                        shape = RectangleShape,
                        colors = ButtonDefaults.buttonColors(
                            option2Color.value,
                            contentColor = Color.White
                        ),
                        modifier = Modifier.width(150.dp).height(50.dp)
                    ) {
                        Text(option2.value?:"")
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Button(
                        onClick = { vm.checkAnswer(option3.value?:"", username)},
                        shape = RectangleShape,
                        colors = ButtonDefaults.buttonColors(
                            option3Color.value,
                            contentColor = Color.White
                        ),
                        modifier = Modifier.width(150.dp).height(50.dp)
                    ) {
                        Text(option3.value?:"")
                    }
                    Button(
                        onClick = { vm.checkAnswer(option4.value?:"", username)},
                        shape = RectangleShape,
                        colors = ButtonDefaults.buttonColors(
                            option4Color.value,
                            contentColor = Color.White
                        ),
                        modifier = Modifier.width(150.dp).height(50.dp)
                    ) {
                        Text(option4.value?:"")
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row (
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.fillMaxWidth()
            ){
                Button(
                    onClick = { vm.getQuestionsFromApi() },
                    shape = RectangleShape,
                    colors = ButtonDefaults.buttonColors(
                        Color.Blue,
                        contentColor = Color.White
                    ),
                    modifier = Modifier.width(150.dp).height(50.dp)
                ) {
                    Text(text = "Next Question")
                }
                Button(
                    onClick = {
                        val intent = Intent(context, ScoreboardActivity::class.java)
                        intent.putExtra("username", username)
                        ContextCompat.startActivity(context, intent, null)
                    },
                    shape = RectangleShape,
                    colors = ButtonDefaults.buttonColors(
                        Color.Blue,
                        contentColor = Color.White
                    ),
                    modifier = Modifier.width(150.dp).height(50.dp)
                ) {
                    Text(text = "Top Scores")
                }
            }
        }
    }
}

