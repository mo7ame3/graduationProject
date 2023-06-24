package com.example.graduationproject.screens.admin.query.reports

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.graduationproject.R
import com.example.graduationproject.components.AdminWorkerOrClientRow
import com.example.graduationproject.components.CardReport
import com.example.graduationproject.components.SmallPhoto
import com.example.graduationproject.components.TopAppBar
import com.example.graduationproject.constant.Constant.adminAllClientDetails
import com.example.graduationproject.constant.Constant.adminAllWorkerDetails
import com.example.graduationproject.data.AdminUserReport
import com.example.graduationproject.navigation.AllScreens

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun AdminReportsQuery(
    navController: NavController,
    client: Boolean,
    id: Int
) {
    val item = if (client) adminAllClientDetails[id] else adminAllWorkerDetails[id]
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
                .padding(start = 20.dp, end = 20.dp)
        ) {
            Row(modifier = Modifier.fillMaxWidth()) {
                Text(text = if (client) "بلاغات العمال" else "بلاغات العملاء", fontSize = 24.sp)
            }
            Spacer(modifier = Modifier.height(15.dp))
            AdminWorkerOrClientRow(
                item,
                showCard = showCard,
                cardData = cardData,
                reportCLick = true,
                onReportAction = {
                    //not Work
                }) { name ->
                if (client) {
                    navController.navigate(AllScreens.AdminClientProfileScreen.name + "/${false}/${false}/${true}/${name}")
                } else {
                    navController.navigate(AllScreens.WorkerProfileScreen.name + "/${false}/${true}/${name}")
                }
            }
            Spacer(modifier = Modifier.height(50.dp))
            if (item.reportList != null) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    LazyColumn(modifier = Modifier.fillMaxSize()) {
                        items(item.reportList) {
                            ReportRow(it, onAction = { name ->
                                if (client) {
                                    navController.navigate(AllScreens.AdminClientProfileScreen.name + "/${false}/${false}/${true}/${name}")
                                } else {
                                    navController.navigate(AllScreens.WorkerProfileScreen.name + "/${false}/${true}/${name}")
                                }
                            })
                            { reportUserName ->
                                //nav to Details
                                navController.navigate(AllScreens.AdminReportListQuery.name + "/${reportUserName}")
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
}

@Composable
fun ReportRow(
    adminUserReport: AdminUserReport,
    onAction: (String) -> Unit,
    onBlockDetailsAction: (String) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onAction.invoke(adminUserReport.name)
            },
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            SmallPhoto()
            Spacer(modifier = Modifier.width(5.dp))
            Text(text = adminUserReport.name)
        }
        IconButton(onClick = { onBlockDetailsAction.invoke(adminUserReport.name) }) {
            Icon(
                painter = painterResource(id = R.drawable.list_svgrepo_com),
                contentDescription = null,
                modifier = Modifier.size(25.dp)
            )
        }
    }
}