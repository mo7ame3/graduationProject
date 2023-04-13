package com.example.graduationproject.screens.admin.home

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.graduationproject.components.TopAppBar
import com.example.graduationproject.navigation.AllScreens
import com.example.graduationproject.sharedpreference.SharedPreference
import com.example.graduationproject.ui.theme.AdminSecondaryColor
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun AdminHomeScreen(navController: NavHostController) {
    //Log out Variables
    val context = LocalContext.current
    val sharedPreference = SharedPreference(context)
    val scope = rememberCoroutineScope()
    Scaffold(topBar = {
        TopAppBar(title = "") {
            scope.launch {
                sharedPreference.saveState("")
                navController.navigate(route = AllScreens.LoginScreen.name) {
                    navController.popBackStack()
                }
            }
        }
    }) {
        Surface(
            modifier = Modifier
                .fillMaxSize()
        ) {
            LazyColumn(
                modifier = Modifier.padding(top = 50.dp, end = 15.dp, start = 15.dp)
            ) {
                items(query) {
                    QueryRow(title = it) { query ->
                        if (query == "المهن") {
                            navController.navigate(route = AllScreens.AdminJobsScreen.name)
                        } else if (query == "العمال") {
                            navController.navigate(route = AllScreens.AdminAllWorkers.name)
                        } else if (query == "العملاء") {
                            navController.navigate(route = AllScreens.AdminAllClients.name)
                        } else if (query == "الحظر") {
                            //nav to report Screen
                            navController.navigate(route = AllScreens.AdminBlockedUsers.name)
                        }

                    }
                    Spacer(modifier = Modifier.height(90.dp))
                }
            }

        }
    }
}

@Composable
fun QueryRow(
    title: String,
    onAction: (String) -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .clickable { onAction.invoke(title) },
        border = BorderStroke(width = 1.dp, color = AdminSecondaryColor),
        shape = RoundedCornerShape(35.dp),
        //contentColor = Color.White
    ) {
        Box(contentAlignment = Alignment.Center) {
            Text(
                text = title,
                style = TextStyle(
                    color = AdminSecondaryColor,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.SemiBold
                )
            )
        }
    }
}


val query = listOf(
    "العمال",
    "العملاء",
    "الحظر",
    "المهن",
)






