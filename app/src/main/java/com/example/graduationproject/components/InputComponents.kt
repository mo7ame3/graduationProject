@file:OptIn(ExperimentalMaterialApi::class)

package com.example.graduationproject.components

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.example.graduationproject.model.shared.craftList.Craft
import com.example.graduationproject.ui.theme.MainColor
import com.example.graduationproject.ui.theme.SecondaryColor


@Composable
fun EmailInput(
    modifier: Modifier = Modifier,
    email: MutableState<String>,
    keyboardType: KeyboardType = KeyboardType.Email,
//    imeAction: ImeAction = ImeAction.Next,
    onAction: KeyboardActions = KeyboardActions.Default,
    isNotError: MutableState<Boolean>,
    isSingleLine: Boolean = true,
    leadingImageVector: ImageVector? = null,
    isNotBackground: Boolean = false,
) {
    OutlinedTextField(
        modifier = modifier
            .fillMaxWidth()
            .padding(start = 25.dp, end = 25.dp),
        shape = RoundedCornerShape(25.dp),
        label = { Text(text = "البريد الالكتروني", style = TextStyle(color = MainColor)) },
        value = email.value,
        onValueChange = {
            email.value = it
            if (it.trim().isNotBlank()) {
                isNotError.value = true
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
            }
            else {
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
            if (it.length <= maxNumbers)
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
fun TextInput(
    modifier: Modifier = Modifier
        .fillMaxWidth()
        .padding(start = 25.dp, end = 25.dp),
    text: MutableState<String>,
    keyboardType: KeyboardType = KeyboardType.Text,
//  imeAction: ImeAction = ImeAction.Next,
    onAction: KeyboardActions = KeyboardActions.Default,
    isNotError: MutableState<Boolean>? = null,
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
                    isNotError?.value = true
                } else {
                    text.value = ""
                    isNotError?.value = false
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

@Composable
fun DropInput(
    modifier: Modifier,
    text: MutableState<String>,
    keyboardType: KeyboardType = KeyboardType.Text,
    expanded: MutableState<Boolean>,
    readOnly: Boolean = false,
    label: String,
    isNotBackground: Boolean = false,
    isBorder: Boolean = true,
    isSingleLine: Boolean = true,
    leadingImageVector: ImageVector? = null,
) {
    OutlinedTextField(
        readOnly = readOnly,
        modifier = modifier,
        shape = RoundedCornerShape(25.dp),
        label = { Text(text = label, style = TextStyle(color = MainColor)) },
        value = text.value,
        onValueChange = {},
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        keyboardActions = KeyboardActions {
//            expanded.value = !expanded.value
        },
        colors = TextFieldDefaults.outlinedTextFieldColors(
            backgroundColor = if (!isNotBackground) {
                SecondaryColor.copy(
                    alpha = 0.2f
                )
            } else Color.White,
            disabledBorderColor = Color.Transparent,
            focusedBorderColor = if (isBorder) {
                if (isNotBackground) Color.Transparent else MainColor
            } else Color.White,
            unfocusedBorderColor = if (isBorder) {
                if (isNotBackground) Color.Transparent else MainColor
            } else
                Color.White
        ),
        trailingIcon = {
            ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded.value)
        },
        singleLine = isSingleLine,
        leadingIcon = {
            if(leadingImageVector != null){
                Icon(imageVector = leadingImageVector, contentDescription = null)
            }
        }

        )
}

@OptIn(ExperimentalMaterialApi::class)
@SuppressLint("RememberReturnType")
@Composable
fun DropList(
    modifier: Modifier = Modifier,
    expanded: MutableState<Boolean>,
    value: MutableState<String>,
    leadingImageVector: ImageVector? = null,
    craftId: MutableState<String>? = null,
    label: String = "العنوان",
    list: List<String>? = null,
    craftList: List<Craft>? = null,
    isNotBackground: Boolean = false
) {

    Box(contentAlignment = Alignment.Center) {
        ExposedDropdownMenuBox(
            expanded = expanded.value,
            onExpandedChange = { expanded.value = !expanded.value }) {
            if(leadingImageVector != null) {
                DropInput(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 25.dp, end = 25.dp),
                    text = value,
                    expanded = expanded,
                    leadingImageVector = leadingImageVector,
                    readOnly = true,
                    label = label,
                    isNotBackground = isNotBackground
                )
            }else{
                DropInput(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 25.dp, end = 25.dp),
                    text = value,
                    expanded = expanded,
                    readOnly = true,
                    label = label,
                    isNotBackground = isNotBackground
                )
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 80.dp, start = 25.dp, end = 50.dp)
            ) {
                ExposedDropdownMenu(
                    expanded = expanded.value,
                    modifier = modifier,
                    onDismissRequest = {
                        expanded.value = false
                    },
                ) {
                    if (list != null && craftList == null) {
                        list.forEach { item ->
                            DropdownMenuItem(

                                onClick = {
                                    value.value = item
                                    expanded.value = false
                                },
                            ){
                                Text(text = item)
                            }
                            Divider(modifier = Modifier.fillMaxWidth())
                        }
                    } else if (craftList != null && list == null) {
                        craftList.forEach { item ->
                            DropdownMenuItem(

                                onClick = {
                                    value.value = item.name
                                    craftId?.value = item.id
                                    expanded.value = false
                                },
                            ){
                                Text(text = item.name)

                            }
                            Divider(modifier = Modifier.fillMaxWidth())
                        }
                    }
                }
            }
        }
    }
}