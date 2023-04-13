package com.example.graduationproject.screens.admin.query.blocks

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.graduationproject.components.SmallPhoto
import com.example.graduationproject.components.TopAppBar
import com.example.graduationproject.constant.Constant
import com.example.graduationproject.data.BlockData

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun AdminBlockedUsers(navController: NavController) {
    Scaffold(topBar = {
        TopAppBar(title = "") {
            navController.popBackStack()
        }
    }) {
        Column(modifier = Modifier.fillMaxSize()) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 20.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "تفاصيل جميع العمال", style = TextStyle(
                        fontSize = 25.sp
                    )
                )
            }




            Spacer(modifier = Modifier.height(50.dp))
            LazyColumn {
                items(Constant.adminBlockList) {
                    BlockRow(blockData = it) {
                        // UnBlocking User from dataBase
                    }
                    Spacer(modifier = Modifier.height(20.dp))
                }
            }
        }

    }
}


@Composable
fun BlockRow(
    blockData: BlockData,
    unBlockUser: () -> Unit
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 15.dp, end = 15.dp), horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            SmallPhoto()
            Spacer(modifier = Modifier.width(10.dp))
            Text(text = blockData.name)
        }
        if (blockData.time != null) {
            Text(text = blockData.time)
        } else if (blockData.image != null) {
            IconButton(onClick = { unBlockUser.invoke() }) {
                Icon(imageVector = blockData.image, contentDescription = null)
            }
        }
    }

}
