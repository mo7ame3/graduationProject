package com.example.graduationproject.screens.client.postScreen

import android.annotation.SuppressLint
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.graduationproject.components.*
import com.example.graduationproject.data.WrapperClass
import com.example.graduationproject.model.client.creatOrder.CreateOrder
import com.example.graduationproject.navigation.AllScreens
import com.example.graduationproject.sharedpreference.SharedPreference
import com.example.graduationproject.ui.theme.MainColor
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File

@OptIn(ExperimentalComposeUiApi::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter", "Recycle")
@Composable
fun ClientPostScreen(
    navController: NavController,
    craftId: String,
    craftName: String,
    postViewModel: PostViewModel
) {

    BackPressHandler {
        navController.navigate(AllScreens.ClientHomeScreen.name + "/home") {
            navController.popBackStack()
            navController.popBackStack()
        }
    }

    Scaffold(topBar = {
        TopAppBar(title = "البحث عن $craftName") {
            navController.navigate(AllScreens.ClientHomeScreen.name + "/home") {
                navController.popBackStack()
                navController.popBackStack()
            }
        }
    }) {
        val context = LocalContext.current
        val sharedPreference = SharedPreference(context)
        val token = sharedPreference.getToken.collectAsState(initial = "")
        val scope = rememberCoroutineScope()
        val problemTitle = remember {
            mutableStateOf("")
        }
        val problemDescription = remember {
            mutableStateOf("")
        }
        val problemType = remember {
            mutableStateOf("")
        }
        val expanded = remember {
            mutableStateOf(false)
        }
        var selectedImage by remember {
            mutableStateOf<Uri?>(null)
        }
        var loading by remember {
            mutableStateOf(false)
        }
        val problemTypeList = listOf("صيانة بسيطة", "صيانة متوسطة", "صيانة معقدة")
        val keyboardController = LocalSoftwareKeyboardController.current
        // valid
        val valid = (problemTitle.value.trim().isNotBlank()
                && problemDescription.value.trim().isNotBlank()
                && problemType.value.trim().isNotBlank()
                && selectedImage.toString().isNotEmpty())


        if (!loading) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.verticalScroll(rememberScrollState())
            ) {
                Spacer(modifier = Modifier.height(5.dp))
                TextInput(isNotBackground = true,
                    text = problemTitle,
                    label = "عنوان المشروع",
                    onAction = KeyboardActions {
                        keyboardController?.hide()
                    })
                Spacer(modifier = Modifier.height(5.dp))
                DropList(
                    value = problemType,
                    expanded = expanded,
                    isNotBackground = true,
                    list = problemTypeList,
                    label = "نوع المشكلة",
                    modifier = Modifier.height(160.dp)
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = " تفاصيل المشكلة",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 30.dp),
                    color = MainColor
                )
                Spacer(modifier = Modifier.height(5.dp))
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(220.dp)
                        .padding(start = 25.dp, end = 25.dp),
                    shape = RoundedCornerShape(25.dp),
                    border = BorderStroke(width = 1.dp, color = MainColor)
                ) {
                    Column(modifier = Modifier.fillMaxSize()) {

//                    if (problemDescription.value.isEmpty()) {
//                        Text(
//                            text = "اكتب تفاصيل المشكلة هنا...",
//                            color = GrayColor,
//                            modifier = Modifier.padding(start = 10.dp, top = 10.dp)
//                        )
//                    }
                        TextInput(
                            text = problemDescription,
                            isBorder = false,
                            label = if (problemDescription.value.isEmpty()) "اكتب تفاصيل المشكلة هنا..." else "",
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(0.dp),
                            isSingleLine = false,
                            isNotBackground = true,
                        )
                    }
                }

                //Add photo
                Spacer(modifier = Modifier.height(5.dp))
                val launcher =
                    rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { uri ->
                        selectedImage = uri
                    }
                Text(
                    text = "اضف صور",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 30.dp),
                    color = MainColor
                )
                Spacer(modifier = Modifier.height(5.dp))

                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(220.dp)
                        .padding(start = 25.dp, end = 25.dp)
                        .clickable {
                            launcher.launch("image/*")
                        },
                    shape = RoundedCornerShape(25.dp),
                    border = BorderStroke(width = 1.dp, color = MainColor)
                ) {
                    //change photo
                    PickPhoto(selectedImage)
                }
                //Ellipse

                DefaultButton(label = "ارسل", enabled = valid) {
                    val imageName = selectedImage?.toString()?.let { it1 -> File(it1) }
                    val fileUri: Uri =
                        selectedImage!! // get the file URI from the file picker result
                    val inputStream = context.contentResolver.openInputStream(fileUri)
                    val file = inputStream?.readBytes()
                        ?.toRequestBody("multipart/form-data".toMediaTypeOrNull())
                    val filePart = file?.let {
                        MultipartBody.Part.createFormData(
                            "image", imageName!!.name, it
                        )
                    }
                    val problemTitlePart = problemTitle.value
                        .toRequestBody("multipart/form-data".toMediaTypeOrNull())
                    val problemTypePart = problemType.value
                        .toRequestBody("multipart/form-data".toMediaTypeOrNull())
                    val problemDescriptionPart = problemDescription.value
                        .toRequestBody("multipart/form-data".toMediaTypeOrNull())
                    scope.launch {
                        loading = true
                        if (token.value.toString().isNotEmpty()) {
                            val response: WrapperClass<CreateOrder, Boolean, Exception> =
                                postViewModel.createOrder(
                                    token = "Bearer " + token.value.toString(),
                                    craftId = craftId,
                                    image = filePart!!,
                                    description = problemDescriptionPart,
                                    title = problemTitlePart,
                                    orderDifficulty = problemTypePart
                                )
                            when (response.data?.status) {
                                "success" -> {
                                    navController.navigate(AllScreens.ClientHomeScreen.name + "/home") {
                                        navController.popBackStack()
                                        navController.popBackStack()
                                    }
                                }

                                "error" -> {
                                    loading = false
                                    Toast.makeText(
                                        context,
                                        "${response.data?.message}",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }

                                else -> {
                                    loading = false
                                    Toast.makeText(
                                        context,
                                        "خطأ في الانترنت",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                        }
                    }
                }
            }
        } else {
            CircleProgress()
        }
    }

}



