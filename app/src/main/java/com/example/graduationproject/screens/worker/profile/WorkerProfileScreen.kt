package com.example.graduationproject.screens.worker.profile


import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.graduationproject.components.CircleProgress
import com.example.graduationproject.components.DefaultButton
import com.example.graduationproject.components.GetSmallPhoto
import com.example.graduationproject.components.LoginButton
import com.example.graduationproject.components.SmallPhoto
import com.example.graduationproject.components.StarsNumber
import com.example.graduationproject.components.TopAppBar
import com.example.graduationproject.constant.Constant
import com.example.graduationproject.data.MyCraftOrderData
import com.example.graduationproject.data.WrapperClass
import com.example.graduationproject.model.shared.profile.GetProfile
import com.example.graduationproject.model.shared.profile.User
import com.example.graduationproject.navigation.AllScreens
import com.example.graduationproject.ui.theme.GrayColor
import com.example.graduationproject.ui.theme.MainColor
import com.example.graduationproject.ui.theme.RedColor
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

@SuppressLint(
    "UnusedMaterialScaffoldPaddingParameter", "CoroutineCreationDuringComposition",
    "StateFlowValueCalledInComposition"
)
@Composable
fun WorkerProfileScreen(
    navController: NavHostController,
    workerId: String,
    workerProfileViewModel: WorkerProfileViewModel
) {

    val context = LocalContext.current
    //coroutineScope
    val scope = rememberCoroutineScope()

    //state flow list
    val getProfileUser = MutableStateFlow<List<User>>(emptyList())

    var loading by remember {
        mutableStateOf(true)
    }
    var exception by remember {
        mutableStateOf(false)
    }

    if (Constant.token.isNotEmpty()) {
        val getProfile: WrapperClass<GetProfile, Boolean, Exception> =
            produceState<WrapperClass<GetProfile, Boolean, Exception>>(
                initialValue = WrapperClass(data = null)
            ) {
                value = workerProfileViewModel.getProfile(
                    userId = workerId,
                    authorization = "Bearer " + Constant.token
                )
            }.value
        if (getProfile.data?.status == "fail" || getProfile.data?.status == "error" || getProfile.e != null) {
            exception = true
            Toast.makeText(
                context,
                "خطأ في الانترنت",
                Toast.LENGTH_SHORT
            ).show()
        } else {
            if (getProfile.data != null) {
                scope.launch {
                    getProfileUser.emit(getProfile.data!!.data?.user!!)
                    loading = false
                    exception = false
                }
            }
        }
    }

//    val completeToggle = remember {
//        mutableStateOf(true)
//    }
//    val picturesList = remember {
//        mutableStateListOf<Uri?>(null)
//    }
    Scaffold(topBar = {
        TopAppBar(title = "") {
            navController.popBackStack()
        }
    })
    {
        if (!loading && !exception) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 30.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {

                    GetSmallPhoto(
                        isProfile = true,
                        uri = if (getProfileUser.value[0].avatar != null) getProfileUser.value[0].avatar.toString() else null
                    )

                }
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = getProfileUser.value[0].name,
                    style = TextStyle(
                        color = MainColor,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                )
                Spacer(modifier = Modifier.height(5.dp))
                Text(
                    text = getProfileUser.value[0].address, style = TextStyle(
                        color = Color.Gray,
                        fontSize = 15.sp
                    )
                )
                Spacer(modifier = Modifier.height(5.dp))
                Text(
                    text = if (getProfileUser.value[0].bio != null) getProfileUser.value[0].bio.toString() else "",
                    style = TextStyle(
                        color = RedColor,
                        fontSize = 12.sp
                    ), maxLines = 1, overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(5.dp))
                //Stars
                StarsNumber(if (getProfileUser.value[0].rate != null) getProfileUser.value[0].rate!!.toInt() else 0)
                Spacer(modifier = Modifier.height(15.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 50.dp, end = 50.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
//                LoginButton(isLogin = completeToggle.value, label = "مشاريع مكتملة") {
//                    completeToggle.value = true
//                }
//                LoginButton(isLogin = !completeToggle.value, label = "معرض اعمالي") {
//                    completeToggle.value = false
//                }
                    LoginButton(isLogin = true, label = "مشاريع مكتملة") {

                    }
                }
                Spacer(modifier = Modifier.height(20.dp))
//            if (completeToggle.value) {
//                //Complete
//                LazyColumn(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .height(350.dp)
//                ) {
//                    items(completeList) {
//                        CompleteProjectRow(it)
//                        Spacer(modifier = Modifier.height(10.dp))
//                    }
//                }
//            } else {
//                //pictures
//                LazyColumn(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .height(350.dp)
//                ) {
//                    items(picturesList) {
//                        if (it != null) {
//                            PickPhotoRow(it)
//                            Spacer(modifier = Modifier.height(10.dp))
//                        }
//                    }
//                }

                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(350.dp)
                ) {
                    items(completeList) {
                        CompleteProjectRow(it)
                        Spacer(modifier = Modifier.height(10.dp))
                    }

                }
                DefaultButton(label = "تقديم بلاغ") {
                    // nav to Report
                    navController.navigate(AllScreens.ReportScreen.name + "/${true}/client")
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
                        val getProfile: WrapperClass<GetProfile, Boolean, Exception> =
                            workerProfileViewModel.getProfile(
                                userId = workerId,
                                authorization = "Bearer " + Constant.token
                            )
                        if (getProfile.data?.status == "fail" || getProfile.data?.status == "error" || getProfile.e != null) {
                            exception = true
                            Toast.makeText(
                                context,
                                "خطأ في الانترنت",
                                Toast.LENGTH_SHORT
                            ).show()
                        } else {
                            if (getProfile.data != null) {
                                scope.launch {
                                    getProfileUser.emit(getProfile.data!!.data?.user!!)
                                    loading = false
                                    exception = false
                                }
                            }
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
fun CompleteProjectRow(
    item: MyCraftOrderData
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .padding(start = 15.dp, end = 15.dp),
        elevation = 2.dp,
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 5.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            // photo url from data base workers photos
            SmallPhoto()
            Spacer(modifier = Modifier.width(5.dp))
            Column {
                Text(
                    text = item.clientName.toString(), style = TextStyle(
                        color = MainColor,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                )
                Text(
                    text = item.problemTitle, style = TextStyle(
                        color = MainColor,
                        fontSize = 15.sp,
                    )
                )
                Text(
                    text = item.problemType, style = TextStyle(
                        color = GrayColor,
                        fontSize = 12.sp,
                    )
                )

            }
        }
    }
}


val completeList = listOf(
    MyCraftOrderData(
        clientName = "عبدالرحمن محمد",
        problemType = "صيانة بسيطة",
        problemTitle = "تصليح كرسي"
    ),
    MyCraftOrderData(
        clientName = "عبدالله محمد",
        problemType = "صيانة بسيطة",
        problemTitle = "تصليح كرسي"
    ),
    MyCraftOrderData(
        clientName = "احمد محمد",
        problemType = "صيانة بسيطة",
        problemTitle = "تصليح كرسي"
    ),
    MyCraftOrderData(
        clientName = "محمد احمد",
        problemType = "صيانة بسيطة",
        problemTitle = "تصليح كرسي"
    ),
    MyCraftOrderData(
        clientName = "محمود محمد",
        problemType = "صيانة بسيطة",
        problemTitle = "تصليح كرسي"
    ),
    MyCraftOrderData(
        clientName = "محمد محمود",
        problemType = "صيانة بسيطة",
        problemTitle = "تصليح كرسي"
    ),
    MyCraftOrderData(
        clientName = "رغد محمود",
        problemType = "صيانة بسيطة",
        problemTitle = "تصليح كرسي"
    ),
)