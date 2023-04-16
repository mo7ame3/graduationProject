package com.example.graduationproject.screens.client.profile

import android.annotation.SuppressLint
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.graduationproject.R
import com.example.graduationproject.components.*
import com.example.graduationproject.data.MyCraftOrderData
import com.example.graduationproject.navigation.AllScreens
import com.example.graduationproject.ui.theme.MainColor

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ClientProfileScreen(
    navController: NavHostController,
    ShowReportButton: Boolean = false,
    completeProject: Boolean = false,
    isAdmin: Boolean = false,
    clientName: String
) {
    val adminOrClient = remember {
        if (ShowReportButton) true else isAdmin
    }
    val isSelect = remember {
        mutableStateOf(false)
    }
    val changeCompleteState = remember {
        mutableStateOf(true)
    }
    Scaffold(topBar = {
        TopAppBar(title = "") {
            navController.popBackStack()
        }
    }) {
        if (completeProject && changeCompleteState.value) {
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
                modifier = if (!adminOrClient) Modifier
                    .fillMaxWidth()
                    .padding(start = 50.dp) else Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                var uri by remember {
                    mutableStateOf<Uri?>(null)
                }
                val launcher =
                    rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { URI ->
                        uri = URI
                    }
                ProfilePhoto(uri = uri)
                if (!adminOrClient)
                    IconButton(onClick = {
                        //nav to edit photo
                        launcher.launch("image/*")
                    }, modifier = Modifier.padding(top = 85.dp)) {
                        Icon(
                            painter = painterResource(id = R.drawable.camera),
                            contentDescription = null, tint = MainColor
                        )
                    }
            }
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = if (clientName.trim().isNotBlank()) clientName else "رغد محمود صالح",
                style = TextStyle(
                    color = MainColor,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold
                )
            )
            Spacer(modifier = Modifier.height(5.dp))
            Text(
                text = "مركز الفيوم", style = TextStyle(
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
                    items(completeList) {
                        CompleteProjectRow(item = it)
                        Spacer(modifier = Modifier.height(20.dp))
                    }
                    item {
                        //Spacer(modifier = Modifier.height(150.dp))
                    }
                }
            }
            if (adminOrClient && ShowReportButton) {
                if (!isSelect.value) {
                    Spacer(modifier = Modifier.height(400.dp))
                }
                DefaultButton(label = "تقديم بلاغ") {
                    // nav to Report
                    navController.navigate(AllScreens.ReportScreen.name + "/${false}/worker")
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


val completeList = listOf(
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
