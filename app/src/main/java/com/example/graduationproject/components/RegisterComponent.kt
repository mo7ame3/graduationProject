package com.example.graduationproject.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import com.example.graduationproject.model.shared.craftList.Craft

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun Register(
    value: MutableState<String>,
    phoneNumber: MutableState<String>,
    name: MutableState<String>,
    isNext: MutableState<Boolean>,
    nameError: MutableState<Boolean>,
    phoneError: MutableState<Boolean>,
    backArrow: MutableState<Boolean>,
) {
    val list = listOf(
        "مركز الفيوم", "مركز يوسف الصديق", "مركز طامية", "مركز سنورس", "مركز إطسا", "مركز إبشواي"
    )
    val expanded = remember {
        mutableStateOf(false)
    }

    val keyboardControllerRegister = LocalSoftwareKeyboardController.current
    val valid = if (value.value.isNotEmpty()) {
        phoneError.value && nameError.value
    } else {
        false
    }
    TextInput(text = name,
        isNotError = nameError,
        onAction = KeyboardActions { keyboardControllerRegister?.hide() })

    EmailInput(email = phoneNumber, isNotError = phoneError, onAction = KeyboardActions {
        keyboardControllerRegister?.hide()
    })

    DropList(expanded = expanded, value = value, list = list)
    Spacer(modifier = Modifier.height(10.dp))
    LoginButton(
        isLogin = true, enabled = valid,
        label = "التالي",

        ) {
        isNext.value = true
        //Arrow Back Navigation
        backArrow.value = true
    }
}


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun RegisterNext(
    workerOrClintBack: MutableState<String>,
    passwordBack: MutableState<String>,
    passwordConfirmBack: MutableState<String>,
    craftIdBack: MutableState<String>,
    craftName: MutableState<String>,
    passwordIsNOtError: MutableState<Boolean>,
    passwordIsNOtErrorConfirm: MutableState<Boolean>,
    craftList: List<Craft>,
    onClick: () -> Unit,
) {
    val workerOrClientList = listOf(
        "عميل", "عامل"
    )
    val workerOrClintExpanded = remember {
        mutableStateOf(false)
    }
    val craftExpanded = remember {
        mutableStateOf(false)
    }
    val eye = remember {
        mutableStateOf(false)
    }
    //Confirm mutable states
    val eyeConfirm = remember {
        mutableStateOf(false)
    }
    val valid = if (workerOrClintBack.value == workerOrClientList[0]) {
        if (workerOrClintBack.value.isNotEmpty()) {
            passwordIsNOtError.value && passwordIsNOtErrorConfirm.value
        } else {
            false
        }
    }
    else if (workerOrClintBack.value == workerOrClientList[1]) {
        if (workerOrClintBack.value.isNotEmpty() && craftIdBack.value.isNotEmpty()) {
            passwordIsNOtError.value && passwordIsNOtErrorConfirm.value
        } else {
            false
        }
    }
    else {
        false
    }
    val keyboardControllerRegister = LocalSoftwareKeyboardController.current
    DropList(
        expanded = workerOrClintExpanded,
        value = workerOrClintBack,
        list = workerOrClientList,
        label = "مستخدم ك",
        modifier = Modifier.height(110.dp)
    )

    //if select Worker
    //index 0 is client and index 1 is worker
    if (workerOrClintBack.value == workerOrClientList[0]) {
        craftIdBack.value = ""
    }
    if (workerOrClintBack.value == workerOrClientList[1]) {
        DropList(
            expanded = craftExpanded,
            value = craftName,
            craftList = craftList,
            label = "الوظيفة",
            craftId = craftIdBack
        )
    }
    //Register valid

    PasswordInput(
        password = passwordBack,
        eye = eye, onButtonAction = {
            eye.value = !eye.value
        },
        onAction = KeyboardActions {
            keyboardControllerRegister?.hide()
        },
        isNotError = passwordIsNOtError
    )
    PasswordInput(
        password = passwordConfirmBack,
        eye = eyeConfirm,
        onButtonAction = {
            eyeConfirm.value = !eyeConfirm.value
        },
        onAction = KeyboardActions {
            keyboardControllerRegister?.hide()
        },
        isNotError = passwordIsNOtErrorConfirm,
        label = "تأكيد كلمة السر",
        isConfirmed = true,
        passwordConfirm = passwordBack
    )
    Spacer(modifier = Modifier.height(10.dp))

    LoginButton(
        isLogin = true, enabled = valid, label = "سجل الدخول", onClick = onClick
    )

}