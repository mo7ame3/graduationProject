package com.example.graduationproject.screens.sharedScreens.login

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.verticalScroll
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
import com.example.graduationproject.constant.Constant
import com.example.graduationproject.data.WrapperClass
import com.example.graduationproject.model.login.Login
import com.example.graduationproject.model.register.Register
import com.example.graduationproject.model.register.myCraft.MyCraft
import com.example.graduationproject.navigation.AllScreens
import com.example.graduationproject.sharedpreference.SharedPreference
import com.example.graduationproject.ui.theme.MainColor
import kotlinx.coroutines.launch

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun LoginScreen(
    navController: NavController, authenticationViewModel: AuthenticationViewModel
) {

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
    val email = rememberSaveable {
        mutableStateOf("")
    }
    val passwordLogin = rememberSaveable {
        mutableStateOf("")
    }
    val emailError = remember {
        mutableStateOf(false)
    }
    val eye = remember {
        mutableStateOf(false)
    }
    val keyboardController = LocalSoftwareKeyboardController.current
    val passwordError = remember {
        mutableStateOf(false)
    }
    val valid = (passwordError.value && emailError.value)

//                  Register Variables
    val nameError = remember {
        mutableStateOf(false)
    }
    val emailErrorRegister = remember {
        mutableStateOf(false)
    }
    val nameBack = rememberSaveable {
        mutableStateOf("")
    }
    val emailBack = rememberSaveable {
        mutableStateOf("")
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
    // loading variable
    val loading = remember {
        mutableStateOf(false)
    }
    // box design
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MainColor)
    ) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            shape = RoundedCornerShape(topEnd = 200.dp, bottomStart = 200.dp)
        ) {
            if (!loading.value) {
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
                                email.value = ""
                            }) {
                                Icon(
                                    imageVector = Icons.Default.ArrowBack,
                                    contentDescription = null
                                )
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
                        if (toggledTopButton) {
                            EmailInput(
                                email = email,
                                isNotError = emailError,
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
                                Text(
                                    text = "هل نسيت كلمة السر ؟",
                                    color = MainColor,
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.SemiBold
                                )
                            }

                            Spacer(modifier = Modifier.height(20.dp))

                            LoginButton(
                                isLogin = true, enabled = valid, label = "سجل الدخول"
                            ) {
                                //Getting Login values tp pass to API
                                scope.launch {
                                    loading.value = true
                                    val login: WrapperClass<Login, Boolean, Exception> =
                                        authenticationViewModel.login(
                                            email = email.value,
                                            password = passwordLogin.value
                                        )
                                    if (login.data?.status == "success") {
                                        sharedPreference.saveName(login.data!!.data?.user!!.name)
                                        sharedPreference.saveToken(login.data!!.token.toString())
                                        Constant.token = login.data!!.token.toString()
                                        when (login.data!!.data?.user?.role) {
                                            "client" -> {
                                                sharedPreference.saveState("client")
                                                navController.navigate(AllScreens.ClientHomeScreen.name + "/login") {
                                                    navController.popBackStack()
                                                }
                                            }

                                            "worker" -> {
                                                sharedPreference.saveState("worker")
                                                navController.navigate(AllScreens.WorkerHomeScreen.name + "/login") {
                                                    navController.popBackStack()
                                                }
                                            }

                                            else -> {
                                                sharedPreference.saveState("admin")
                                                navController.navigate(AllScreens.AdminHomeScreen.name) {
                                                    navController.popBackStack()
                                                }
                                            }
                                        }
                                    } else {
                                        loading.value = false
                                        Toast.makeText(
                                            context,
                                            login.data?.message,
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                }
                            }
                        } else {
                            Column(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .verticalScroll(rememberScrollState())
                                    .padding(bottom = 30.dp),
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
                                            scope.launch {
                                                loading.value = true
                                                val register: WrapperClass<Register, Boolean, Exception> =
                                                    authenticationViewModel.register(
                                                        name = nameBack.value,
                                                        email = emailBack.value,
                                                        address = addressListBack.value,
                                                        role = if (workerOrClintBack.value == "عميل") "client" else "worker",
                                                        password = passwordBack.value,
                                                        passwordConfirm = passwordConfirmBack.value
                                                    )
                                                if (register.data?.status == "success") {
                                                    sharedPreference.saveName(register.data!!.data?.user!!.name)
                                                    sharedPreference.saveToken(register.data!!.token.toString())
                                                    Constant.token =
                                                        register.data!!.token.toString()
                                                    if (workerOrClintBack.value == "عميل") {
                                                        sharedPreference.saveState("client")
                                                        navController.navigate(AllScreens.ClientHomeScreen.name + "/login") {
                                                            navController.popBackStack()
                                                        }
                                                    } else {
                                                        sharedPreference.saveState("worker")
                                                        val myCraft: WrapperClass<MyCraft, Boolean, Exception> =
                                                            authenticationViewModel.workerChooseCraft(
                                                                token = "Bearer " + register.data!!.token.toString(),
                                                                myCraft = craftListBack.value,
                                                                workerId = register.data!!.data?.user!!.id
                                                            )
                                                        if (myCraft.data?.status == "success") {
                                                            navController.navigate(AllScreens.WorkerHomeScreen.name + "/login") {
                                                                navController.popBackStack()
                                                            }
                                                        } else {
                                                            loading.value = false
                                                            Toast.makeText(
                                                                context,
                                                                register.data?.message,
                                                                Toast.LENGTH_SHORT
                                                            ).show()
                                                        }
                                                    }
                                                } else {
                                                    loading.value = false
                                                    Toast.makeText(
                                                        context,
                                                        register.data?.message,
                                                        Toast.LENGTH_SHORT
                                                    ).show()
                                                }
                                            }
                                        }
                                    }
                                } else {
                                    Register(
                                        name = nameBack,
                                        value = addressListBack,
                                        isNext = isNext,
                                        phoneNumber = emailBack,
                                        nameError = nameError,
                                        phoneError = emailErrorRegister,
                                        backArrow = showBackArrow,
                                    )
                                }
                            }
                        }
                    }

                }
            } else {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    CircularProgressIndicator()
                }
            }
        }
    }
}

