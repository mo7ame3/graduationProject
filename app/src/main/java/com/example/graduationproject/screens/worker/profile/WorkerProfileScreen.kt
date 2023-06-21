package com.example.graduationproject.screens.worker.profile

import android.annotation.SuppressLint
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.graduationproject.R
import com.example.graduationproject.components.*
import com.example.graduationproject.data.MyCraftOrderData
import com.example.graduationproject.navigation.AllScreens
import com.example.graduationproject.ui.theme.GrayColor
import com.example.graduationproject.ui.theme.MainColor
import com.example.graduationproject.ui.theme.RedColor

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun WorkerProfileScreen(
    navController: NavHostController,
    ShowReportButton: Boolean = false,
    isAdmin: Boolean = false,
    workerName: String
    //id from database
) {
    val stars = remember {
        mutableStateOf(3)
    }
    val completeToggle = remember {
        mutableStateOf(true)
    }
    val picturesList = remember {
        mutableStateListOf<Uri?>(null)
    }
    val launcher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { uri ->
            picturesList.add(uri)
        }
    val adminOrWorker = remember {
        if (ShowReportButton) true else isAdmin
    }
    Scaffold(topBar = {
        TopAppBar(title = "") {
            navController.popBackStack()
        }
    })
    {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 30.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Row(
                modifier = if (!adminOrWorker) Modifier
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
                if (!adminOrWorker)
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
                text = if (workerName.trim().isNotBlank()) workerName else "رغد محمود صالح",
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
            Spacer(modifier = Modifier.height(5.dp))
            Text(
                text = "نجار محترف لمدة عامين", style = TextStyle(
                    color = RedColor,
                    fontSize = 12.sp
                ), maxLines = 1, overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(5.dp))
            //Stars
            StarsNumber(stars.value)
            Spacer(modifier = Modifier.height(15.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 50.dp, end = 50.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                LoginButton(isLogin = completeToggle.value, label = "مشاريع مكتملة") {
                    completeToggle.value = true
                }
                LoginButton(isLogin = !completeToggle.value, label = "معرض اعمالي") {
                    completeToggle.value = false
                }
            }
            Spacer(modifier = Modifier.height(20.dp))
            if (completeToggle.value) {
                //Complete
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
            }
            else {
                //pictures
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(350.dp)
                ) {
                    items(picturesList) {
                        if (it != null) {
                            PickPhotoRow(it)
                            Spacer(modifier = Modifier.height(10.dp))
                        }
                    }
                }
            }
            if (adminOrWorker && ShowReportButton) {
                DefaultButton(label = "تقديم بلاغ") {
                    // nav to Report
                    navController.navigate(AllScreens.ReportScreen.name + "/${true}/client")
                }
            }
            else {
                if (!completeToggle.value && !adminOrWorker) {
                    LoginButton(
                        isLogin = true,
                        label = "أضف صور لمعرض أعمالي",
                        modifier = Modifier.width(200.dp)
                    ) {
                        launcher.launch("image/*")
                    }
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


@Composable
fun PickPhotoRow(
    selectedImage: Uri? = null
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(220.dp)
            .padding(start = 25.dp, end = 25.dp),
        shape = RoundedCornerShape(25.dp),
        border = BorderStroke(width = 1.dp, color = MainColor)
    ) {
        //change photo
        PickPhoto(selectedImage)
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