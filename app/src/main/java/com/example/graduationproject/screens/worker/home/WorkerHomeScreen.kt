package com.example.graduationproject.screens.worker.home

import android.annotation.SuppressLint
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
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
import com.example.graduationproject.data.WrapperClass
import com.example.graduationproject.model.worker.home.Order
import com.example.graduationproject.model.worker.home.WorkerHome
import com.example.graduationproject.navigation.AllScreens
import com.example.graduationproject.screens.sharedScreens.chat.ChatList
import com.example.graduationproject.screens.worker.myProjects.MyProjects
import com.example.graduationproject.sharedpreference.SharedPreference
import com.example.graduationproject.ui.theme.GrayColor
import com.example.graduationproject.ui.theme.MainColor
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterialScaffoldPaddingParameter", "CoroutineCreationDuringComposition")
@Composable
fun WorkerHomeScreen(
    navController: NavHostController,
    route: String,
    workerHomeViewModel: WorkerHomeViewModel
) {
    //Log out Variables
    val context = LocalContext.current
    val sharedPreference = SharedPreference(context)
    val name = sharedPreference.getName.collectAsState(initial = "")
    val token = sharedPreference.getToken.collectAsState(initial = "")

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


    var loading by remember {
        mutableStateOf(true)
    }
    var exception by remember {
        mutableStateOf(false)
    }

    //state flow list
    val homeList = MutableStateFlow<List<Order>>(emptyList())

    //Until Hind Finish Post Craft Id In Login And Register

    if (token.value.toString().isNotEmpty()) {
        val homeData: WrapperClass<WorkerHome, Boolean, Exception> =
            produceState<WrapperClass<WorkerHome, Boolean, Exception>>(
                initialValue = WrapperClass(data = null)
            ) {
                value = workerHomeViewModel.getHome(
                    authorization = "Bearer ${token.value.toString()}",
                    craftId = "6451481ed17c19136a05327b"
                )
            }.value
        if (homeData.data?.status == "success") {
            if (homeData.data != null) {
                scope.launch {
                    homeList.emit(homeData.data!!.data!!.orders)
                    loading = false
                    exception = false
                }
            }
        } else if (homeData.data?.status == "fail" || homeData.e != null) {
            exception = true
            Toast.makeText(
                context,
                "خطأ في الانترنت",
                Toast.LENGTH_SHORT
            ).show()
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
        if (!loading && !exception) {
            if (home.value == "home") {
                LazyColumn {
                    items(homeList.value) {
                        WorkerHomeRow(item = it) { data ->
//                        Log.d("TAG", "WorkerHomeScreen: ${data.name}")
//                        Log.d("TAG", "WorkerHomeScreen: ${data.problemTitle}")
//                        Log.d("TAG", "WorkerHomeScreen: ${data.problemType}")
//                        Log.d("TAG", "WorkerHomeScreen: ${data.address}")
//                        Log.d("TAG", "WorkerHomeScreen: ${data.time}")
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
        } else if (loading && !exception) {
            CircleProgress()
        } else if (exception) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                IconButton(onClick = {
                    exception = false
                    loading = true
                    scope.launch {
                        val homeData: WrapperClass<WorkerHome, Boolean, Exception> =
                            workerHomeViewModel.getHome(
                                authorization = "Bearer ${token.value.toString()}",
                                craftId = "6451481ed17c19136a05327b"
                            )
                        if (homeData.data?.status == "success") {
                            if (homeData.data != null) {
                                scope.launch {
                                    homeList.emit(homeData.data!!.data?.orders!!)
                                    loading = false
                                    exception = false
                                }
                            }
                        } else if (homeData.data?.status == "fail" || homeData.e != null) {
                            exception = true
                            Toast.makeText(
                                context,
                                "خطأ في الانترنت",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }) {
                    Icon(
                        imageVector = Icons.Default.Refresh, contentDescription = null,
                        modifier = Modifier.size(60.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun WorkerHomeRow(
    item: Order,
    onAction: (Order) -> Unit
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
                GetSmallPhoto(uri = if (item.user.avatar != null) item.user.avatar.toString() else null)
                Spacer(modifier = Modifier.width(10.dp))
                Column {
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = item.user.name, style = TextStyle(
                                color = MainColor,
                                fontSize = 20.sp
                            )
                        )
                        Row {
                            Text(
                                text = item.createdDate, style = TextStyle(
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
                        text = item.title + "- " + item.orderDifficulty, style = TextStyle(
                            color = MainColor,
                            fontSize = 14.sp
                        )
                    )
                    Spacer(modifier = Modifier.height(5.dp))
                    Text(
                        text = item.user.address, style = TextStyle(
                            color = GrayColor,
                            fontSize = 12.sp
                        )
                    )
                }
            }
        }
    }
}









