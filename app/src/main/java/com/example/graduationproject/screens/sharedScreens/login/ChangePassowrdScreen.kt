package com.example.graduationproject.screens.sharedScreens.login

import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.graduationproject.components.LoginButton
import com.example.graduationproject.components.PasswordInput
import com.example.graduationproject.navigation.AllScreens
import com.example.graduationproject.ui.theme.MainColor

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun ChangePasswordScreen(navController: NavHostController) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        shape = RoundedCornerShape(topEnd = 250.dp, bottomStart = 250.dp)
    ) {
        Column(
            modifier = Modifier.padding(top = 150.dp), horizontalAlignment = Alignment.End
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 10.dp, end = 10.dp),
                horizontalArrangement = Arrangement.End
            ) {
                IconButton(onClick = {
                    navController.popBackStack()
                }) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = null,
                        tint = MainColor
                    )
                }
            }
            Column(
                modifier = Modifier.padding(top = 10.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                val keyboardController = LocalSoftwareKeyboardController.current
                //password input
                val password = rememberSaveable {
                    mutableStateOf("")
                }
                val eye = remember {
                    mutableStateOf(false)
                }
                val passwordIsNOtError = remember {
                    mutableStateOf(false)
                }
                //confirmPassword input
                val confirmPassword = rememberSaveable {
                    mutableStateOf("")
                }
                val eyeConfirm = remember {
                    mutableStateOf(false)
                }
                val passwordIsNOtErrorConfirm = remember {
                    mutableStateOf(false)
                }

                // Button
                val valid = (passwordIsNOtError.value && passwordIsNOtErrorConfirm.value)

                PasswordInput(
                    password = password,
                    eye = eye, onButtonAction = {
                        eye.value = !eye.value
                    },
                    onAction = KeyboardActions {
                        keyboardController?.hide()
                    },
                    isNotError = passwordIsNOtError
                )

                PasswordInput(
                    password = confirmPassword,
                    eye = eyeConfirm,
                    onButtonAction = {
                        eyeConfirm.value = !eyeConfirm.value
                    },
                    onAction = KeyboardActions {
                        keyboardController?.hide()
                    },
                    isNotError = passwordIsNOtErrorConfirm,
                    label = "تأكيد كلمة السر",
                    isConfirmed = true,
                    passwordConfirm = password
                )

                Spacer(modifier = Modifier.height(10.dp))

                LoginButton(
                    isLogin = true, enabled = valid, label = "تغيير",
                ) {
                    // send and nav to loginScreen
                    navController.navigate(route = AllScreens.LoginScreen.name) {
                        navController.popBackStack()
                        navController.popBackStack()
                        navController.popBackStack()
                    }
                }
            }
        }
    }
}