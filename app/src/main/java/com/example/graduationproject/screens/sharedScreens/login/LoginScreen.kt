package com.example.graduationproject.screens.sharedScreens.login

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.graduationproject.components.*
import com.example.graduationproject.navigation.AllScreens
import com.example.graduationproject.sharedpreference.SharedPreference
import com.example.graduationproject.ui.theme.MainColor
import kotlinx.coroutines.launch

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun LoginScreen(navController: NavController) {

    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val sharedPreference = SharedPreference(context)

    //Toggle between login and register
    var toggledTopButton by remember {
        mutableStateOf(true)
    }
    //When is Next
    val showBackArrow = remember {
        mutableStateOf(false)
    }

    // Register Client or Worker & Password and ConfirmPassword Toggle

    val isNext = remember {
        mutableStateOf(false)
    }
    // Login Variables
    val phoneNumber = rememberSaveable {
        mutableStateOf("01")
    }
    val passwordLogin = rememberSaveable {
        mutableStateOf("")
    }
    // box design
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
                // toggle if not showBackArrow
                if (!showBackArrow.value) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceAround,
                        verticalAlignment = Alignment.Top
                    ) {
                        LoginButton(label = "تسجيل الدخول", isLogin = toggledTopButton) {
                            toggledTopButton = true
                        }
                        LoginButton(label = "حساب جديد", isLogin = !toggledTopButton) {
                            toggledTopButton = false
                        }
                    }
                } else {
                    Row(modifier = Modifier.padding(end = 15.dp)) {
                        IconButton(onClick = {
                            showBackArrow.value = false
                            isNext.value = false
                            passwordLogin.value = ""
                            phoneNumber.value = ""
                        }) {
                            Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null)
                        }
                    }
                }
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 10.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    //verticalArrangement = Arrangement.SpaceBetween
                ) {
                    val phoneError = remember {
                        mutableStateOf(false)
                    }
                    val eye = remember {
                        mutableStateOf(false)
                    }
                    val keyboardController = LocalSoftwareKeyboardController.current
                    val passwordError = remember {
                        mutableStateOf(false)
                    }
                    val valid = (passwordError.value && phoneError.value)

//                  Register Variables
                    val nameError = remember {
                        mutableStateOf(false)
                    }
                    val phoneErrorRegister = remember {
                        mutableStateOf(false)
                    }
                    val nameBack = rememberSaveable {
                        mutableStateOf("")
                    }
                    val phoneBack = rememberSaveable {
                        mutableStateOf("01")
                    }
                    val addressListBack = rememberSaveable {
                        mutableStateOf("")
                    }

                    //RegisterNext values
                    val craftListBack = rememberSaveable {
                        mutableStateOf("")
                    }
                    val workerOrClintBack = rememberSaveable {
                        mutableStateOf("")
                    }
                    val passwordBack = rememberSaveable {
                        mutableStateOf("")
                    }
                    val passwordConfirmBack = rememberSaveable {
                        mutableStateOf("")
                    }
                    val passwordIsNOtError = remember {
                        mutableStateOf(false)
                    }
                    val passwordIsNOtErrorConfirm = remember {
                        mutableStateOf(false)
                    }


                    if (toggledTopButton) {
                        PhoneNumber(phoneNumber = phoneNumber,
                            isNotError = phoneError,
                            onAction = KeyboardActions {
                                keyboardController?.hide()
                            })
                        Spacer(modifier = Modifier.height(10.dp))

                        PasswordInput(password = passwordLogin, eye = eye, onButtonAction = {
                            eye.value = !eye.value
                        }, onAction = KeyboardActions {
                            keyboardController?.hide()
                        }, isNotError = passwordError
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        TextButton(onClick = {
                            navController.navigate(route = AllScreens.ForgotPasswordScreen.name)
                        }) {
                            Text(text = "هل نسيت كلمة السر ؟" , color = MainColor , fontSize = 14.sp , fontWeight = FontWeight.SemiBold )
                        }

                        Spacer(modifier = Modifier.height(20.dp))

                        LoginButton(
                            isLogin = true, enabled = valid, label = "سجل الدخول"
                        ) {
                            //Getting Login values tp pass to API
                            scope.launch {
                                if (phoneNumber.value == "01030718569" && passwordLogin.value == "1234567890000") {
                                    sharedPreference.saveState("client")
                                    navController.navigate(AllScreens.ClientHomeScreen.name + "/login") {
                                        navController.popBackStack()
                                    }

                                }
                                if (phoneNumber.value == "01029932345" && passwordLogin.value == "1234567890000") {
                                    sharedPreference.saveState("worker")
                                    navController.navigate(AllScreens.WorkerHomeScreen.name + "/login") {
                                        navController.popBackStack()
                                    }
                                }
                                if (phoneNumber.value == "01555031763" && passwordLogin.value == "com_sci1234#RAMH") {
                                    sharedPreference.saveState("admin")
                                    navController.navigate(AllScreens.AdminHomeScreen.name) {
                                        navController.popBackStack()
                                    }
                                }
                            }

                            //phoneNumber
                            Log.d("TAG", "phoneNumber: ${phoneNumber.value}")
                            //passwordLogin
                            Log.d("TAG", "phoneNumber: ${passwordLogin.value}")
                        }
                    } else {
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            if (isNext.value) {
                                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                    RegisterNext(
                                        workerOrClintBack = workerOrClintBack,
                                        passwordBack = passwordBack,
                                        passwordConfirmBack = passwordConfirmBack,
                                        craftListBack = craftListBack,
                                        passwordIsNOtError = passwordIsNOtError,
                                        passwordIsNOtErrorConfirm = passwordIsNOtErrorConfirm
                                    ) {
                                        //Getting Register values tp pass to API
                                        //name
                                        if (nameBack.value == "Raghad"
                                            && phoneBack.value == "01030718569" && passwordBack.value == "123456789"
                                        ) {
                                            navController.navigate(AllScreens.ClientHomeScreen.name + "/login")
                                        }

//                                        Log.d("TAG", "nameBack: ${nameBack.value}")
//                                        //Phone
//                                        Log.d("TAG", "phoneBack: ${phoneBack.value}")
//                                        //address
//                                        Log.d("TAG", "listBack: ${addressListBack.value}")
//                                        //worker or clint
//                                        Log.d(
//                                            "TAG", "workerOrClintBack: ${workerOrClintBack.value}"
//                                        )
//                                        //craft
//                                        Log.d("TAG", "craftListBack: ${craftListBack.value}")
//                                        //password
//                                        Log.d("TAG", "passwordRegister: ${passwordBack.value}")
//                                        //passwordConfirm
//                                        Log.d("TAG", "passwordConfirm: ${passwordConfirmBack.value}")
                                    }
                                }
                            } else {
                                Register(
                                    name = nameBack,
                                    value = addressListBack,
                                    isNext = isNext,
                                    phoneNumber = phoneBack,
                                    nameError = nameError,
                                    phoneError = phoneErrorRegister,
                                    backArrow = showBackArrow,
                                )
                            }
                        }
                    }
                }

            }
        }
    }
}