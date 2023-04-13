package com.example.graduationproject.screens.admin.query.workers

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.graduationproject.components.AdminWorkerOrClientRow
import com.example.graduationproject.components.CardReport
import com.example.graduationproject.components.TopAppBar
import com.example.graduationproject.constant.Constant
import com.example.graduationproject.navigation.AllScreens

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun AdminAllWorkers(navController: NavController) {
    val showCard = remember {
        mutableStateOf(false)
    }
    val cardData = remember {
        mutableStateOf("")
    }
    Scaffold(topBar = {
        TopAppBar(title = "") {
            navController.popBackStack()
        }
    }) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 10.dp, start = 15.dp, end = 15.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "تفاصيل جميع العمال", style = TextStyle(
                        fontSize = 25.sp
                    )
                )
            }
            Spacer(modifier = Modifier.height(50.dp))
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                LazyColumn {
                    items(items = Constant.adminAllWorkerDetails) {
                        AdminWorkerOrClientRow(it, showCard = showCard, cardData = cardData,
                            onReportAction = { report ->
                                navController.navigate(AllScreens.AdminReportsQuery.name + "/${false}/${report}")
                            }) { name ->
                            navController.navigate(AllScreens.WorkerProfileScreen.name + "/${false}/${true}/${name}")
                        }
                        Spacer(modifier = Modifier.height(25.dp))
                    }
                }
                if (showCard.value) {
                    CardReport(onAccept = {
                        showCard.value = false
                        navController.navigate(route = AllScreens.AdminHomeScreen.name) {
                            navController.popBackStack()
                            navController.popBackStack()
                            navController.popBackStack()
                            navController.popBackStack()
                            navController.popBackStack()
                        }
                    }, onReject = {
                        showCard.value = false
                    }, name = cardData.value)
                }
            }
        }
    }
}