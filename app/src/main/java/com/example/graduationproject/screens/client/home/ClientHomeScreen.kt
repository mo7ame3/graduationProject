package com.example.graduationproject.screens.client.home

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.graduationproject.components.BottomBar
import com.example.graduationproject.components.DrawerBody
import com.example.graduationproject.components.DrawerHeader
import com.example.graduationproject.components.InternetCraftName
import com.example.graduationproject.components.InternetCraftPhoto
import com.example.graduationproject.components.TopMainBar
import com.example.graduationproject.constant.Constant.adminCraftList
import com.example.graduationproject.data.GoogleDriveList
import com.example.graduationproject.navigation.AllScreens
import com.example.graduationproject.screens.client.order.ClientOrderScreen
import com.example.graduationproject.screens.sharedScreens.chat.ChatList
import com.example.graduationproject.sharedpreference.SharedPreference
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ClientHomeScreen(navController: NavController, route: String) {

    //Log out Variables
    val context = LocalContext.current
    val sharedPreference = SharedPreference(context)

    val home = remember {
        mutableStateOf("home")
    }
    val title = remember {
        mutableStateOf("خدماتي")
    }
    // from order offer
    val isFirst = remember {
        mutableStateOf(true)
    }
    if (route == "home" || route == "chat" || route == "order") {
        if (isFirst.value) {
            home.value = route
            isFirst.value = false
            title.value =
                if (route == "home") "خدماتي" else if (route == "chat") "المحادثات" else "طلباتي"
        }
    }

    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()
    Scaffold(
        drawerContent = {
            DrawerHeader()
            Spacer(modifier = Modifier.height(50.dp))
            DrawerBody(isClient = true) {
                if (it.title == "طلباتي") {
                    scope.launch {
                        home.value = "order"
                        scaffoldState.drawerState.close()
                    }
                }

                if (it.title == "أحمد محمد") {
                    //Navigate to Profile
                    scope.launch {
                        navController.navigate(route = AllScreens.ClientProfileScreen.name + "/${false}/${false}/${false}/ ")
                        scaffoldState.drawerState.close()
                    }
                }
                if (it.title == "إعدادات حسابي") {
                    scope.launch {
                        navController.navigate(route = AllScreens.ClientProfileSettingsScreen.name)
                        scaffoldState.drawerState.close()
                    }
                }
                if (it.title == "مكتملة") {
                    // nav to profile
                    scope.launch {
                        navController.navigate(route = AllScreens.ClientProfileScreen.name + "/${false}/${true}/${false}/ ")
                        scaffoldState.drawerState.close()
                    }
                }
                if (it.title == "تسجيل الخروج") {
                    //Log out and nav to Login
                    scope.launch {
                        sharedPreference.saveState("")
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
        bottomBar = { BottomBar(selected = home, title = title) },
    ) {
        Column {
            if (home.value == "home") {
                Home {
                    //navigate to post
                    navController.navigate(route = AllScreens.ClientPostScreen.name + "/${it.jobName}")
                }
            }
            if (home.value == "order") {
                ClientOrderScreen(navController)
            }
            if (home.value == "chat") {
                ChatList(navController)
            }
        }
    }
}

@Composable
fun Home(
    onClick: (GoogleDriveList) -> Unit
) {
    LazyColumn {
        items(adminCraftList) {
            JobRow(job = it) { job ->
                onClick.invoke(job)
            }
        }
        item {
            Spacer(modifier = Modifier.height(50.dp))
        }
    }
}

@Composable
fun JobRow(job: GoogleDriveList, onClick: (GoogleDriveList) -> Unit) {
    Column(modifier = Modifier
        .clickable {
            onClick.invoke(job)
        }
        .fillMaxSize()
        .padding(10.dp), horizontalAlignment = Alignment.CenterHorizontally) {
        Box {
            Surface(
                elevation = 10.dp,
                shape = RoundedCornerShape(size = 30.dp),
                modifier = Modifier.size(350.dp, 230.dp)
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    InternetCraftPhoto(uri = job.picGoogle)
                    InternetCraftName(jobName = job.jobName)
                }
            }
        }

    }
}
