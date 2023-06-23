package com.example.graduationproject.screens.client.order

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.graduationproject.R
import com.example.graduationproject.components.BottomBar
import com.example.graduationproject.components.CircleProgress
import com.example.graduationproject.components.GetSmallPhoto
import com.example.graduationproject.components.LoginButton
import com.example.graduationproject.components.ProblemDescription
import com.example.graduationproject.components.StarsNumber
import com.example.graduationproject.components.TopAppBar
import com.example.graduationproject.data.WrapperClass
import com.example.graduationproject.model.admin.deleteCraft.Delete
import com.example.graduationproject.model.client.offerOfAnOrder.Data
import com.example.graduationproject.model.client.offerOfAnOrder.GetOfferOfAnOrder
import com.example.graduationproject.model.client.updateOrder.UpdateOrder
import com.example.graduationproject.model.shared.updateOffer.UpdateOffer
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
    "SuspiciousIndentation", "StateFlowValueCalledInComposition"
)
@Composable
fun ClientOrderOfferScreen(
    navController: NavController,
    orderViewModel: OrderViewModel,
    orderTitle: String,
    orderDescription: String,
    orderId: String,
    craftId: String,
) {
    //Bottom Bar Variables
    val home = remember {
        mutableStateOf("")
    }
    val context = LocalContext.current
    val sharedPreference = SharedPreference(context)
    val token = sharedPreference.getToken.collectAsState(initial = "")
    var loading by remember {
        mutableStateOf(true)
    }
    var haveOffer by remember {
        mutableStateOf(false)
    }

    val acceptOffer = remember {
        mutableStateOf(false)
    }

    val acceptedId = remember {
        mutableStateOf("")
    }
    val offerCanceled = remember {
        mutableStateOf(0)
    }
    val length = remember {
        mutableStateOf(0)
    }

    //coroutineScope
    val scope = rememberCoroutineScope()

    //state flow list
    val offersList = MutableStateFlow<List<Data>>(emptyList())

    //response
    if (token.value.toString().isNotEmpty()) {
        val offerData: WrapperClass<GetOfferOfAnOrder, Boolean, Exception> =
            produceState<WrapperClass<GetOfferOfAnOrder, Boolean, Exception>>(
                initialValue = WrapperClass(data = null)
            ) {
                value = orderViewModel.getOfferOfAnOrder(
                    authorization = "Bearer ${token.value.toString()}",
                    orderId = orderId
                )
            }.value
        if (offerData.data?.status == "success") {
            if (!offerData.data!!.data.isNullOrEmpty()) {
                scope.launch {
                    offersList.emit(offerData.data!!.data!!)
                    haveOffer = offerData.data!!.length > 0
                    offersList.value.forEach { t ->
                        length.value = length.value + 1
                        when (t.status) {
                            "accepted" -> {
                                acceptOffer.value = true
                                acceptedId.value = t.id
                                loading = false
                            }

                            "canceled" -> {
                                offerCanceled.value = offerCanceled.value + 1
                                if (length.value == offersList.value.size) {
                                    loading = false
                                }
                            }

                            else -> {
                                loading = false
                            }
                        }
                    }
                }
            } else {
                loading = false
            }
        }
        else if (offerData.data?.status == "fail" || offerData.data?.status == "error" || offerData.e != null) {
            Toast.makeText(
                context,
                "خطأ في الانترنت",
                Toast.LENGTH_SHORT
            ).show()
        }
    }


    Scaffold(topBar = {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (!loading && offerCanceled.value == offersList.value.size) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start,
                    modifier = Modifier.padding(start = 10.dp)
                ) {
                    IconButton(onClick = {
                        loading = true
                        scope.launch {
                            val deleteResponse: WrapperClass<Delete, Boolean, Exception> =
                                orderViewModel.deleteOrder(
                                    craftId = craftId,
                                    authorization = "Bearer ${token.value.toString()}",
                                    orderId = orderId
                                )

                            if (deleteResponse.data?.status == "success") {
                                navController.popBackStack()
                            } else {
                                loading = false
                                Toast.makeText(
                                    context,
                                    "حدث خطأ ما",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    }) {
                        Icon(
                            painter = painterResource(id = R.drawable.trash),
                            contentDescription = null,
                            tint = MainColor,
                            modifier = Modifier.size(30.dp)
                        )
                    }
                }
            } else {
                Row {
                }
            }
            Text(text = orderTitle, style = TextStyle(color = MainColor, fontSize = 18.sp))
            TopAppBar(title = "", isProfile = true) {
                navController.popBackStack()
            }
        }

    }, bottomBar = {
        BottomBar(selected = home, home = {
            navController.navigate(route = AllScreens.ClientHomeScreen.name + "/${home.value}") {
                navController.popBackStack()
                navController.popBackStack()
                navController.popBackStack()
            }
        }, chat = {
            navController.navigate(route = AllScreens.ClientHomeScreen.name + "/${home.value}") {
                navController.popBackStack()
                navController.popBackStack()
                navController.popBackStack()
            }
        }, order = {
            navController.navigate(route = AllScreens.ClientHomeScreen.name + "/${home.value}") {
                navController.popBackStack()
                navController.popBackStack()
                navController.popBackStack()
            }
        })
    })
    {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(25.dp)
        ) {
            //ProblemDescription
            ProblemDescription(
                problemDescription = orderDescription
            )
            Spacer(modifier = Modifier.height(10.dp))

            // Swipe Refresh
            var swipeLoading by remember {
                mutableStateOf(false)
            }
            val swipeRefreshState = rememberSwipeRefreshState(isRefreshing = swipeLoading)

            SwipeRefresh(state = swipeRefreshState,
                onRefresh = {
                    scope.launch {
                        swipeLoading = true
                        val refreshData: WrapperClass<GetOfferOfAnOrder, Boolean, Exception> =
                            orderViewModel.getOfferOfAnOrder(
                                authorization = "Bearer ${token.value.toString()}",
                                orderId = orderId
                            )
                        if (refreshData.data?.status == "success") {
                            if (refreshData.data != null) {
                                offersList.emit(refreshData.data!!.data!!)
                                swipeLoading = false
                            }
                        } else {
                            swipeLoading = false
                        }
                    }
                })
            {
                if (loading && !haveOffer) {
                    CircleProgress()
                } else if (!loading && haveOffer) {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = if (offerCanceled.value == offersList.value.size) Arrangement.Center else Arrangement.Top
                    ) {
                        if (offerCanceled.value != offersList.value.size) {
                            items(offersList.value) { item ->
                                //Not Accept and Not Reject
                                OfferRow(
                                    item = item,
                                    onAcceptAction = { AcceptedWorker ->
                                        scope.launch {
                                            loading = true
                                            haveOffer = false
                                            val acceptWorker: WrapperClass<UpdateOffer, Boolean, Exception> =
                                                orderViewModel.updateOffer(
                                                    authorization = "Bearer ${token.value.toString()}",
                                                    offerId = AcceptedWorker._id,
                                                    text = "",
                                                    status = "accepted"
                                                )
                                            if (acceptWorker.data?.status == "success") {
                                                val updateOrder: WrapperClass<UpdateOrder, Boolean, Exception> =
                                                    orderViewModel.updateOrderStatus(
                                                        authorization = "Bearer ${token.value.toString()}",
                                                        orderId = orderId,
                                                        craftId = craftId,
                                                        status = "carryingout"
                                                    )
                                                if (updateOrder.data?.status == "success") {
                                                    offersList.value.forEach { t ->
                                                        if (t.id == AcceptedWorker.id) {
                                                            acceptOffer.value = true
                                                            acceptedId.value = AcceptedWorker.id
                                                            loading = false
                                                            haveOffer = true
                                                        } else {
                                                            loading = false
                                                            haveOffer = true
                                                        }
                                                    }
                                                }

                                            }
                                            else {
                                                loading = false
                                                haveOffer = true
                                                Toast.makeText(
                                                    context,
                                                    "خطأ في الانترنت",
                                                    Toast.LENGTH_SHORT
                                                ).show()
                                            }
                                        }
                                    },
                                    onRejectAction = { RejectedWorker ->
                                        scope.launch {
                                            loading = true
                                            haveOffer = false
                                            val rejectWorker: WrapperClass<UpdateOffer, Boolean, Exception> =
                                                orderViewModel.updateOffer(
                                                    authorization = "Bearer ${token.value.toString()}",
                                                    offerId = RejectedWorker._id,
                                                    text = "",
                                                    status = "canceled"
                                                )
                                            if (rejectWorker.data?.status == "success") {
                                                offerCanceled.value = offerCanceled.value + 1
                                                val refreshData: WrapperClass<GetOfferOfAnOrder, Boolean, Exception> =
                                                    orderViewModel.getOfferOfAnOrder(
                                                        authorization = "Bearer ${token.value.toString()}",
                                                        orderId = orderId
                                                    )
                                                if (refreshData.data?.status == "success") {
                                                    if (refreshData.data != null) {
                                                        offersList.emit(refreshData.data!!.data!!)
                                                        loading = false
                                                        haveOffer = true
                                                    }
                                                } else {
                                                    loading = false
                                                    haveOffer = true
                                                }
                                            } else {
                                                loading = false
                                                haveOffer = true
                                                Toast.makeText(
                                                    context,
                                                    "خطأ في الانترنت",
                                                    Toast.LENGTH_SHORT
                                                ).show()
                                            }
                                        }
                                    },
                                    onCompleteAction = { completeOrder ->
                                        loading = true
                                        haveOffer = false
                                        scope.launch {
                                            val acceptWorker: WrapperClass<UpdateOffer, Boolean, Exception> =
                                                orderViewModel.updateOffer(
                                                    authorization = "Bearer ${token.value.toString()}",
                                                    offerId = completeOrder._id,
                                                    text = "",
                                                    status = "completed"
                                                )
                                            if (acceptWorker.data?.status == "success") {
                                                val updateOrder: WrapperClass<UpdateOrder, Boolean, Exception> =
                                                    orderViewModel.updateOrderStatus(
                                                        authorization = "Bearer ${token.value.toString()}",
                                                        orderId = orderId,
                                                        craftId = craftId,
                                                        status = "orderDone"
                                                    )
                                                if (updateOrder.data?.status == "success") {
                                                    navController.navigate(route = AllScreens.ClientHomeScreen.name + "/orderdone")
                                                }
                                            }
                                            else {
                                                loading = false
                                                haveOffer = true
                                                Toast.makeText(
                                                    context,
                                                    "خطأ في الانترنت",
                                                    Toast.LENGTH_SHORT
                                                ).show()
                                            }
                                        }
                                    },
                                    onRejectCompleteAction = { RejectCompleteAction ->
                                        scope.launch {
                                            loading = true
                                            haveOffer = false
                                            val rejectWorker: WrapperClass<UpdateOffer, Boolean, Exception> =
                                                orderViewModel.updateOffer(
                                                    authorization = "Bearer ${token.value.toString()}",
                                                    offerId = RejectCompleteAction.id,
                                                    text = "",
                                                    status = "pending"
                                                )
                                            if (rejectWorker.data?.status == "success") {
                                                val updateOrder: WrapperClass<UpdateOrder, Boolean, Exception> =
                                                    orderViewModel.updateOrderStatus(
                                                        authorization = "Bearer ${token.value.toString()}",
                                                        orderId = orderId,
                                                        craftId = craftId,
                                                        status = "pending"
                                                    )
                                                if (updateOrder.data?.status == "success") {
                                                    loading = false
                                                    haveOffer = true
                                                    acceptOffer.value = false
                                                    acceptedId.value = ""
                                                }
                                            } else {
                                                loading = false
                                                haveOffer = true
                                                Toast.makeText(
                                                    context,
                                                    "خطأ في الانترنت",
                                                    Toast.LENGTH_SHORT
                                                ).show()
                                            }
                                        }
                                    },
                                    acceptOffer = acceptOffer,
                                    acceptedId = acceptedId,
                                    navController = navController
                                )
                            }
                            item {
                                Spacer(modifier = Modifier.height(10.dp))
                            }
                        } else {
                            item {
                                Text(
                                    text = "لا يوجد عروض حاليا", style = TextStyle(
                                        color = MainColor,
                                        fontWeight = FontWeight.SemiBold,
                                        fontSize = 20.sp
                                    )
                                )
                            }
                        }

                    }
                }
                else {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        item {
                            Text(
                                text = "لا يوجد عروض حاليا", style = TextStyle(
                                    color = MainColor,
                                    fontWeight = FontWeight.SemiBold,
                                    fontSize = 20.sp
                                )
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun OfferRow(
    item: Data,
    onAcceptAction: (Data) -> Unit,
    onRejectAction: (Data) -> Unit,
    onCompleteAction: (Data) -> Unit,
    onRejectCompleteAction: (Data) -> Unit,
    acceptOffer: MutableState<Boolean>,
    acceptedId: MutableState<String>,
    navController: NavController
) {
    //The Whole Row
    if (item.status != "canceled") {
        if (!acceptOffer.value) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            )
            {
                var expanded by remember {
                    mutableStateOf(false)
                }
                //Navigate to Profile Row
                Row(modifier = Modifier
                    .width(200.dp)
                    .clickable {
                        navController.navigate(route = AllScreens.WorkerProfileScreen.name + "/${true}/${false}/${item.worker.name}")
                    }) {
                    GetSmallPhoto(uri = if (item.worker.avatar != null) item.worker.avatar.toString() else null)
                    Spacer(modifier = Modifier.width(5.dp))
                    Column(modifier = Modifier.padding(end = 5.dp)) {
                        Text(
                            text = item.worker.name,
                            style = TextStyle(
                                color = MainColor,
                                fontSize = 17.sp
                            ),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        if (!expanded) {
                            if (!item.worker.bio.isNullOrEmpty()) {
                                Text(
                                    text = item.worker.bio,
                                    style = TextStyle(color = RedColor, fontSize = 12.sp),
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                            }
                            if (item.worker.rate != null && item.worker.rate > 0) {
                                StarsNumber(item.worker.rate)
                            }
                            if (!item.text.isNullOrEmpty()) {
                                Text(
                                    text = item.text,
                                    style = TextStyle(color = MainColor, fontSize = 12.sp),
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                            }
                        } else {
                            AnimatedVisibility(visible = true) {
                                if (!item.worker.bio.isNullOrEmpty()) {
                                    Text(
                                        text = item.worker.bio,
                                        style = TextStyle(color = RedColor, fontSize = 12.sp),
                                    )
                                }
                                if (item.worker.rate != null && item.worker.rate > 0) {
                                    StarsNumber(item.worker.rate)
                                }
                                if (!item.text.isNullOrEmpty()) {
                                    Text(
                                        text = item.text,
                                        style = TextStyle(color = MainColor, fontSize = 12.sp),
                                    )
                                }
                            }
                        }
                        Icon(
                            imageVector = if (expanded) Icons.Filled.KeyboardArrowUp
                            else Icons.Filled.KeyboardArrowDown,
                            contentDescription = "Down Arrow",
                            modifier = Modifier
                                .size(25.dp)
                                .clickable {
                                    expanded = !expanded
                                },
                            tint = Color.DarkGray
                        )
                    }
                }
                Row(modifier = Modifier.fillMaxWidth()) {
                    //Accept Button
                    LoginButton(
                        isLogin = true,
                        label = "قبول",
                        modifier = Modifier.width(65.dp)
                    ) {
                        onAcceptAction.invoke(item)
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    //Reject Button
                    LoginButton(
                        isLogin = false,
                        label = "رفض",
                        modifier = Modifier.width(65.dp)
                    ) {
                        onRejectAction.invoke(item)
                    }
                }

                Spacer(modifier = Modifier.height(25.dp))
            }
        } else if (acceptOffer.value) {
            if (item.id == acceptedId.value) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth(),
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    )
                    {
                        var expanded by remember {
                            mutableStateOf(false)
                        }
                        //Navigate to Profile Row
                        Row(modifier = Modifier
                            .width(300.dp)
                            .clickable {
                                navController.navigate(route = AllScreens.WorkerProfileScreen.name + "/${true}/${false}/${item.worker.name}")
                            }) {
                            GetSmallPhoto(uri = if (item.worker.avatar != null) item.worker.avatar.toString() else null)
                            Spacer(modifier = Modifier.width(5.dp))
                            Column(modifier = Modifier.padding(end = 5.dp)) {
                                Text(
                                    text = item.worker.name,
                                    style = TextStyle(
                                        color = MainColor,
                                        fontSize = 17.sp
                                    ),
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                                if (!expanded) {
                                    if (!item.worker.bio.isNullOrEmpty()) {
                                        Text(
                                            text = item.worker.bio,
                                            style = TextStyle(color = RedColor, fontSize = 12.sp),
                                            maxLines = 1,
                                            overflow = TextOverflow.Ellipsis
                                        )
                                    }
                                    if (item.worker.rate != null && item.worker.rate > 0) {
                                        StarsNumber(item.worker.rate)
                                    }
                                    if (!item.text.isNullOrEmpty()) {
                                        Text(
                                            text = item.text,
                                            style = TextStyle(color = MainColor, fontSize = 12.sp),
                                            maxLines = 1,
                                            overflow = TextOverflow.Ellipsis
                                        )
                                    }
                                } else {
                                    AnimatedVisibility(visible = true) {
                                        if (!item.worker.bio.isNullOrEmpty()) {
                                            Text(
                                                text = item.worker.bio,
                                                style = TextStyle(
                                                    color = RedColor,
                                                    fontSize = 12.sp
                                                ),
                                            )
                                        }
                                        if (item.worker.rate != null && item.worker.rate > 0) {
                                            StarsNumber(item.worker.rate)
                                        }
                                        if (!item.text.isNullOrEmpty()) {
                                            Text(
                                                text = item.text,
                                                style = TextStyle(
                                                    color = MainColor,
                                                    fontSize = 12.sp
                                                ),
                                            )
                                        }
                                    }
                                }
                                Icon(
                                    imageVector = if (expanded) Icons.Filled.KeyboardArrowUp
                                    else Icons.Filled.KeyboardArrowDown,
                                    contentDescription = "Down Arrow",
                                    modifier = Modifier
                                        .size(25.dp)
                                        .clickable {
                                            expanded = !expanded
                                        },
                                    tint = Color.DarkGray
                                )
                            }
                        }
                        Row {
                            IconButton(onClick = { navController.navigate(route = AllScreens.ChatDetails.name + "/${item.worker.id}") }) {
                                Icon(
                                    painter = painterResource(id = R.drawable.chat),
                                    contentDescription = null,
                                    modifier = Modifier.size(40.dp), tint = MainColor
                                )
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(5.dp))
                    Column(modifier = Modifier.fillMaxWidth()) {
                        //Accept Button
                        LoginButton(
                            isLogin = true,
                            label = "تأكيد اكتمال المشروع",
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            onCompleteAction.invoke(item)
                        }
                        Spacer(modifier = Modifier.height(5.dp))
                        //Reject Button
                        LoginButton(
                            isLogin = false,
                            label = "تأكيد إلغاء العمل",
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            onRejectCompleteAction.invoke(item)
                        }
                    }
                }
            }
        }
    }
}

