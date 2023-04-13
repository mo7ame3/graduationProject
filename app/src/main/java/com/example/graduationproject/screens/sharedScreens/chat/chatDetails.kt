package com.example.graduationproject.screens.sharedScreens.chat

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.graduationproject.R
import com.example.graduationproject.components.SmallPhoto
import com.example.graduationproject.ui.theme.MainColor

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ChatDetails(navController: NavHostController) {
    Scaffold(topBar = {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .padding(start = 20.dp, end = 20.dp)
                    .fillMaxWidth()
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    SmallPhoto()
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = "Ahmed Mohamed",
                        style = TextStyle(color = MainColor, fontSize = 15.sp)
                    )
                }
                IconButton(onClick = { }) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = null,
                        tint = MainColor
                    )
                }
            }
        }
    }) {
        Column(verticalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxSize()) {
            Column {
                Text(text = "dfsggddg")
                Text(text = "dfsggddg")
                Text(text = "dfsggddg")
                Text(text = "dfsggddg")
                Text(text = "dfsggddg")
                Text(text = "dfsggddg")
                Text(text = "dfsggddg")
                Text(text = "dfsggddg")
                Text(text = "dfsggddg")
                Text(text = "dfsggddg")
                Text(text = "dfsggddg")
                Text(text = "dfsggddg")
                Text(text = "dfsggddg")
                Text(text = "dfsggddg")
                Text(text = "dfsggddg")
                Text(text = "dfsggddg")
                Text(text = "dfsggddg")
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 25.dp, end = 25.dp, bottom = 10.dp)
                    .background(Color.Transparent)
            ) {
                val value = remember {
                    mutableStateOf("")
                }
                Surface(
                    shape = RoundedCornerShape(25.dp),
                    border = BorderStroke(width = 1.dp, color = Color.Gray),
                    modifier = Modifier
                        .fillMaxWidth(0.8f)
                ) {
                    OutlinedTextField(
                        value = value.value, onValueChange = {
                            value.value = it
                        }, modifier = Modifier.fillMaxWidth(),
                        maxLines = 5,
                        colors = TextFieldDefaults.textFieldColors(
                            backgroundColor = Color.White

                        )
                    )
                }
                IconButton(onClick = { }) {
                    Icon(
                        painter = painterResource(id = R.drawable.share),
                        contentDescription = null,
                        tint = MainColor,
                        modifier = Modifier.size(35.dp)
                    )
                }
            }
        }

    }


}