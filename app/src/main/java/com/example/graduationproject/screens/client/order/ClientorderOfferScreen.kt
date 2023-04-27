package com.example.graduationproject.screens.client.order

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.graduationproject.R
import com.example.graduationproject.components.*
import com.example.graduationproject.data.OffersList
import com.example.graduationproject.navigation.AllScreens
import com.example.graduationproject.ui.theme.GoldColor
import com.example.graduationproject.ui.theme.MainColor
import com.example.graduationproject.ui.theme.RedColor

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ClientOrderOfferScreen(
    navController: NavController,
    title: String
) {
    //Bottom Bar Variables
    val home = remember {
        mutableStateOf("")
    }
    Scaffold(topBar = {
        TopAppBar(title = title) {
            navController.popBackStack()
        }
    },
        bottomBar = {
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
        }) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(25.dp)
        ) {
            //ProblemDescription
            ProblemDescription(
                problemDescription = "لقد قمت بتحتطيم الكرسي الخاص بي و اريد من يقوم بمساعدتي في حل هذه المشكله واللتي ادت اللي عدم استعمالي لهذا الكرسي مره اخري و قمت بتحطيم الدولاب الخاص بي و قد تدني ويكون نجار كويس للتصليح و السعر يكون معقول جداااااا " +
                        "\"لقد قمت بتحتطيم الكرسي الخاص بي و اريد من يقوم بمساعدتي في حل هذه المشكله واللتي ادت اللي عدم استعمالي لهذا الكرسي مره اخري و قمت بتحطيم الدولاب الخاص بي و قد تدني ويكون نجار كويس للتصليح و السعر يكون معقول جداااااا "
            )

            Spacer(modifier = Modifier.height(10.dp))
            // تاكيد و تراجع
            val id = remember {
                mutableStateOf(-1)
            }
            val acceptState = remember {
                mutableStateOf(false)
            }
            // تأكيد اكتمال المروع و تأكيد الغاء العمل
            val confirmed = remember {
                mutableStateOf(false)
            }
            //
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                //هيظهر كلة في حالة قيد الانتظار
                items(offersListForTesting) { item ->
                    //Accept
                    //هيظهر ف حالة قيد التنفيذ
                    if (id.value != -1 && acceptState.value) {
                        if (item.id == id.value) {
                            AcceptRow(
                                item = item,
                                navController = navController,
                                onConfirmAction = {
                                    confirmed.value = true
                                },
                                confirmed = confirmed,
                                onCancelAction = {
                                    id.value = -1
                                },
                                onCompleteProjectConfirmation = {
                                    navController.navigate(route = AllScreens.ClientRateScreen.name)
                                },
                                onCancelProjectConfirmation = {
                                    confirmed.value = false
                                    id.value = -1
                                }
                            )
                        }
                    }
                    //Reject
                    else if (id.value != -1 && !acceptState.value) {
                        //Delete Offer
                        if (item.id != id.value) {
                            OfferRow(item = item, onAcceptAction = { AcceptedWorker ->
                                id.value = AcceptedWorker.id
                                acceptState.value = true
                            }, onRejectAction = { RejectedWorker ->
                                id.value = RejectedWorker.id
                                acceptState.value = false
                            }, navController = navController)
                        }
                    }
                    //Not Accept and Not Reject
                    else {
                        OfferRow(item = item, onAcceptAction = { AcceptedWorker ->
                            id.value = AcceptedWorker.id
                            acceptState.value = true
                        }, onRejectAction = { RejectedWorker ->
                            id.value = RejectedWorker.id
                            acceptState.value = false
                        }, navController = navController)
                    }
                }
                item {
                    Spacer(modifier = Modifier.height(30.dp))
                }
            }
        }
    }
}


@Composable
fun OfferRow(
    item: OffersList,
    onAcceptAction: (OffersList) -> Unit,
    onRejectAction: (OffersList) -> Unit,
    navController: NavController
) {
    //The Whole Row
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    )
    {
        //Navigate to Profile Row
        Row(modifier = Modifier
            .width(200.dp)
            .clickable {
                navController.navigate(route = AllScreens.WorkerProfileScreen.name + "/${true}/${false}/${item.name}")
            }) {
            SmallPhoto()
            Spacer(modifier = Modifier.width(5.dp))
            Column {
                Text(
                    text = item.name,
                    style = TextStyle(
                        color = MainColor,
                        fontSize = 17.sp
                    ),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = item.bio,
                    style = TextStyle(color = RedColor, fontSize = 12.sp),
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1
                )
                StarsNumber(item.stars)
            }
        }
        Row(modifier = Modifier.fillMaxWidth()) {
            //Accept Button
            LoginButton(isLogin = true, label = "قبول", modifier = Modifier.width(65.dp)) {
                onAcceptAction.invoke(item)
            }
            Spacer(modifier = Modifier.width(10.dp))
            //Reject Button
            LoginButton(isLogin = false, label = "رفض", modifier = Modifier.width(65.dp)) {
                onRejectAction.invoke(item)
            }
        }
    }

}

val offersListForTesting = listOf(
    OffersList(0, "ريشة", "نجار محترف خبره سنتين", 5),
    OffersList(1, "رزة", "نجار محترف خبره 3 سنين", 4),
    OffersList(2, "أحمد محمد", "نجار محترف خبره 4 سنين", 3),
    OffersList(3, "جحا", "نجار محترف خبره سنتين", 5),
    OffersList(4, "احمد محمد", "نجار مبتدأ", 2),
    OffersList(5, "جحا", "نجار مبندأ", 1),
    OffersList(6, "جحا", "نجار", 5),
    OffersList(7, "احمد محمد", "نجار", 5),
    OffersList(8, "جحا", "نجار", 5),
    OffersList(9, "احمد محمد", "نجار", 5),
    OffersList(10, "احمد محمد ", "نجار", 5),

    )

@Composable
fun AcceptRow(
    item: OffersList,
    confirmed: MutableState<Boolean>,
    onConfirmAction: () -> Unit,
    onCancelAction: () -> Unit,
    navController: NavController,
    onCompleteProjectConfirmation: () -> Unit,
    onCancelProjectConfirmation: () -> Unit,
) {
    Column {
        //The Whole Row
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        )
        {
            //Navigate to Profile Row
            Row(modifier = Modifier
                .width(200.dp)
                .clickable {
                    navController.navigate(route = AllScreens.WorkerProfileScreen.name + "/${true}/${false}/${item.name}")
                }) {
                SmallPhoto()
                Spacer(modifier = Modifier.width(5.dp))
                Column {
                    Text(
                        text = item.name,
                        style = TextStyle(
                            color = MainColor,
                            fontSize = 17.sp
                        ),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        text = item.bio,
                        style = TextStyle(color = RedColor, fontSize = 12.sp),
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1
                    )
                    Row {
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = null,
                            tint = if (item.stars >= 1) GoldColor else Color.Transparent
                        )
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = null,
                            tint = if (item.stars >= 2) GoldColor else Color.Transparent
                        )
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = null,
                            tint = if (item.stars >= 3) GoldColor else Color.Transparent
                        )
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = null,
                            tint = if (item.stars >= 4) GoldColor else Color.Transparent
                        )
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = null,
                            tint = if (item.stars == 5) GoldColor else Color.Transparent
                        )

                    }
                }
            }
            Row {
                IconButton(onClick = { navController.navigate(route = AllScreens.ChatDetails.name + "/${item.id}") }) {
                    Icon(
                        painter = painterResource(id = R.drawable.chat),
                        contentDescription = null,
                        modifier = Modifier.size(40.dp), tint = MainColor
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(3.dp))
        //confirm and cancel buttons
        if (!confirmed.value) {
            Row(modifier = Modifier.fillMaxWidth()) {
                //Accept Button
                LoginButton(isLogin = true, label = "تأكيد", modifier = Modifier.width(80.dp)) {
                    onConfirmAction.invoke()
                }
                Spacer(modifier = Modifier.width(10.dp))
                //Reject Button
                LoginButton(isLogin = false, label = "تراجع", modifier = Modifier.width(80.dp)) {
                    onCancelAction.invoke()
                }
            }
        } else {
            LoginButton(
                isLogin = true,
                label = "تأكيد اكتمال المشروع",
                modifier = Modifier.fillMaxWidth()
            ) {
                onCompleteProjectConfirmation.invoke()
            }
            Spacer(modifier = Modifier.height(5.dp))
            //Reject Button
            LoginButton(
                isLogin = false,
                label = "تأكيد إلغاء العمل",
                modifier = Modifier.fillMaxWidth()
            ) {
                onCancelProjectConfirmation.invoke()
            }

        }
    }
}