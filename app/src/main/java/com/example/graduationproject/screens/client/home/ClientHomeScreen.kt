package com.example.graduationproject.screens.client.home

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
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
import com.example.graduationproject.constant.Constant
import com.example.graduationproject.data.WrapperClass
import com.example.graduationproject.model.getAllCrafts.Craft
import com.example.graduationproject.model.getAllCrafts.GetAllCrafts
import com.example.graduationproject.navigation.AllScreens
import com.example.graduationproject.screens.client.order.ClientOrderScreen
import com.example.graduationproject.screens.sharedScreens.chat.ChatList
import com.example.graduationproject.sharedpreference.SharedPreference
import com.example.graduationproject.ui.theme.MainColor
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ClientHomeScreen(
    navController: NavController,
    route: String,
    clientHomeViewModel: ClientHomeViewModel
) {
    //Shared preference variables
    val context = LocalContext.current
    val sharedPreference = SharedPreference(context)
    val name = sharedPreference.getName.collectAsState(initial = "")
    val token = sharedPreference.getToken.collectAsState(initial = "")
    val home = remember {
        mutableStateOf("home")
    }
    val title = remember {
        mutableStateOf(Constant.title)
    }
    // from order offer
    val isFirst = remember {
        mutableStateOf(true)
    }
    val loading = remember {
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
    var craftList: List<Craft>? = null

    if (token.value.toString().isNotEmpty()) {
        val craftData: WrapperClass<GetAllCrafts, Boolean, Exception> =
            produceState<WrapperClass<GetAllCrafts, Boolean, Exception>>(
                initialValue = WrapperClass(data = null)
            ) {
                value = clientHomeViewModel.getAllCrafts(token = "Bearer ${token.value.toString()}")
            }.value
        if (craftData.data != null) {
            craftList = craftData.data!!.data?.crafts
            loading.value = false
        }
    }

    Scaffold(
        drawerContent = {
            DrawerHeader()
            Spacer(modifier = Modifier.height(50.dp))
            DrawerBody(isClient = true, name = name.value.toString()) {
                if (it.title == "طلباتي") {
                    scope.launch {
                        home.value = "order"
                        scaffoldState.drawerState.close()
                    }
                }

                if (it.title == name.value.toString()) {
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
        bottomBar = { BottomBar(selected = home, title = title) },
    ) {
        if (!loading.value) {
            Column {
                if (home.value == "home") {
                    if (craftList != null) {
                        Home(craftList) {
                            //navigate to post
                            navController.navigate(route = AllScreens.ClientPostScreen.name + "/${it.name}")
                        }
                    }
                }
                if (home.value == "order") {
                    ClientOrderScreen(navController)
                }
                if (home.value == "chat") {
                    ChatList(navController)
                }
            }
        } else {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                CircularProgressIndicator(color = MainColor)
            }
        }
    }
}

@Composable
fun Home(
    craftList: List<Craft>,
    onClick: (Craft) -> Unit,
) {
    LazyColumn {
        items(craftList) {
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
fun JobRow(
    job: Craft,
    onClick: (Craft) -> Unit
) {
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
                    InternetCraftPhoto(uri = job.avatar)
                    InternetCraftName(jobName = job.name)
                }
            }
        }

    }
}
