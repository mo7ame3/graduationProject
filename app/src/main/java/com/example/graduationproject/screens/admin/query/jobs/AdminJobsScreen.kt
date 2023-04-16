package com.example.graduationproject.screens.admin.query.jobs

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.graduationproject.components.InternetCraftName
import com.example.graduationproject.components.InternetCraftPhoto
import com.example.graduationproject.components.TopAppBar
import com.example.graduationproject.constant.Constant.adminCraftList
import com.example.graduationproject.data.GoogleDriveList
import com.example.graduationproject.navigation.AllScreens
import com.example.graduationproject.ui.theme.AdminSecondaryColor

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun AdminJobsScreen(navController: NavHostController) {
    Scaffold(topBar = {
        TopAppBar(title = "") {
            navController.popBackStack()
        }
    }) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
        ) {
            items(items = adminCraftList) {
                JobRow(googleDriveList = it, onEditAction = { edit ->
                    navController.navigate(AllScreens.AdminEditJobsScreen.name + "/${edit}")
                })
                { craftQuery ->
                    navController.navigate(route = AllScreens.AdminAllWorkersInSpecificJob.name + "/${craftQuery.id}")
                }
            }
        }
    }
}

@Composable
fun JobRow(
    googleDriveList: GoogleDriveList,
    onEditAction: (Int) -> Unit,
    onClick: (GoogleDriveList) -> Unit
) {
    Column(modifier = Modifier
        .clickable {
            onClick.invoke(googleDriveList)
        }
        .fillMaxSize()
        .padding(10.dp), horizontalAlignment = Alignment.CenterHorizontally) {
        Box {
            Surface(
                elevation = 10.dp,
                shape = RoundedCornerShape(size = 30.dp),
                modifier = Modifier.size(350.dp, 230.dp),
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Box {
                        InternetCraftPhoto(googleDriveList.picGoogle)
                        Row(
                            modifier = Modifier.size(300.dp, 200.dp),
                            horizontalArrangement = Arrangement.End,
                            verticalAlignment = Alignment.Top
                        ) {
                            IconButton(onClick = {
                                // nav to edit
                                onEditAction.invoke(googleDriveList.id)
                            }) {
                                Icon(
                                    imageVector = Icons.Default.Edit, contentDescription = null,
                                    tint = AdminSecondaryColor
                                )
                            }
                        }
                    }
                    InternetCraftName(jobName = googleDriveList.jobName)
                }
            }
        }
    }
}
