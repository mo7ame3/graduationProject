package com.example.graduationproject.screens.client.rate

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarBorder
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.graduationproject.components.LoginButton
import com.example.graduationproject.components.SmallPhoto
import com.example.graduationproject.navigation.AllScreens
import com.example.graduationproject.ui.theme.GoldColor
import com.example.graduationproject.ui.theme.MainColor
import com.example.graduationproject.ui.theme.RedColor

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ClientRateScreen(navController: NavHostController) {
    Scaffold(topBar = {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // from data base
            Text(
                text = "تصليح كرسي",
                modifier = Modifier.padding(top = 15.dp),
                style = TextStyle(fontSize = 18.sp, color = MainColor)
            )
        }
    }) {
        Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
            Card(
                shape = RoundedCornerShape(size = 25.dp),
                elevation = 2.dp,
                modifier = Modifier.size(350.dp, 400.dp),
                border = BorderStroke(width = 1.dp, color = Color.Gray)
            ) {
                //Profile Name and Bio Column
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(top = 50.dp, bottom = 15.dp),
                    verticalArrangement = Arrangement.SpaceBetween
                )
                {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        // URI from DataBase
                        SmallPhoto()
                        Spacer(modifier = Modifier.width(15.dp))
                        // from data base
                        Text(
                            text = "أحمد محمد",
                            style = TextStyle(
                                color = MainColor,
                                fontSize = 18.sp
                            ),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        Spacer(modifier = Modifier.width(5.dp))
                        // from data base
                        Text(
                            text = "نجار محترف لمدة عامين",
                            style = TextStyle(color = RedColor, fontSize = 16.sp),
                            overflow = TextOverflow.Ellipsis,
                            maxLines = 1
                        )

                    }
                    //Rate Stars
                    val num = remember {
                        mutableStateOf(-1)
                    }
                    Row {
                        Star(index = 1, num = num) { num.value = 1 }
                        Star(index = 2, num = num) { num.value = 2 }
                        Star(index = 3, num = num) { num.value = 3 }
                        Star(index = 4, num = num) { num.value = 4 }
                        Star(index = 5, num = num) { num.value = 5 }
                    }

                    //Buttons
                    Row(
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        modifier = Modifier.fillMaxWidth()
                    ) {

                        LoginButton(
                            isLogin = true,
                            label = "ارسل",
                            modifier = Modifier
                                .width(120.dp)
                                .height(45.dp)
                        ) {
                            if (num.value > 0)
                                navController.navigate(route = AllScreens.ClientHomeScreen.name + "/{}")
                        }
                        LoginButton(
                            isLogin = false,
                            label = "الغاء",
                            modifier = Modifier
                                .width(120.dp)
                                .height(45.dp)
                        ) {
                            navController.navigate(route = AllScreens.ClientHomeScreen.name + "/{}")

                        }
                    }
                }

            }
        }
    }
}

@Composable
fun Star(
    num: MutableState<Int>,
    index: Int,
    onAction: () -> Unit

) {
    // val icon = if (selected) Icons.Default.Star else Icons.Default.StarBorder

    IconButton(onClick = { onAction.invoke() }) {
        Icon(
            imageVector = if (num.value >= index) Icons.Default.Star else Icons.Default.StarBorder,
            contentDescription = null,
            modifier = Modifier.size(50.dp),
            tint = GoldColor
        )
    }
}





