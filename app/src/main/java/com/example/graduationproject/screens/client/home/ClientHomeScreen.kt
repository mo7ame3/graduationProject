package com.example.graduationproject.screens.client.home

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.rememberScaffoldState
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
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.graduationproject.components.BottomBar
import com.example.graduationproject.components.CircleProgress
import com.example.graduationproject.components.DrawerBody
import com.example.graduationproject.components.DrawerHeader
import com.example.graduationproject.components.InternetCraftName
import com.example.graduationproject.components.InternetCraftPhoto
import com.example.graduationproject.components.TopMainBar
import com.example.graduationproject.constant.Constant
import com.example.graduationproject.data.WrapperClass
import com.example.graduationproject.model.shared.craft.Craft
import com.example.graduationproject.model.shared.getAllCrafts.GetAllCrafts
import com.example.graduationproject.navigation.AllScreens
import com.example.graduationproject.screens.client.order.ClientOrderScreen
import com.example.graduationproject.screens.client.order.OrderViewModel
import com.example.graduationproject.screens.sharedScreens.chat.ChatList
import com.example.graduationproject.sharedpreference.SharedPreference
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

@SuppressLint(
    "UnusedMaterialScaffoldPaddingParameter", "CoroutineCreationDuringComposition",
    "StateFlowValueCalledInComposition"
)
@Composable
fun ClientHomeScreen(
    navController: NavController,
    route: String,
    clientHomeViewModel: ClientHomeViewModel,
    orderViewModel: OrderViewModel
) {
    //Shared preference variables
    val context = LocalContext.current
    val sharedPreference = SharedPreference(context)
    val name = sharedPreference.getName.collectAsState(initial = "")
    val token = sharedPreference.getToken.collectAsState(initial = "")

    //select item in bottomBar
    val home = remember {
        mutableStateOf("home")
    }

    //app title
    val title = remember {
        mutableStateOf(Constant.title)
    }

    // from order offer
    val isFirst = remember {
        mutableStateOf(true)
    }

    //check home loading
    var loading by remember {
        mutableStateOf(true)
    }
    var exception by remember {
        mutableStateOf(false)
    }
    //bottomBar
    if (route == "home" || route == "chat" || route == "order") {
        if (isFirst.value) {
            home.value = route
            isFirst.value = false
            title.value =
                if (route == "home") Constant.title else if (route == "chat") "المحادثات" else "طلباتي"
        }
    }

    //scaffoldState
    val scaffoldState = rememberScaffoldState()

    //coroutineScope
    val scope = rememberCoroutineScope()

    //state flow list
    val craftFromGetAllCraftList = MutableStateFlow<List<Craft>>(emptyList())

    //response
    if (token.value.toString().isNotEmpty()) {
        val craftData: WrapperClass<GetAllCrafts, Boolean, Exception> =
            produceState<WrapperClass<GetAllCrafts, Boolean, Exception>>(
                initialValue = WrapperClass(data = null)
            ) {
                value = clientHomeViewModel.getAllCrafts(token = "Bearer ${token.value.toString()}")
            }.value
        if (craftData.data?.status == "success") {
            if (craftData.data != null) {
                scope.launch {
                    craftFromGetAllCraftList.emit(craftData.data!!.data?.crafts!!)
                    loading = false
                    exception = false
                }
            }
        }
        else if (craftData.data?.status == "fail" || craftData.data?.status == "error" || craftData.e != null) {
            exception = true
            Toast.makeText(
                context,
                "خطأ في الانترنت",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    // Swipe Refresh
    var swipeLoading by remember {
        mutableStateOf(false)
    }
    val swipeRefreshState = rememberSwipeRefreshState(isRefreshing = swipeLoading)

    SwipeRefresh(state = swipeRefreshState,
        onRefresh = {
            if (home.value == "home") {
            swipeLoading = true
            loading = false
            scope.launch {
                val craftData: WrapperClass<GetAllCrafts, Boolean, Exception> =
                    clientHomeViewModel.getAllCrafts(token = "Bearer ${token.value.toString()}")
                if (craftData.data?.status == "success") {
                    if (craftData.data != null) {
                        craftFromGetAllCraftList.emit(craftData.data!!.data?.crafts!!)
                        swipeLoading = false
                    }
                } else {
                    swipeLoading = false
                }
            }
            }
        })
    {
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
                            navController.navigate(route = AllScreens.ClientMyProfileScreen.name + "/${false}")
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
                            navController.navigate(route = AllScreens.ClientMyProfileScreen.name + "/${true}")
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
            if (!loading && !exception) {
                Column {
                    if (home.value == "home") {
                        Home(craftFromGetAllCraftList.value) {
                            //navigate to post
                            navController.navigate(route = AllScreens.ClientPostScreen.name + "/${it.id}/${it.name}")
                        }
                    }
                    if (home.value == "order") {
                        ClientOrderScreen(navController , orderViewModel)
                    }
                    if (home.value == "chat") {
                        ChatList(navController)
                    }
                }
            }
            else if (loading && !exception) {
                CircleProgress()
            }
            else if (exception) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    IconButton(onClick = {
                        exception = false
                        loading = true
                        scope.launch {
                            val craftData: WrapperClass<GetAllCrafts, Boolean, Exception> =
                                clientHomeViewModel.getAllCrafts(token = "Bearer ${token.value.toString()}")
                            if (craftData.data?.status == "success") {
                                if (craftData.data != null) {
                                    scope.launch {
                                        craftFromGetAllCraftList.emit(craftData.data!!.data?.crafts!!)
                                        loading = false
                                        exception = false
                                    }
                                }
                            } else if (craftData.data?.status == "fail" || craftData.e != null) {
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
}

@Composable
fun Home(
    craftFromGetAllCraftList: List<Craft>,
    onClick: (Craft) -> Unit,
) {
    LazyColumn {
        items(craftFromGetAllCraftList) {
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