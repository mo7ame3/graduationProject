package com.example.graduationproject.screens.sharedScreens.login

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.graduationproject.components.LoginButton
import com.example.graduationproject.components.PhoneNumber
import com.example.graduationproject.components.SMSNumber
import com.example.graduationproject.navigation.AllScreens
import com.example.graduationproject.ui.theme.GrayColor
import com.example.graduationproject.ui.theme.MainColor

@SuppressLint("SuspiciousIndentation")
@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun ForgotPasswordScreen(navController: NavHostController) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MainColor)
    ) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            shape = RoundedCornerShape(topEnd = 250.dp, bottomStart = 250.dp)
        ) {
            Column(
                modifier = Modifier.padding(top = 150.dp), horizontalAlignment = Alignment.End
            ) {
                val keyboardController = LocalSoftwareKeyboardController.current
                //phone
                val phoneNumber = rememberSaveable {
                    mutableStateOf("01")
                }
                val notPhoneError = remember {
                    mutableStateOf(false)
                }
                // Boolean to action searchButton to send SMS
                val sendSMS = remember {
                    mutableStateOf(false)
                }
                // SMS
                val smsNumber = rememberSaveable {
                    mutableStateOf("")
                }
                if(sendSMS.value){
                    Row(modifier = Modifier.fillMaxWidth().padding(start = 10.dp , end = 10.dp),
                        horizontalArrangement = Arrangement.End
                    ) {
                        IconButton(onClick = {
                            sendSMS.value = false
                        }) {
                            Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null, tint = MainColor)
                        }
                    }
                }
                Column( modifier = Modifier.padding(top = 10.dp)) {
                    if (!sendSMS.value) {
                        PhoneNumber(phoneNumber = phoneNumber,
                            isNotError = notPhoneError,
                            onAction = KeyboardActions {
                                keyboardController?.hide()
                            })
                        Spacer(modifier = Modifier.height(10.dp))
                        //Search and Cancel Button
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceAround
                        ) {
                            LoginButton(isLogin = notPhoneError.value, label = "بحث" ) {
                                // Search in dataBase and return Boolean to send SMS
                                if(notPhoneError.value)
                                    sendSMS.value = true
                            }
                            LoginButton(isLogin = !notPhoneError.value, label = "الغاء") {
                                // popBackStack
                                navController.popBackStack()
                            }
                        }
                    } else {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(buildAnnotatedString {
                                withStyle(style = SpanStyle(color = GrayColor, fontSize = 14.sp )){
                                    append("لقد قمنا بارسال رساله الي هذا الرقم ")
                                }
                                withStyle(style = SpanStyle(color = GrayColor,fontSize = 14.sp , fontWeight = FontWeight.Bold)){
                                    append(phoneNumber.value)
                                }})
                            Spacer(modifier = Modifier.height(30.dp))
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceAround,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                SMSNumber(smsNumber = smsNumber,
                                    onAction = KeyboardActions{
                                        keyboardController?.hide()
                                    })
                                LoginButton(isLogin = true, label = "تأكيد", modifier = Modifier.height(45.dp)) {
                                    //if the code is true nav to ChangePasswordScreen
                                    if(smsNumber.value == "123456"){
                                        navController.navigate(AllScreens.ChangePasswordScreen.name)
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}