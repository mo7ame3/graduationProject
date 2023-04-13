package com.example.graduationproject.screens.worker.myProjects

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.graduationproject.R
import com.example.graduationproject.components.LoginButton
import com.example.graduationproject.components.SmallPhoto
import com.example.graduationproject.data.MyCraftOrderData
import com.example.graduationproject.navigation.AllScreens
import com.example.graduationproject.ui.theme.GrayColor
import com.example.graduationproject.ui.theme.MainColor

@Composable
fun MyProjects(navController: NavController) {

    val toggleButton = remember {
        mutableStateOf(true)
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 35.dp, end = 35.dp, top = 20.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            LoginButton(isLogin = toggleButton.value, label = "قيد التنفيذ") {
                toggleButton.value = true
            }
            LoginButton(isLogin = !toggleButton.value, label = "مشاريع لم تبدأ") {
                toggleButton.value = false
            }
        }
        if (toggleButton.value) {
            LazyColumn(modifier = Modifier.padding(top = 20.dp)) {
                items(underwayList) {
                    UnderwayRow(item = it, navController = navController) {
                        navController.navigate(route = AllScreens.MyProjectProblemDetails.name)
                    }
                    Spacer(modifier = Modifier.height(20.dp))
                }
                item {
                    Spacer(modifier = Modifier.height(50.dp))
                }
            }
        } else {
            LazyColumn(modifier = Modifier.padding(top = 20.dp)) {
                items(underwayList) {
                    ProjectsDidNotStartYet(item = it,
                        navController = navController,
                        onAcceptAction = {
                            // Add to accept in database
                        },
                        onRejectAction = {
                            // Remove from database
                        }) {
                        navController.navigate(route = AllScreens.MyProjectProblemDetails.name)
                    }
                    Spacer(modifier = Modifier.height(20.dp))
                }
                item {
                    Spacer(modifier = Modifier.height(50.dp))
                }
            }
        }
    }
}


@Composable
fun UnderwayRow(
    item: MyCraftOrderData, navController: NavController, onAction: (MyCraftOrderData) -> Unit

) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp)
            .clickable {
                onAction.invoke(item)
            }, elevation = 5.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 10.dp, end = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            SmallPhoto()
            Column {
                Text(
                    text = item.clientName.toString(), style = TextStyle(
                        fontSize = 20.sp, fontWeight = FontWeight.SemiBold, color = MainColor
                    )
                )
                Spacer(modifier = Modifier.height(5.dp))
                Text(
                    text = item.problemTitle + "- " + item.problemType, style = TextStyle(
                        fontSize = 14.sp, color = MainColor
                    )
                )
                Spacer(modifier = Modifier.height(5.dp))
                Text(
                    text = item.address.toString(), style = TextStyle(
                        fontSize = 12.sp, color = GrayColor
                    )
                )
                Spacer(modifier = Modifier.height(5.dp))
            }
            //Action of Chat Button When Chat is Done
            Surface(
                modifier = Modifier
                    .size(60.dp)
                    .clickable {
                        navController.navigate(route = AllScreens.WorkerHomeScreen.name + "/chat") {
                            navController.popBackStack()
                        }
                    }, color = Color.Transparent
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {

                    Icon(
                        painter = painterResource(id = R.drawable.chat),
                        contentDescription = null,
                        modifier = Modifier.size(40.dp),
                        tint = MainColor
                    )
                }

            }
        }
    }
}

@Composable
fun ProjectsDidNotStartYet(
    item: MyCraftOrderData,
    navController: NavController,
    onAcceptAction: (MyCraftOrderData) -> Unit,
    onRejectAction: (MyCraftOrderData) -> Unit,
    onAction: (MyCraftOrderData) -> Unit,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp)
            .clickable {
                onAction.invoke(item)
            }, elevation = 5.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 10.dp, end = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            SmallPhoto()
            Column {
                Text(
                    text = item.clientName.toString(), style = TextStyle(
                        fontSize = 20.sp, fontWeight = FontWeight.SemiBold, color = MainColor
                    )
                )
                Spacer(modifier = Modifier.height(5.dp))
                Text(
                    text = item.problemTitle + "- " + item.problemType, style = TextStyle(
                        fontSize = 14.sp, color = MainColor
                    )
                )
                Spacer(modifier = Modifier.height(5.dp))
                Text(
                    text = item.address.toString(), style = TextStyle(
                        fontSize = 12.sp, color = GrayColor
                    )
                )
                Spacer(modifier = Modifier.height(10.dp))
                Row {
                    //Accept Button
                    LoginButton(isLogin = true, label = "قبول", modifier = Modifier.width(65.dp)) {
                        onAcceptAction.invoke(item)
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    //Reject Button
                    LoginButton(isLogin = false, label = "رفض", modifier = Modifier.width(65.dp)) {
                        onRejectAction.invoke(item)
                    }
                }
            }
            //Action of Chat Button When Chat is Done
            Surface(
                modifier = Modifier
                    .size(60.dp)
                    .clickable {
                        navController.navigate(route = AllScreens.WorkerHomeScreen.name + "/chat") {
                            navController.popBackStack()
                        }
                    }, color = Color.Transparent
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {

                    Icon(
                        painter = painterResource(id = R.drawable.chat),
                        contentDescription = null,
                        modifier = Modifier.size(40.dp),
                        tint = MainColor
                    )
                }

            }
        }
    }
}

val underwayList = listOf(
    MyCraftOrderData("أحمد محمد", "تصليح كرسي", "صيانة بسيطة", "", "مركز الفيوم"),
    MyCraftOrderData("أحمد محمد", "تصليح كرسي", "صيانة بسيطة", "", "مركز الفيوم"),
    MyCraftOrderData("أحمد محمد", "تصليح كرسي", "صيانة بسيطة", "", "مركز الفيوم"),
    MyCraftOrderData("أحمد محمد", "تصليح كرسي", "صيانة بسيطة", "", "مركز الفيوم"),
    MyCraftOrderData("أحمد محمد", "تصليح كرسي", "صيانة بسيطة", "", "مركز الفيوم"),
    MyCraftOrderData("أحمد محمد", "تصليح كرسي", "صيانة بسيطة", "", "مركز الفيوم"),
    MyCraftOrderData("أحمد محمد", "تصليح كرسي", "صيانة بسيطة", "", "مركز الفيوم"),
    MyCraftOrderData("أحمد محمد", "تصليح كرسي", "صيانة بسيطة", "", "مركز الفيوم"),
    MyCraftOrderData("أحمد محمد", "تصليح كرسي", "صيانة بسيطة", "", "مركز الفيوم"),
    MyCraftOrderData("أحمد محمد", "تصليح كرسي", "صيانة بسيطة", "", "مركز الفيوم"),
    MyCraftOrderData("أحمد محمد", "تصليح كرسي", "صيانة بسيطة", "", "مركز الفيوم"),
    MyCraftOrderData("أحمد محمد", "تصليح كرسي", "صيانة بسيطة", "", "مركز الفيوم"),
    MyCraftOrderData("أحمد محمد", "تصليح كرسي", "صيانة بسيطة", "", "مركز الفيوم"),
)