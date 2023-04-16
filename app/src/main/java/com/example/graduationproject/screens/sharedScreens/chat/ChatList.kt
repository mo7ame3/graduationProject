package com.example.graduationproject.screens.sharedScreens.chat

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.graduationproject.components.SmallPhoto
import com.example.graduationproject.data.ChatData
import com.example.graduationproject.navigation.AllScreens
import com.example.graduationproject.ui.theme.MainColor
import com.example.graduationproject.ui.theme.RedColor

@Composable
fun ChatList(navController: NavController) {
    LazyColumn {
        items(chatList) {
            ChatRow(chatData = it) { job ->
                //nav to chat
                Log.d("job", "ChatList: ${job.name}")
                navController.navigate(route = AllScreens.ChatDetails.name+"/${it.id}")
            }
            Spacer(modifier = Modifier.height(20.dp))
        }
        item {
            Spacer(modifier = Modifier.height(50.dp))
        }
    }
}

@Composable
fun ChatRow(
    chatData: ChatData,
    onClick: (ChatData) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 15.dp, end = 10.dp)
            .clickable {
                onClick.invoke(chatData)
            },
        verticalAlignment = Alignment.CenterVertically,
    ) {
        //Uri is Already selected from data base
        SmallPhoto()
        Spacer(modifier = Modifier.width(10.dp))
        Column {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = chatData.name,
                    style = TextStyle(
                        color = MainColor,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                )
                Spacer(modifier = Modifier.width(5.dp))
                if (chatData.craft != null) {
                    Text(
                        text = chatData.craft,
                        style = TextStyle(
                            color = RedColor,
                            fontSize = 14.sp
                        )
                    )
                }
            }
            if (chatData.message != null) {
                Text(
                    text = chatData.message,
                    style = TextStyle(
                        color = Color.Gray,
                        fontSize = 16.sp
                    ),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
//            Spacer(modifier = Modifier.height(1.dp))
//            Divider(Modifier.fillMaxWidth())
        }
    }
}

val chatList = listOf(
    ChatData(
        id = 1,
        name = "ريشة",
        craft = "كهربائي",
        message = "تمام",
    ),
    ChatData(
        id = 2,
        name = "رزة",
        craft = "محارة",
        message = "ماشي",
    ),
    ChatData(
        id = 3,
        name = "أحمد محمد",
        craft = "نجار",
    ),
    ChatData(
        id = 4,
        name = "جحا",
        craft = "سباك",
        message = "ماشي",
    ),
    ChatData(
        id = 5,
        name = "احمد محمد",
        craft = "عامل بناء",
        message = "تمام يافندم هاجي بكرا الساعه 9 الصبح ياريت بكرا تكون الرمله و الاسمنت جاهزين ",
    ),
    ChatData(
        id = 6,
        name = "جحا",
        craft = "سباك",
        message = "ماشي",
    ),
    ChatData(
        id = 7,
        name = "جحا",
        craft = "سباك",
        message = "ماشي",
    ),
    ChatData(
        id = 8,
        name = "احمد محمد",
        craft = "عامل بناء",
        message = "تمام يافندم هاجي بكرا الساعه 9 الصبح ياريت بكرا تكون الرمله و الاسمنت جاهزين ",
    ),
    ChatData(
        id = 9,
        name = "جحا",
        craft = "سباك",
        message = "ماشي",
    ),
    ChatData(
        id = 10,
        name = "احمد محمد",
        craft = "عامل بناء",
        message = "تمام يافندم هاجي بكرا الساعه 9 الصبح ياريت بكرا تكون الرمله و الاسمنت جاهزين ",
    ),
    ChatData(
        id = 11,
        name = "احمد محمد",
        craft = "عامل بناء",
        message = "تمام يافندم هاجي بكرا الساعه 9 الصبح ياريت بكرا تكون الرمله و الاسمنت جاهزين ",
    ),
    ChatData(
        id = 12,
        name = "جحا",
        craft = "سباك",
        message = "ماشي",
    ),
    ChatData(
        id = 13,
        name = "احمد محمد",
        craft = "عامل بناء",
        message = "تمام يافندم هاجي بكرا الساعه 9 الصبح ياريت بكرا تكون الرمله و الاسمنت جاهزين ",
    ),
    ChatData(
        id = 14,
        name = "احمد محمد",
        craft = "عامل بناء",
        message = "تمام يافندم هاجي بكرا الساعه 9 الصبح ياريت بكرا تكون الرمله و الاسمنت جاهزين ",
    ))