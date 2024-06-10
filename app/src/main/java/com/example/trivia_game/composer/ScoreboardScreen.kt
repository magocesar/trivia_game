package com.example.trivia_game.composer

import android.content.Intent
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.trivia_game.GameActivity
import com.example.trivia_game.R
import com.example.trivia_game.room.user.User
import com.example.trivia_game.view_model.ScoreboardViewModel

@Composable
fun ScoreboardScreen(vm : ScoreboardViewModel, username : String) {
    val context = LocalContext.current

    val users = remember {
        mutableStateOf(listOf<User>())
    }

    val points = remember {
        mutableIntStateOf(0)
    }

    LaunchedEffect(vm.topUsers) {
        vm.topUsers.observeForever {
            users.value = it
        }
    }

    LaunchedEffect(vm.userPoints) {
        vm.userPoints.observeForever {
            points.intValue = it
        }
    }

    LaunchedEffect(true) {
        vm.getTopUsers()
        vm.getPointsByUsername(username)
    }

    Box(
        modifier = Modifier.fillMaxSize().background(Color.Yellow),
        contentAlignment = Alignment.Center,
    ){
        Column (
            modifier = Modifier.fillMaxWidth().padding(top = 20.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Text(
                text = "Trivia Game Scoreboard",
                modifier = Modifier.padding(8.dp),
                style = androidx.compose.ui.text.TextStyle(fontSize = 24.sp)
            )

            Spacer(modifier = Modifier.height(10.dp))

            val image : Painter = painterResource(id = R.drawable.scoreboard_logo)
            Image(painter = image, contentDescription = "Descriptive text")

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = "Username :$username",
                modifier = Modifier.padding(8.dp),
                style = androidx.compose.ui.text.TextStyle(fontSize = 18.sp)
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = "Points : " + points.intValue.toString(),
                modifier = Modifier.padding(8.dp),
                style = androidx.compose.ui.text.TextStyle(fontSize = 18.sp)
            )

            Spacer(modifier = Modifier.height(10.dp))

            Button(
                onClick = {
                    val intent = Intent(context, GameActivity::class.java)
                    intent.putExtra("username", username)
                    context.startActivity(intent)
                },
                shape = RectangleShape,
                colors = ButtonDefaults.buttonColors(
                    Color.Blue,
                    contentColor = Color.White
                ),
                modifier = Modifier
                    .width(200.dp)
                    .height(50.dp)
            ) {
                Text(text = "Back to Main Menu")
            }

            Spacer(modifier = Modifier.height(20.dp))

            UserList(users = users.value)

            Spacer(modifier = Modifier.height(10.dp))

        }
    }
}

@Composable
fun UserItem(user : User){
    Row(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ){
        Text(text = "Username : " + user.username, modifier = Modifier.weight(1f))
        Text(text = "Points : " + user.points.toString(), modifier = Modifier.weight(1f))
    }
}

@Composable
fun UserList(users: List<User>) {
    LazyColumn {
        items(users.size) { index ->
            UserItem(user = users[index])
        }
    }
}