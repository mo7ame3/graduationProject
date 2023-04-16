package com.example.graduationproject.screens.sharedScreens.chat

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.graduationproject.R
import com.example.graduationproject.components.SmallPhoto
import com.example.graduationproject.sharedpreference.SharedPreference
import com.example.graduationproject.ui.theme.MainColor
import com.example.graduationproject.ui.theme.RedColor
import com.example.graduationproject.ui.theme.SecondaryColor

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ChatDetails(navController: NavHostController, id: String) {

    // we should pass 2 ids one for the Sender and the other for the Receiver
    val selectedPersonToChatWith = chatList.filter { t->
        id.toInt() == t.id
    }

    val context = LocalContext.current
    val sharedPreference = SharedPreference(context)
    val chatMassages = remember {
        mutableStateListOf<String>()
    }

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
                    Row( verticalAlignment = Alignment.CenterVertically) {
                        Text(text = selectedPersonToChatWith[0].name, style = TextStyle(color = MainColor, fontSize = 15.sp,fontWeight = FontWeight.SemiBold), fontSize = 18.sp)
                        Text(text = selectedPersonToChatWith[0].craft.toString(), style = TextStyle(color = RedColor, fontSize = 14.sp), modifier = Modifier.padding(start = 5.dp, end = 5.dp) )
                    }
                }
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = null,
                        tint = MainColor
                    )
                }
            }
        }
    }, bottomBar = { BottomChat(chatMassages) }) {
        Column(
            verticalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxSize()
        ) {
            //massages
            LazyColumn(modifier = Modifier.padding(top=15.dp,bottom=15.dp,start=5.dp,end=5.dp)) {
                items(chatMassages) {
                    //Text(text = it)
                    if (sharedPreference.getState.toString().isNotEmpty()) {
                        SenderMassage(text = it)
                    }
                    else {
                        ReceiverMassage(text = it)
                    }
                }
            }
        }
    }
}

@Composable
fun BottomChat(
    chatMassages: MutableList<String>
) {

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
        IconButton(onClick = {
            // send new message
            chatMassages.add(value.value)
            value.value = ""
        }) {
            Icon(
                painter = painterResource(id = R.drawable.share),
                contentDescription = null,
                tint = MainColor,
                modifier = Modifier.size(35.dp)
            )
        }
    }
}

@Composable
fun SenderMassage(text: String) {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Start) {
        Surface(
            shape = RoundedCornerShape(size = 20.dp),
            color = MainColor,
            contentColor = Color.White,
            modifier = Modifier.padding(5.dp)
        ) {
            Text(text = text, modifier = Modifier.padding(5.dp))
        }
    }
}

@Composable
fun ReceiverMassage(text: String) {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
        Surface(
            shape = RoundedCornerShape(size = 20.dp),
            color = SecondaryColor,
            contentColor = Color.White,
            modifier = Modifier.padding(5.dp)
        ) {
            Text(text = text, modifier = Modifier.padding(5.dp))

        }
    }
}