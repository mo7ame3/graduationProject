package com.example.graduationproject.screens.client.order

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.graduationproject.R
import com.example.graduationproject.data.Job
import com.example.graduationproject.navigation.AllScreens
import com.example.graduationproject.ui.theme.MainColor

@Composable
fun ClientOrderScreen(navController: NavController) {
    LazyColumn {
        items(orders) {
            OrderRow(onClick = {
                //navigate to my order
                navController.navigate(route = AllScreens.ClientMyCraftOrders.name + "/${it.job}")
            }, order = it)
            Spacer(modifier = Modifier.height(10.dp))
        }
        item {
            Spacer(modifier = Modifier.height(50.dp))
        }
    }
}

@Composable
fun OrderRow(
    onClick: (Job) -> Unit,
    order: Job
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
            .padding(start = 15.dp, end = 15.dp)
            .clickable { onClick.invoke(order) },
        border = BorderStroke(width = 1.dp, color = MainColor), shape = RoundedCornerShape(10.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 12.dp)
        ) {
            Icon(
                painter = painterResource(id = order.pic),
                contentDescription = null,
                modifier = Modifier.size(35.dp),
                tint = MainColor
            )
            Spacer(modifier = Modifier.width(10.dp))
            Text(text = order.job, style = TextStyle(color = MainColor, fontSize = 20.sp))
        }
    }

}


val orders = listOf(
    Job("خدمة الكهرباء", R.drawable.electrical_plug),
    Job("خدمة التنظيف", R.drawable.vacuum_cleaner),
    Job("خدمة النقاشة", R.drawable.paint_roller),
    Job("خدمة السباكة", R.drawable.running_water),
    Job("خدمة النجارة", R.drawable.saw),
    Job("خدمة الحدادة", R.drawable.anvil),
    Job("خدمة البناء", R.drawable.trowel),
    Job("خدمة الصيانة", R.drawable.washing_machine)
)