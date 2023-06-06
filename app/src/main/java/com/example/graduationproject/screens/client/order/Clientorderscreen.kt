package com.example.graduationproject.screens.client.order

import android.annotation.SuppressLint
import android.widget.Toast
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
import androidx.compose.runtime.produceState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.graduationproject.R
import com.example.graduationproject.data.Job
import com.example.graduationproject.data.WrapperClass
import com.example.graduationproject.model.shared.craftList.CraftList
import com.example.graduationproject.model.shared.getAllCrafts.GetAllCrafts
import com.example.graduationproject.navigation.AllScreens
import com.example.graduationproject.ui.theme.MainColor
import kotlinx.coroutines.launch

@SuppressLint("ProduceStateDoesNotAssignValue", "CoroutineCreationDuringComposition")
@Composable
fun ClientOrderScreen(navController: NavController, orderViewModel: OrderViewModel) {

    //coroutineScope
    val scope = rememberCoroutineScope()

    val response: WrapperClass<CraftList, Boolean, Exception> =
        produceState<WrapperClass<CraftList, Boolean, Exception>>(
            initialValue = WrapperClass(data = null)
        )
        {
            value = orderViewModel.getCraftList()
        }.value

//    if (response.data?.status == "success") {
//        if (response.data != null) {
//            scope.launch {
//                craftFromGetAllCraftList.emit(craftData.data!!.data?.crafts!!)
//                loading = false
//                exception = false
//            }
//        }
//    }
//    else if (response.data?.status == "fail" || response.e != null) {
//        exception = true
//        Toast.makeText(
//            context,
//            "خطأ في الانترنت",
//            Toast.LENGTH_SHORT
//        ).show()
//    }

    LazyColumn {
        items(orders) {
            OrderRow(onClick = {
                //navigate to my order
                navController.navigate(route = AllScreens.ClientMyCraftOrders.name + "/${it.job}/${it.job}")
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
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
        ) {
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