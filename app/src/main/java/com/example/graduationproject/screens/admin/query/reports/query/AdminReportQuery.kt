package com.example.graduationproject.screens.admin.query.reports.query

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.material.Checkbox
import androidx.compose.material.CheckboxDefaults
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.graduationproject.components.SmallPhoto
import com.example.graduationproject.components.TopAppBar
import com.example.graduationproject.constant.Constant.reportList
import com.example.graduationproject.ui.theme.MainColor

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun AdminReportListQuery(navController: NavController, name: String) {
    Scaffold(topBar = {
        TopAppBar(title = "") {
            navController.popBackStack()
        }
    }) {
        val checkedlist = remember {
            mutableStateListOf(
                "شخص مزيف",
                "وصف غير دقيق للمشكلة",
                "سعر غير مناسب",
                "",
                "",
                "",
                "",
                ""
            )
        }
        //list
        Column(modifier = Modifier.padding(start = 15.dp, end = 15.dp)) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 15.dp, bottom = 15.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                SmallPhoto()
                Spacer(modifier = Modifier.width(10.dp))
                Text(text = name)
            }
            reportList.forEach { element ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Checkbox(
                        checked = when (element) {
                            checkedlist[0] -> true
                            checkedlist[1] -> true
                            checkedlist[2] -> true
                            checkedlist[3] -> true
                            checkedlist[4] -> true
                            checkedlist[5] -> true
                            checkedlist[6] -> true
                            else -> checkedlist[7] == element
                        },
                        onCheckedChange = {
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
    }
}