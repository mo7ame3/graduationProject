package com.example.graduationproject.components

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.core.text.isDigitsOnly
import com.example.graduationproject.ui.theme.MainColor
import com.example.graduationproject.ui.theme.SecondaryColor


@Composable
fun PhoneNumber(
    modifier: Modifier = Modifier,
    phoneNumber: MutableState<String>,
    keyboardType: KeyboardType = KeyboardType.Number,
//    imeAction: ImeAction = ImeAction.Next,
    onAction: KeyboardActions = KeyboardActions.Default,
    isNotError: MutableState<Boolean>,
    isSingleLine: Boolean = true,
    leadingImageVector: ImageVector? = null,
    isNotBackground: Boolean = false,
) {
    val maxNumbers = 11
    OutlinedTextField(
        modifier = modifier
            .fillMaxWidth()
            .padding(start = 25.dp, end = 25.dp),
        shape = RoundedCornerShape(25.dp),
        label = { Text(text = "رقم الهاتف", style = TextStyle(color = MainColor)) },
        value = phoneNumber.value,
        onValueChange = {
            if (it.length >= 2) {
                if (it.toCharArray()[0] == '0' && it.toCharArray()[1] == '1') {
                    if (it.isDigitsOnly()) if (it.length <= maxNumbers) {
                        phoneNumber.value = it
                        isNotError.value = it.length == maxNumbers
                    }
                }
            }
        },
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        keyboardActions = onAction,
        colors = TextFieldDefaults.outlinedTextFieldColors(
            backgroundColor = if (!isNotBackground) {
                SecondaryColor.copy(
                    alpha = 0.2f
                )
            } else Color.White,
        ),
        singleLine = isSingleLine,
        leadingIcon = {
            if (leadingImageVector != null) {
                Icon(imageVector = leadingImageVector, contentDescription = null, tint = MainColor)
            }
        }
    )
}

@SuppressLint("SuspiciousIndentation")
@Composable
fun SMSNumber(
    modifier: Modifier = Modifier,
    smsNumber: MutableState<String>,
    keyboardType: KeyboardType = KeyboardType.Number,
//    imeAction: ImeAction = ImeAction.Next,
    onAction: KeyboardActions = KeyboardActions.Default,
    isSingleLine: Boolean = true,
) {
    val maxNumbers = 6
    OutlinedTextField(
        modifier = modifier
            .fillMaxWidth(.4f)
            .height(55.dp)
            .padding(start = 25.dp, end = 25.dp),
        shape = RoundedCornerShape(25.dp),
        label = { Text(text = "ادخل كود التأكيد", style = TextStyle(color = MainColor)) },
        value = smsNumber.value,
        onValueChange = {
            if(it.length <= maxNumbers)
            smsNumber.value = it
        },
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        keyboardActions = onAction,
        colors = TextFieldDefaults.outlinedTextFieldColors(
            backgroundColor = Color.White,
        ),
        singleLine = isSingleLine,
    )
}

@Composable
fun PasswordInput(
    modifier: Modifier = Modifier,
    password: MutableState<String>,
    keyboardType: KeyboardType = KeyboardType.Password,
    isSingleLine: Boolean = true,
    isConfirmed: Boolean = false,
    passwordConfirm: MutableState<String> = mutableStateOf(""),
//    imeAction: ImeAction = ImeAction.Next,
    eye: MutableState<Boolean>,
    onButtonAction: () -> Unit,
    onAction: KeyboardActions = KeyboardActions.Default,
    isNotError: MutableState<Boolean>,
    label: String = "كلمة السر",
    leadingImageVector: ImageVector? = null,
    isNotBackground: Boolean = false,
) {

    val visualTransformation = if (eye.value) VisualTransformation.None
    else PasswordVisualTransformation()
    OutlinedTextField(singleLine = isSingleLine,
        modifier = modifier
            .fillMaxWidth()
            .padding(start = 25.dp, end = 25.dp),
        shape = RoundedCornerShape(25.dp),
        label = { Text(text = label, style = TextStyle(color = MainColor)) },
        value = password.value,
        onValueChange = {
            if (it.isNotBlank()) {
                if (it.length > 11) {
                    if (isConfirmed) {
                        password.value = it
                        isNotError.value = password.value == passwordConfirm.value
                    } else {
                        password.value = it
                        isNotError.value = true
                    }
                } else {
                    password.value = it
                    isNotError.value = false
                }
            } else {
                password.value = ""
                isNotError.value = false
            }
        },
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        keyboardActions = onAction,
        colors = TextFieldDefaults.outlinedTextFieldColors(
            backgroundColor = if (!isNotBackground) {
                SecondaryColor.copy(
                    alpha = 0.2f
                )
            } else Color.White,
            disabledBorderColor = MainColor,
            focusedBorderColor = MainColor,
            unfocusedBorderColor = MainColor
        ),
        visualTransformation = visualTransformation,
        trailingIcon = {
            IconButton(onClick = { onButtonAction.invoke() }) {
                if (eye.value) Icon(
                    imageVector = Icons.Default.VisibilityOff, contentDescription = null
                )
                else Icon(imageVector = Icons.Default.Visibility, contentDescription = null)
            }
        }, leadingIcon = {
            if (leadingImageVector != null) {
                Icon(imageVector = leadingImageVector, contentDescription = null, tint = MainColor)
            }
        })
}

@Composable
fun TextInput(
    modifier: Modifier = Modifier
        .fillMaxWidth()
        .padding(start = 25.dp, end = 25.dp),
    text: MutableState<String>,
    keyboardType: KeyboardType = KeyboardType.Text,
//  imeAction: ImeAction = ImeAction.Next,
    onAction: KeyboardActions = KeyboardActions.Default,
    IsNotError: MutableState<Boolean>? = null,
    expanded: MutableState<Boolean>? = null,
    readOnly: Boolean = false,
    label: String = "الاسم",
    isNotBackground: Boolean = false,
    isBorder: Boolean = true,
    isSingleLine: Boolean = true,
    leadingImageVector: ImageVector? = null
) {
    OutlinedTextField(
        readOnly = readOnly,
        modifier = modifier,
        shape = RoundedCornerShape(25.dp),
        label = { Text(text = label, style = TextStyle(color = MainColor)) },
        value = text.value,
        onValueChange = {
            if (!readOnly) {
                if (it.trim().isNotBlank()) {
                    text.value = it
                    IsNotError?.value = true
                } else {
                    text.value = ""
                    IsNotError?.value = false
                }
            }
        },
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        keyboardActions = onAction,
        colors = TextFieldDefaults.outlinedTextFieldColors(
            backgroundColor = if (!isNotBackground) {
                SecondaryColor.copy(
                    alpha = 0.2f
                )
            } else Color.White,
            disabledBorderColor = Color.Transparent,
            focusedBorderColor = if (isBorder) {
                if (!isNotBackground) Color.Transparent else MainColor
            } else Color.White,
            unfocusedBorderColor = if (isBorder) {
                if (!isNotBackground) Color.Transparent else MainColor
            } else
                Color.White
        ),
        trailingIcon = {
            if (readOnly) {
                IconButton(onClick = {}) {
                    Icon(
                        imageVector = if (!expanded!!.value) Icons.Default.ArrowDropDown
                        else Icons.Default.ArrowDropUp,
                        contentDescription = null
                    )
                }
            }
        },
        singleLine = isSingleLine,
        leadingIcon = {
            if (leadingImageVector != null) {
                Icon(imageVector = leadingImageVector, contentDescription = null, tint = MainColor)
            }
        }
    )
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun DropList(
    modifier: Modifier = Modifier.height(250.dp),
    expanded: MutableState<Boolean>,
    value: MutableState<String>,
    label: String = "العنوان",
    list: List<String>,
    isNotBackground: Boolean = false,
    leadingImageVector: ImageVector? = null
) {

    ExposedDropdownMenuBox(
        modifier = Modifier
            .fillMaxWidth()
            //.padding(start = 50.dp, end = 50.dp)
            .clip(shape = RoundedCornerShape(25.dp)),
        expanded = expanded.value,
        onExpandedChange = { expanded.value = !expanded.value }) {
        TextInput(
            text = value,
            readOnly = true,
            label = label,
            expanded = expanded,
            isNotBackground = isNotBackground,
            leadingImageVector = leadingImageVector
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 65.dp, start = 50.dp, end = 50.dp)
        ) {
            ExposedDropdownMenu(
                expanded = expanded.value,
                modifier = modifier,
                onDismissRequest = { expanded.value = false }) {
                list.forEach { t ->
                    DropdownMenuItem(
                        onClick = {
                            value.value = t
                            expanded.value = false
                        },
                    ) {
                        Text(text = t)
                    }
                    Divider(modifier = Modifier.fillMaxWidth())
                }
            }
        }
    }
}
