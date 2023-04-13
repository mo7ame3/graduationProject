package com.example.graduationproject.screens.client.order

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.graduationproject.R
import com.example.graduationproject.components.TopAppBar
import com.example.graduationproject.data.MyCraftOrderData
import com.example.graduationproject.navigation.AllScreens
import com.example.graduationproject.ui.theme.MainColor
import com.example.graduationproject.ui.theme.RedColor

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ClientMyCraftOrders(navController: NavController, id: String) {
    Scaffold(topBar = {
        TopAppBar(title = id) {
            navController.popBackStack()
        }
    }) {
        //
        LazyColumn {
            items(itemList) { itemlist ->
                MyCraftOrdersRow(item = itemlist,
                    onClick = {
                        //nav to Offer Screen
                        navController.navigate(AllScreens.ClientOrderOfferScreen.name + "/${it.problemTitle}")
                    },
                    onDeleteItem = {
                        //Delete Problem "Trash Icon"
                    })
                Spacer(modifier = Modifier.height(15.dp))
            }
        }
    }

}


@Composable
fun MyCraftOrdersRow(
    item: MyCraftOrderData,
    onClick: (MyCraftOrderData) -> Unit,
    onDeleteItem: (MyCraftOrderData) -> Unit,
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .padding(start = 15.dp, end = 15.dp)
            .clickable { onClick.invoke(item) },
        shape = RoundedCornerShape(25.dp), border = BorderStroke(width = 1.dp, color = MainColor)
    ) {

        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 15.dp, end = 15.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = item.problemTitle,
                    style = TextStyle(
                        color = MainColor,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 20.sp
                    )
                )
                Text(
                    text = item.problemType, style = TextStyle(
                        color = Color.Gray,
                        fontSize = 18.sp
                    )
                )
                Text(
                    text = item.problemState.toString(),
                    style = TextStyle(
                        color = RedColor,
                        fontSize = 16.sp
                    )
                )
            }
            Row {
                if (item.problemState == "قيد الانتظار...") {
                    Surface(
                        modifier = Modifier
                            .size(60.dp)
                            .clickable { onDeleteItem.invoke(item) },
                        color = Color.Transparent
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.trash),
                                contentDescription = null,
                                tint = MainColor,
                                modifier = Modifier.size(30.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}

val itemList = listOf(
    MyCraftOrderData(
        problemTitle = "تصليح كرسي",
        problemType = "صيانة متوسطة",
        problemState = "قيد التنفيذ..."
    ),
    MyCraftOrderData(
        problemTitle = "تصليح دولاب",
        problemType = "صيانة معقدة",
        problemState = "قيد الانتظار..."
    ),

    )

