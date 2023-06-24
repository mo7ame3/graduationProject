package com.example.graduationproject.screens.client.profile

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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.graduationproject.components.CircleProgress
import com.example.graduationproject.components.DefaultButton
import com.example.graduationproject.components.GetSmallPhoto
import com.example.graduationproject.components.SmallPhoto
import com.example.graduationproject.components.StarsNumber
import com.example.graduationproject.components.TopAppBar
import com.example.graduationproject.constant.Constant
import com.example.graduationproject.data.MyCraftOrderData
import com.example.graduationproject.data.WrapperClass
import com.example.graduationproject.model.shared.profile.GetProfile
import com.example.graduationproject.model.shared.profile.User
import com.example.graduationproject.navigation.AllScreens
import com.example.graduationproject.ui.theme.MainColor
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

@SuppressLint(
    "UnusedMaterialScaffoldPaddingParameter", "CoroutineCreationDuringComposition",
    "StateFlowValueCalledInComposition"
)
@Composable
fun ClientProfileScreen(
    navController: NavHostController,
    clientId: String,
    clientProfileViewModel: ClientProfileViewModel
) {
    val isSelect = remember {
        mutableStateOf(false)
    }
    val changeCompleteState = remember {
        mutableStateOf(true)
    }

    //coroutineScope
    val scope = rememberCoroutineScope()

    //state flow list
    val getClientProfile = MutableStateFlow<List<User>>(emptyList())

    var loading by remember {
        mutableStateOf(true)
    }
    var exception by remember {
        mutableStateOf(false)
    }
    val context = LocalContext.current

    if (Constant.token.isNotEmpty()) {
        val response =
            produceState<WrapperClass<GetProfile, Boolean, Exception>>(initialValue = WrapperClass()) {
                value = clientProfileViewModel.getProfile(
                    authorization = "Bearer " + Constant.token,
                    userId = clientId
                )
            }.value
        if (response.data?.status == "fail" || response.data?.status == "error" || response.e != null) {
            exception = true
            Toast.makeText(
                context,
                "خطأ في الانترنت",
                Toast.LENGTH_SHORT
            ).show()
        } else {
            if (response.data != null) {
                scope.launch {
                    getClientProfile.emit(response.data!!.data?.user!!)
                    loading = false
                    exception = false
                }
            }
        }
    }

    Scaffold(topBar = {
        TopAppBar(title = "") {
            navController.popBackStack()
        }
    }) {
        if (!loading && !exception) {
            if (changeCompleteState.value) {
                isSelect.value = true
                changeCompleteState.value = false
            }
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 30.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    GetSmallPhoto(
                        isProfile = true,
                        uri = if (getClientProfile.value[0].avatar != null) getClientProfile.value[0].avatar.toString() else null
                    )
                }
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = getClientProfile.value[0].name,
                    style = TextStyle(
                        color = MainColor,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                )
                Spacer(modifier = Modifier.height(5.dp))
                Text(
                    text = getClientProfile.value[0].address, style = TextStyle(
                        color = Color.Gray,
                        fontSize = 15.sp
                    )
                )

                Spacer(modifier = Modifier.height(15.dp))
                DefaultButton(label = "مشاريع مكتملة") {
                    isSelect.value = !isSelect.value
                }
                Spacer(modifier = Modifier.height(15.dp))
                if (isSelect.value) {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(400.dp)
                    ) {
                        items(completeClientList) {
                            CompleteProjectClientRow(item = it)
                            Spacer(modifier = Modifier.height(20.dp))
                        }
                    }
                }
                if (!isSelect.value) {
                    Spacer(modifier = Modifier.height(400.dp))
                }
                DefaultButton(label = "تقديم بلاغ") {
                    // nav to Report
                    navController.navigate(AllScreens.ReportScreen.name + "/${false}/worker")
                }
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
                        val getProfile: WrapperClass<GetProfile, Boolean, Exception> =
                            clientProfileViewModel.getProfile(
                                userId = clientId,
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
                                    getClientProfile.emit(getProfile.data!!.data?.user!!)
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
fun CompleteProjectClientRow(
    item: MyCraftOrderData
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .padding(start = 15.dp, end = 15.dp),
        elevation = 5.dp,
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
                    text = item.workerName.toString(), style = TextStyle(
                        color = MainColor,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                )
                Text(
                    text = item.problemTitle + "- " + item.problemType, style = TextStyle(
                        color = MainColor,
                        fontSize = 12.sp,
                    )
                )
                StarsNumber(stars = item.workerRate!!)

            }
        }
    }
}


val completeClientList = listOf(
    MyCraftOrderData(
        workerName = "أحمد محمد",
        workerRate = 5,
        problemType = "صيانة بسيطة",
        problemTitle = "تصليح كرسي"
    ),
    MyCraftOrderData(
        workerName = "أحمد محمد",
        workerRate = 5,
        problemType = "صيانة بسيطة",
        problemTitle = "تصليح كرسي"
    ),
    MyCraftOrderData(
        workerName = "محمد أحمد",
        workerRate = 3,
        problemType = "صيانة بسيطة",
        problemTitle = "تصليح كرسي"
    ),
    MyCraftOrderData(
        workerName = "محمد محمد",
        workerRate = 2,
        problemType = "صيانة بسيطة",
        problemTitle = "تصليح كرسي"
    ),
    MyCraftOrderData(
        workerName = "محمود محمد",
        workerRate = 4,
        problemType = "صيانة بسيطة",
        problemTitle = "تصليح كرسي"
    ),
    MyCraftOrderData(
        workerName = "محمود أحمد",
        workerRate = 3,
        problemType = "صيانة بسيطة",
        problemTitle = "تصليح كرسي"
    ),
    MyCraftOrderData(
        workerName = "عباس احمد",
        workerRate = 3,
        problemType = "صيانة بسيطة",
        problemTitle = "تصليح كرسي"
    ),
)