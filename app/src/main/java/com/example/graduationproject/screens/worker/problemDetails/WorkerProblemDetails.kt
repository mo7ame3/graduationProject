package com.example.graduationproject.screens.worker.problemDetails

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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.graduationproject.components.CircleProgress
import com.example.graduationproject.components.DefaultButton
import com.example.graduationproject.components.GetSmallPhoto
import com.example.graduationproject.components.InternetPhoto
import com.example.graduationproject.components.PickPhoto
import com.example.graduationproject.components.ProblemDescription
import com.example.graduationproject.components.TextInput
import com.example.graduationproject.components.TopAppBar
import com.example.graduationproject.data.WrapperClass
import com.example.graduationproject.model.worker.createOffer.CreateOffer
import com.example.graduationproject.model.worker.orderDetails.GetOrderDetails
import com.example.graduationproject.model.worker.orderDetails.Order
import com.example.graduationproject.navigation.AllScreens
import com.example.graduationproject.sharedpreference.SharedPreference
import com.example.graduationproject.ui.theme.MainColor
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

@SuppressLint(
    "UnusedMaterialScaffoldPaddingParameter", "CoroutineCreationDuringComposition",
    "StateFlowValueCalledInComposition"
)
@Composable
fun WorkerProblemDetails(
    navController: NavController,
    orderID: String,
    workerProblemDetailsViewModel: WorkerProblemDetailsViewModel
) {
    //Log out Variables
    val context = LocalContext.current
    val sharedPreference = SharedPreference(context)
    val token = sharedPreference.getToken.collectAsState(initial = "")
    val craftId = sharedPreference.getCraftId.collectAsState(initial = "")

    var loading by remember {
        mutableStateOf(true)
    }
    var exception by remember {
        mutableStateOf(false)
    }
    val offerDetails = remember {
        mutableStateOf("")
    }
    val scope = rememberCoroutineScope()


    //state flow list
    val orderDetails = MutableStateFlow<List<Order>>(emptyList())
    if (token.value.toString().isNotEmpty() && craftId.value.toString().isNotEmpty()) {
        val response: WrapperClass<GetOrderDetails, Boolean, Exception> =
            produceState<WrapperClass<GetOrderDetails, Boolean, Exception>>(initialValue = WrapperClass()) {
                value = workerProblemDetailsViewModel.getOrderDetails(
                    authorization = "Bearer " + token.value.toString(),
                    craftId = craftId.value.toString(),
                    orderId = orderID
                )
            }.value

        if (response.data?.status == "success") {
            scope.launch {
                loading = false
                exception = false
                orderDetails.emit(response.data!!.data!!.order)
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

    Scaffold(topBar = {
        TopAppBar(title = "تفاصيل الطلب") {
            navController.popBackStack()
        }
    }) {
        if (!loading && !exception) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 25.dp, end = 25.dp),
            ) {
                item {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                //get Client id from database and nav to client profile
                                navController.navigate(route = AllScreens.ClientProfileScreen.name + "/${true}/${false}/${false}/ ")
                            },
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        GetSmallPhoto(uri = if (orderDetails.value[0].user.avatar != null) orderDetails.value[0].user.avatar.toString() else null)
                        Spacer(modifier = Modifier.width(5.dp))
                        Text(
                            text = orderDetails.value[0].user.name, style = TextStyle(
                                color = MainColor,
                                fontSize = 15.sp
                            )
                        )
                    }
                    Spacer(modifier = Modifier.height(20.dp))
                    Row(
                        modifier = Modifier.padding(start = 25.dp),
                    ) {
                        Text(
                            text = "${orderDetails.value[0].title}- ${orderDetails.value[0].orderDifficulty}",
                            style = TextStyle(
                                color = MainColor,
                                fontSize = 18.sp
                            )
                        )
                    }
                    Spacer(modifier = Modifier.height(15.dp))
                    //ProblemDescription
                    ProblemDescription(problemDescription = orderDetails.value[0].description)

                    Spacer(modifier = Modifier.height(20.dp))
                    Surface(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(220.dp),
                        shape = RoundedCornerShape(25.dp),
                        border = BorderStroke(width = 1.dp, color = MainColor)
                    ) {
                        //change photo
                        if (orderDetails.value[0].avatar == null) {
                            PickPhoto()
                        } else {
                            InternetPhoto(uri = orderDetails.value[0].avatar.toString())
                        }
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                    Surface(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(220.dp),
                        shape = RoundedCornerShape(25.dp),
                        border = BorderStroke(width = 1.dp, color = MainColor)
                    ) {
                        Column(modifier = Modifier.fillMaxSize()) {
                            TextInput(
                                text = offerDetails,
                                isBorder = false,
                                label = if (offerDetails.value.isEmpty()) "اكتب تفاصيل العرض هنا..." else "",
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(0.dp),
                                isSingleLine = false,
                                isNotBackground = true,
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        DefaultButton(label = "قدم عرض مساعدة", modifier = Modifier.width(150.dp))
                        {
                            loading = true
                            scope.launch {
                                val response: WrapperClass<CreateOffer, Boolean, Exception> =
                                    workerProblemDetailsViewModel.createOffer(
                                        authorization = "Bearer " + token.value.toString(),
                                        orderId = orderID,
                                        offerDetails = offerDetails.value.ifBlank { null }
                                    )
                                if (response.data?.status == "success") {
                                    navController.navigate(AllScreens.WorkerHomeScreen.name + "/login") {
                                        navController.popBackStack()
                                    }
                                }
                                else if (response.data?.status == "fail" || response.data?.status == "error" || response.e != null) {
                                    loading = false
                                    Toast.makeText(
                                        context,
                                        "خطأ في الانترنت",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                        }
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
                        val reloadResponse: WrapperClass<GetOrderDetails, Boolean, Exception> =
                            workerProblemDetailsViewModel.getOrderDetails(
                                authorization = "Bearer " + token.value.toString(),
                                craftId = craftId.value.toString(),
                                orderId = orderID
                            )
                        if (reloadResponse.data?.status == "success") {
                            if (reloadResponse.data != null) {
                                scope.launch {
                                    orderDetails.emit(reloadResponse.data!!.data?.order!!)
                                    loading = false
                                    exception = false
                                }
                            }
                        } else if (reloadResponse.data?.status == "fail" || reloadResponse.data?.status == "error" || reloadResponse.e != null) {
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