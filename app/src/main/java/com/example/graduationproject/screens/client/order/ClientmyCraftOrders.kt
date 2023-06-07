package com.example.graduationproject.screens.client.order

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.graduationproject.R
import com.example.graduationproject.components.CircleProgress
import com.example.graduationproject.components.TopAppBar
import com.example.graduationproject.data.WrapperClass
import com.example.graduationproject.model.admin.deleteCraft.Delete
import com.example.graduationproject.model.client.getMyOrder.Data
import com.example.graduationproject.model.client.getMyOrder.GetMyOrder
import com.example.graduationproject.navigation.AllScreens
import com.example.graduationproject.sharedpreference.SharedPreference
import com.example.graduationproject.ui.theme.MainColor
import com.example.graduationproject.ui.theme.RedColor
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

@SuppressLint(
    "UnusedMaterialScaffoldPaddingParameter", "CoroutineCreationDuringComposition",
    "StateFlowValueCalledInComposition"
)
@Composable
fun ClientMyCraftOrders(
    navController: NavController,
    craftId: String,
    name: String,
    orderViewModel: OrderViewModel
) {
    //coroutineScope
    val scope = rememberCoroutineScope()

    val context = LocalContext.current
    val sharedPreference = SharedPreference(context)
    val token = sharedPreference.getToken.collectAsState(initial = "")

    var loading by remember {
        mutableStateOf(true)
    }
    var exception by remember {
        mutableStateOf(false)
    }
    //state flow list
    val orderList = MutableStateFlow<List<Data>>(emptyList())

    if (token.value.toString().isNotEmpty()) {
        val response: WrapperClass<GetMyOrder, Boolean, Exception> =
            produceState<WrapperClass<GetMyOrder, Boolean, Exception>>(initialValue = WrapperClass()) {
                value = orderViewModel.getMyOrder(
                    authorization = "Bearer ${token.value.toString()}",
                    craftId = craftId
                )
            }.value

        if (response.data?.status == "success") {
            if (response.data!!.data != null) {
                scope.launch {
                    loading = false
                    orderList.emit(response.data!!.data!!)
                }
            } else {
                loading = false
            }
        } else if (response.data?.status == "fail" || response.data?.status == "error" || response.e != null) {
            exception = true
            Toast.makeText(
                context,
                "خطأ في الانترنت",
                Toast.LENGTH_SHORT
            ).show()
        }

    }

    // Swipe Refresh
    var swipeLoading by remember {
        mutableStateOf(false)
    }

    val swipeRefreshState = rememberSwipeRefreshState(isRefreshing = swipeLoading)
    SwipeRefresh(state = swipeRefreshState,
        onRefresh = {
            swipeLoading = true
            loading = false
            scope.launch {
                val orderData: WrapperClass<GetMyOrder, Boolean, Exception> =
                    orderViewModel.getMyOrder(
                        authorization = "Bearer ${token.value.toString()}",
                        craftId = craftId
                    )
                if (orderData.data?.status == "success") {
                    if (orderData.data != null) {
                        orderList.emit(orderData.data!!.data!!)
                        swipeLoading = false
                    }
                } else {
                    swipeLoading = false
                }
            }
        })
    {
        Scaffold(topBar = {
            TopAppBar(title = name) {
                navController.navigate(AllScreens.ClientHomeScreen.name + "/order") {
                    navController.popBackStack()
                }
            }
        })
        {
            if (!loading && !exception) {
                if (orderList.value.isNotEmpty()) {
                    LazyColumn(modifier = Modifier.fillMaxSize()) {
                        items(orderList.value) { itemlist ->
                            MyCraftOrdersRow(item = itemlist,
                                onClick = { orderData ->
                                    //nav to Offer Screen
                                    navController.navigate(AllScreens.ClientOrderOfferScreen.name + "/${orderData.title}")
                                },
                                onDeleteItem = { deleteOrder ->
                                    //Delete Problem "Trash Icon"
                                    loading = true
                                    scope.launch {
                                        val deleteResponse: WrapperClass<Delete, Boolean, Exception> =
                                            orderViewModel.deleteOrder(
                                                craftId = craftId,
                                                authorization = "Bearer ${token.value.toString()}",
                                                orderId = deleteOrder._id
                                            )

                                        if (deleteResponse.data?.status == "success") {
                                            val orderData: WrapperClass<GetMyOrder, Boolean, Exception> =
                                                orderViewModel.getMyOrder(
                                                    authorization = "Bearer ${token.value.toString()}",
                                                    craftId = craftId
                                                )
                                            if (orderData.data?.status == "success") {
                                                if (orderData.data != null) {
                                                    orderList.emit(orderData.data!!.data!!)
                                                    loading = false
                                                }
                                            }
                                            else {
                                                loading = false
                                            }
                                        }
                                        else {
                                            loading = false
                                            Toast.makeText(
                                                context,
                                                "حدث خطأ ما",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                        }
                                    }
                                })
                            Spacer(modifier = Modifier.height(15.dp))
                        }
                    }
                } else {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        item {
                            Text(
                                text = "لا يوجد طلبات حاليا", style = TextStyle(
                                    color = MainColor,
                                    fontWeight = FontWeight.SemiBold,
                                    fontSize = 20.sp
                                )
                            )
                        }
                    }
                }
            } else if (loading && !exception) {
                CircleProgress()
            } else if (exception) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    IconButton(onClick = {
                        exception = false
                        loading = true
                        scope.launch {
                            val reloadResponse: WrapperClass<GetMyOrder, Boolean, Exception> =
                                orderViewModel.getMyOrder(
                                    authorization = "Bearer ${token.value.toString()}",
                                    craftId = craftId
                                )
                            if (reloadResponse.data?.status == "success") {
                                if (reloadResponse.data != null) {
                                    scope.launch {
                                        orderList.emit(reloadResponse.data!!.data!!)
                                        loading = false
                                        exception = false
                                    }
                                }
                            } else if (reloadResponse.data?.status == "fail" || reloadResponse.e != null) {
                                exception = true
                                Toast.makeText(
                                    context,
                                    "خطأ في الانترنت",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    }) {
                        Icon(
                            imageVector = Icons.Default.Refresh, contentDescription = null,
                            modifier = Modifier.size(60.dp)
                        )
                    }
                }
            }
        }
    }

}


@Composable
fun MyCraftOrdersRow(
    item: Data,
    onClick: (Data) -> Unit,
    onDeleteItem: (Data) -> Unit,
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
                    text = item.title,
                    style = TextStyle(
                        color = MainColor,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 20.sp
                    )
                )
                Text(
                    text = item.orderDifficulty, style = TextStyle(
                        color = Color.Gray,
                        fontSize = 18.sp
                    )
                )
                Text(
                    text = if (item.orderHavingOffers) "قيد التنفيذ..." else "قيد الانتظار...",
                    style = TextStyle(
                        color = RedColor,
                        fontSize = 16.sp
                    )
                )
            }
            Row {
                if (!item.orderHavingOffers) {
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

