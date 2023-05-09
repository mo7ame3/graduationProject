package com.example.graduationproject.screens.admin.query.crafts.createCraft

import android.annotation.SuppressLint
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.graduationproject.components.CircleProgress
import com.example.graduationproject.components.DefaultButton
import com.example.graduationproject.components.PickPhoto
import com.example.graduationproject.components.TextInput
import com.example.graduationproject.components.TopAppBar
import com.example.graduationproject.data.WrapperClass
import com.example.graduationproject.model.admin.createCraft.CreateNewCraft
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
fun AdminCreateNewCraft(
    navController: NavController, createNewCraftViewModel: CreateNewCraftViewModel
) {
    Scaffold(topBar = {
        TopAppBar(title = "") {
            navController.popBackStack()
        }
    }) {
        val jobTitle = remember {
            mutableStateOf("")
        }
        var loading = false
        var selectedImage by remember {
            mutableStateOf<Uri?>(null)
        }
        val valid = (jobTitle.value.trim().isNotBlank() && selectedImage != null)
        val context = LocalContext.current
        val sharedPreference = SharedPreference(context)
        val token = sharedPreference.getToken.collectAsState(initial = "")
        val scope = rememberCoroutineScope()
        val keyboardController = LocalSoftwareKeyboardController.current
        if (!loading) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.verticalScroll(rememberScrollState())
            ) {
                Spacer(modifier = Modifier.height(5.dp))
                TextInput(isNotBackground = true,
                    text = jobTitle,
                    label = "اسم المهنة",
                    onAction = KeyboardActions {
                        keyboardController?.hide()
                    })
                Spacer(modifier = Modifier.height(5.dp))

                //Add photo
                Spacer(modifier = Modifier.height(5.dp))

                val launcher =
                    rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { uri ->
                        selectedImage = uri
                    }
                Text(
                    text = "اضف صوره",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 30.dp),
                    color = MainColor
                )
                Spacer(modifier = Modifier.height(5.dp))

                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp)
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
                Spacer(modifier = Modifier.height(10.dp))
                DefaultButton(label = "ارسل", enabled = valid) {
                    loading = true
                    scope.launch {
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
                        val namePart = jobTitle.value
                            .toRequestBody("multipart/form-data".toMediaTypeOrNull())


                        val addCraft: WrapperClass<CreateNewCraft, Boolean, Exception> =
                            createNewCraftViewModel.createNewCraft(
                                name = namePart,
                                image = filePart!!,
                                token = "Bearer ${token.value.toString()}"
                            )
                        if (addCraft.data?.status == "success") {
                            navController.navigate(AllScreens.AdminHomeScreen.name) {
                                navController.popBackStack()
                                navController.popBackStack()
                                navController.popBackStack()
                            }
                        } else {
                            loading = false
                            Toast.makeText(
                                context, addCraft.data?.message, Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            }
        } else {
            CircleProgress()
        }
    }
}

