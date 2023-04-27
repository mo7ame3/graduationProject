package com.example.graduationproject.screens.client.profileSettings

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.graduationproject.components.*

@OptIn(ExperimentalComposeUiApi::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ClientProfileSettingsScreen(navController: NavController) {
    Scaffold(topBar = {
        TopAppBar(title = "") {
            navController.popBackStack()
        }
    }) {
        //name
        val nameEdit = remember {
            mutableStateOf("رغد محمود صالح")
        }
        //phone
        val phoneEdit = remember {
            mutableStateOf("01030718569")
        }
        val phoneEditNotError = remember {
            mutableStateOf(true)
        }
        //address var
        val addressList = listOf(
            "مركز الفيوم",
            "مركز يوسف الصديق",
            "مركز طامية",
            "مركز سنورس",
            "مركز إطسا",
            "مركز إبشواي"
        )
        val expanded = remember {
            mutableStateOf(false)
        }
        val addressEdit = remember {
            mutableStateOf(addressList[0])
        }

        // is change password

        val isChanePassword = remember {
            mutableStateOf(false)
        }

        // old password

        val oldPasswordEdit = remember {
            mutableStateOf("")
        }
        val oldEye = remember {
            mutableStateOf(false)
        }
        val oldPasswordEditNotError = remember {
            mutableStateOf(false)
        }

        // new password

        val newPasswordEdit = remember {
            mutableStateOf("")
        }
        val newEye = remember {
            mutableStateOf(false)
        }
        val newPasswordEditNotError = remember {
            mutableStateOf(false)
        }

        //confirm password

        val confirmPasswordEdit = remember {
            mutableStateOf("")
        }
        val confirmPasswordEditNotError = remember {
            mutableStateOf(false)
        }
        val keyboardController = LocalSoftwareKeyboardController.current

        //valid check old password from data base

        val valid = if (isChanePassword.value) {
            (nameEdit.value.isNotBlank() && phoneEditNotError.value && addressEdit.value.isNotBlank() && oldPasswordEditNotError.value && newPasswordEditNotError.value && confirmPasswordEditNotError.value)
        } else {
            (nameEdit.value.isNotBlank() && phoneEditNotError.value && addressEdit.value.isNotBlank())
        }


        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()), horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TextInput(text = nameEdit,
                leadingImageVector = Icons.Default.Person,
                isNotBackground = true,
                onAction = KeyboardActions {
                    keyboardController?.hide()
                })
            Spacer(modifier = Modifier.height(5.dp))
            EmailInput(
                email = phoneEdit,
                isNotError = phoneEditNotError,
                onAction = KeyboardActions {
                    keyboardController?.hide()
                },
                leadingImageVector = Icons.Default.Email,
                isNotBackground = true
            )
            Spacer(modifier = Modifier.height(5.dp))
            DropList(
                expanded = expanded,
                value = addressEdit,
                list = addressList,
                isNotBackground = true,
                leadingImageVector = Icons.Default.LocationOn
            )
            if (!isChanePassword.value) {
                Spacer(modifier = Modifier.height(5.dp))
                DefaultButton(label = "تغيير كلمة السر", modifier = Modifier.width(150.dp)) {
                    isChanePassword.value = !isChanePassword.value
                }

            } else {
                Spacer(modifier = Modifier.height(5.dp))
                DefaultButton(label = "عدم تغيير كلمة السر", modifier = Modifier.width(150.dp)) {
                    isChanePassword.value = !isChanePassword.value
                }
            }
            if (isChanePassword.value) {
                PasswordInput(password = oldPasswordEdit,
                    eye = oldEye,
                    onButtonAction = {
                        oldEye.value = !oldEye.value
                    },
                    isNotError = oldPasswordEditNotError,
                    leadingImageVector = Icons.Default.Lock,
                    isNotBackground = true,
                    label = "كلمة السر القديمة",
                    onAction = KeyboardActions {
                        keyboardController?.hide()
                    })
                Spacer(modifier = Modifier.height(5.dp))
                PasswordInput(password = newPasswordEdit,
                    eye = newEye,
                    onButtonAction = {
                        newEye.value = !newEye.value
                    },
                    isNotError = newPasswordEditNotError,
                    leadingImageVector = Icons.Default.Lock,
                    isNotBackground = true,
                    label = "كلمة السر الجديدة",
                    onAction = KeyboardActions {
                        keyboardController?.hide()
                    })
                Spacer(modifier = Modifier.height(5.dp))
                PasswordInput(password = confirmPasswordEdit,
                    eye = newEye,
                    onButtonAction = {
                        newEye.value = !newEye.value
                    },
                    isNotError = confirmPasswordEditNotError,
                    leadingImageVector = Icons.Default.Lock,
                    isNotBackground = true,
                    isConfirmed = true,
                    passwordConfirm = newPasswordEdit,
                    label = "كلمة السر الجديدة",
                    onAction = KeyboardActions {
                        keyboardController?.hide()
                    })
            }
            if (!isChanePassword.value) Spacer(modifier = Modifier.height(302.dp))
            else Spacer(modifier = Modifier.height(100.dp))
            LoginButton(
                label = "تأكيد",
                enabled = valid,
                isLogin = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(45.dp)
                    .padding(start = 25.dp, end = 25.dp)
            ) {
                // nav to home and edit data
                // if isChange password edit all data
                // if isn'tChange password edit name and phone and address
                Log.d("tag", "name: ${nameEdit.value}")
                Log.d("tag", "phone: ${phoneEditNotError.value}")
                Log.d("tag", "address: ${addressEdit.value}")
                Log.d("tag", "oldPasswordEditNotError: ${oldPasswordEditNotError.value}")
                Log.d("tag", "newPasswordEditNotError: ${newPasswordEditNotError.value}")
                Log.d("tag", "ProfileSettingsScreen: ${confirmPasswordEditNotError.value}")
                Log.d("tag", "valid: $valid")
                navController.popBackStack()
            }
        }
    }
}