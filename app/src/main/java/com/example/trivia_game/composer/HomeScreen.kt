package com.example.trivia_game.composer

import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import com.example.trivia_game.GameActivity
import com.example.trivia_game.R
import com.example.trivia_game.view_model.HomeScreenViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(vm : HomeScreenViewModel){

    val context = LocalContext.current
    val username = remember { mutableStateOf("") }
    val errorMessage = remember { mutableStateOf("") }

    LaunchedEffect(vm.errorMessage) {
        vm.errorMessage.observeForever {
            errorMessage.value = it
        }
    }

    LaunchedEffect(vm.isLoggedIn){
        vm.isLoggedIn.observeForever{ isLoggedIn ->
            if(isLoggedIn){
                vm.isLoggedIn.postValue(false)
                val intent = Intent(context, GameActivity::class.java)
                intent.putExtra("username", vm.username.value)
                ContextCompat.startActivity(context, intent, null)
            }
        }
    }

    Box(
        modifier = Modifier.fillMaxSize().background(Color.Yellow),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {

            Text(text = "Geography Trivia", style = TextStyle(fontSize = 36.sp))

            Spacer(modifier = Modifier.height(16.dp))

            val image : Painter = painterResource(id = R.drawable.logo_trivia_removebg)
            Image(painter = image, contentDescription = "Logo Trivia Game")

            Spacer(modifier = Modifier.height(16.dp))

            TextField(
                value = username.value,
                onValueChange = {
                    username.value = it
                    vm.updateUsername(it)
                },
                label = { Text("Enter your username", color = Color.Black) },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Done
                ),
                modifier = Modifier.fillMaxWidth(0.8f),
                textStyle = TextStyle(
                    fontSize = 20.sp
                ),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color.Blue,
                    unfocusedBorderColor = Color.Blue,
                    cursorColor = Color.Black
            )

            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = errorMessage.value ?: "",
                color = Color.Red,
                style = TextStyle(fontSize = 16.sp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {vm.submit()},
                modifier = Modifier.fillMaxWidth(0.8f),
                shape = RectangleShape,
                colors = ButtonDefaults.buttonColors(
                    Color.Blue,
                    contentColor = Color.White
                )
            ){
                Text("Start Game")
            }
        }
    }
}
