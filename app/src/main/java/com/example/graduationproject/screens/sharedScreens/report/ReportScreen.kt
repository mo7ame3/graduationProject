package com.example.graduationproject.screens.sharedScreens.report

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Checkbox
import androidx.compose.material.CheckboxDefaults
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.graduationproject.components.LoginButton
import com.example.graduationproject.components.TopAppBar
import com.example.graduationproject.constant.Constant.reportList
import com.example.graduationproject.navigation.AllScreens
import com.example.graduationproject.ui.theme.MainColor

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ReportScreen(
    navController: NavHostController,
    client: Boolean = true
) {
    Scaffold(topBar = {
        TopAppBar(title = "بلاغ") {
            navController.popBackStack()
        }
    }) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 100.dp),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.SpaceBetween
        ) {

            val checkedlist = remember {
                mutableStateListOf<String>()
            }
            //list
            Column {
                reportList.forEach { element ->
                    var checked by remember {
                        mutableStateOf(false)
                    }
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                checked = !checked
                                if (checked) {
                                    checkedlist.add(element)
                                } else {
                                    checkedlist.remove(element)
                                }
                            }) {
                        Checkbox(
                            checked = checked,
                            onCheckedChange = {
                                checked = it
                                if (checked) {
                                    checkedlist.add(element)
                                } else {
                                    checkedlist.remove(element)
                                }
                            },
                            colors = CheckboxDefaults.colors(
                                checkedColor = MainColor,
                                uncheckedColor = MainColor
                            )
                        )
                        Text(text = element)
                    }
                    Spacer(modifier = Modifier.height(15.dp))
                }
            }

            // Spacer(modifier = Modifier.height(50.dp))
            //Buttons
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.fillMaxWidth()
            ) {

                LoginButton(
                    isLogin = true,
                    label = "تأكيد",
                    modifier = Modifier
                        .width(120.dp)
                        .height(45.dp)
                ) {
                    //if condition between screens "Client or Worker"
                    if (checkedlist.isNotEmpty()) {
                        if (client) {
                            navController.navigate(route = AllScreens.ClientHomeScreen.name + "/{}") {
                                navController.popBackStack()
                                navController.popBackStack()
                                navController.popBackStack()
                            }
                        } else {
                            navController.navigate(route = AllScreens.WorkerHomeScreen.name + "/{}") {
                                navController.popBackStack()
                                navController.popBackStack()
                                navController.popBackStack()
                            }
                        }
                    }
                    // update report to database
                }
                LoginButton(
                    isLogin = false,
                    label = "تراجع",
                    modifier = Modifier
                        .width(120.dp)
                        .height(45.dp)
                ) {
                    //if condition between screens "Client or Worker"
                    if (client) {
                        navController.navigate(route = AllScreens.ClientHomeScreen.name + "/{}") {
                            navController.popBackStack()
                            navController.popBackStack()
                            navController.popBackStack()
                        }
                    } else {
                        navController.navigate(route = AllScreens.WorkerHomeScreen.name + "/{}") {
                            navController.popBackStack()
                            navController.popBackStack()
                            navController.popBackStack()
                        }
                    }
                }
            }
        }

    }
}