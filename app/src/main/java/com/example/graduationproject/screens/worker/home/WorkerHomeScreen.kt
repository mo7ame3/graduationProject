package com.example.graduationproject.screens.worker.home

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.graduationproject.R
import com.example.graduationproject.components.*
import com.example.graduationproject.data.WorkerHomeList
import com.example.graduationproject.navigation.AllScreens
import com.example.graduationproject.screens.sharedScreens.chat.ChatList
import com.example.graduationproject.screens.worker.myProjects.MyProjects
import com.example.graduationproject.sharedpreference.SharedPreference
import com.example.graduationproject.ui.theme.GrayColor
import com.example.graduationproject.ui.theme.MainColor
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun WorkerHomeScreen(navController: NavHostController, route: String) {
    //Log out Variables
    val context = LocalContext.current
    val sharedPreference = SharedPreference(context)
    val name = sharedPreference.getName.collectAsState(initial = "")

    val home = remember {
        mutableStateOf("home")
    }
    val title = remember {
        mutableStateOf("خدماتي")
    }
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()

    // from order offer
    val isFirst = remember {
        mutableStateOf(true)
    }
    if (route == "home" || route == "chat" || route == "order") {
        if (isFirst.value) {
            home.value = route
            isFirst.value = false
            title.value =
                if (route == "home") "خدماتي" else if (route == "chat") "المحادثات" else "مشاريعي"
        }
    }

    Scaffold(
        drawerContent = {
            DrawerHeader()
            Spacer(modifier = Modifier.height(50.dp))
            DrawerBody(isClient = false, name = name.value.toString()) {
                if (it.title == "مشاريعي") {
                    scope.launch {
                        Log.d("TAG", "WorkerHomeScreen: ${it.title}")
                        home.value = "order"
                        scaffoldState.drawerState.close()
                    }
                }

                if (it.title == name.value.toString()) {
                    //Navigate to Profile
                    scope.launch {
                        navController.navigate(route = AllScreens.WorkerProfileScreen.name + "/${false}/${false}/ ")
                        scaffoldState.drawerState.close()
                    }
                }
                if (it.title == "إعدادات حسابي") {
                    scope.launch {
                        navController.navigate(route = AllScreens.WorkerProfileSettingsScreen.name)
                        scaffoldState.drawerState.close()
                    }
                }
                if (it.title == "تسجيل الخروج") {
                    //Log out and nav to Login
                    scope.launch {
                        sharedPreference.saveState("")
                        sharedPreference.saveToken("")
                        navController.navigate(route = AllScreens.LoginScreen.name) {
                            navController.popBackStack()
                        }
                    }
                }
            }
        },
        scaffoldState = scaffoldState,
        topBar = {
            TopMainBar(title = title) {
                scope.launch {
                    scaffoldState.drawerState.open()
                }
            }

        },
        bottomBar = { BottomBar(selected = home, title = title, isClient = false) },
    ) {
        if (home.value == "home") {
            LazyColumn {
                items(workerList) {
                    WorkerHomeRow(item = it) { data ->
                        Log.d("TAG", "WorkerHomeScreen: ${data.name}")
                        Log.d("TAG", "WorkerHomeScreen: ${data.problemTitle}")
                        Log.d("TAG", "WorkerHomeScreen: ${data.problemType}")
                        Log.d("TAG", "WorkerHomeScreen: ${data.address}")
                        Log.d("TAG", "WorkerHomeScreen: ${data.time}")
                        navController.navigate(AllScreens.WorkerProblemDetilas.name)
                    }
                    Spacer(modifier = Modifier.height(15.dp))
                }
                item {
                    Spacer(modifier = Modifier.height(50.dp))
                }
            }
        }
        if (home.value == "chat") {
            ChatList(navController = navController)
        }
        //my projects
        if (home.value == "order") {
            MyProjects(navController)
        }
    }
}

@Composable
fun WorkerHomeRow(
    item: WorkerHomeList,
    onAction: (WorkerHomeList) -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .padding(start = 25.dp, end = 25.dp)
            .clickable { onAction(item) },
        border = BorderStroke(width = 1.dp, color = GrayColor),
        shape = RoundedCornerShape(30.dp)
    )
    {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 25.dp, end = 25.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                SmallPhoto()
                Spacer(modifier = Modifier.width(10.dp))
                Column {
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = item.name, style = TextStyle(
                                color = MainColor,
                                fontSize = 20.sp
                            )
                        )
                        Row {
                            Text(
                                text = item.time, style = TextStyle(
                                    color = GrayColor,
                                    fontSize = 12.sp
                                )
                            )
                            Spacer(modifier = Modifier.width(5.dp))
                            Icon(
                                painter = painterResource(id = R.drawable.clock),
                                contentDescription = null,
                                tint = GrayColor,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    }
                    Text(
                        text = item.problemTitle + "- " + item.problemType, style = TextStyle(
                            color = MainColor,
                            fontSize = 14.sp
                        )
                    )
                    Spacer(modifier = Modifier.height(5.dp))
                    Text(
                        text = item.address, style = TextStyle(
                            color = GrayColor,
                            fontSize = 12.sp
                        )
                    )
                }
            }
        }
    }
}


val workerList = listOf(
    WorkerHomeList("أحمد محمد", "تصليح كرسي", "صيانة بسيطة", "مركز الفيوم", "منذ ساعتين"),
    WorkerHomeList("محمد أحمد", "تصليح كرسي", "صيانة بسيطة", "مركز الفيوم", "منذ ساعتين"),
    WorkerHomeList("أحمد محمد", "تصليح كرسي", "صيانة بسيطة", "مركز الفيوم", "منذ ساعتين"),
    WorkerHomeList("رغد محمود", "تصليح كرسي", "صيانة بسيطة", "مركز الفيوم", "منذ ساعتين"),
    WorkerHomeList("عمرو اشرف", "تصليح شباك", "صيانة متوسطة", "مركز الفيوم", "منذ 15 ساعة"),
    WorkerHomeList("هند علي", "تصليح باب", "صيانة بسيطة", "مركز الفيوم", "منذ 18 سساعة"),
    WorkerHomeList("مجدي", "تصليح دولاب", "صيانة متوسطة", "مركز الفيوم", "منذ يوم"),
)







