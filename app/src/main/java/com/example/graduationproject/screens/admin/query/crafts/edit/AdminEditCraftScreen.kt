package com.example.graduationproject.screens.admin.query.crafts.edit

import android.annotation.SuppressLint
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.graduationproject.R
import com.example.graduationproject.components.CircleProgress
import com.example.graduationproject.components.InternetCraftPhoto
import com.example.graduationproject.components.LoginButton
import com.example.graduationproject.components.TopAppBar
import com.example.graduationproject.constant.Constant
import com.example.graduationproject.data.WrapperClass
import com.example.graduationproject.model.admin.updateCraft.UpdateCraft
import com.example.graduationproject.model.shared.craft.Craft
import com.example.graduationproject.model.shared.getCraft.GetCraft
import com.example.graduationproject.navigation.AllScreens
import com.example.graduationproject.ui.theme.AdminMainColor
import com.example.graduationproject.ui.theme.AdminSecondaryColor
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun AdminEditCraftScreen(
    navController: NavHostController,
    craftId: String,
    adminEditCraftViewModel: AdminEditCraftViewModel
) {
    val loading = remember {
        mutableStateOf(true)
    }

    var craftData: Craft? = null
    if (Constant.token.isNotEmpty()) {
        val response: WrapperClass<GetCraft, Boolean, Exception> =
            produceState<WrapperClass<GetCraft, Boolean, Exception>>(
                initialValue = WrapperClass(data = null)
            ) {
                value = adminEditCraftViewModel.getOneCraft(
                    authorization = "Bearer " + Constant.token,
                    craftId = craftId
                )
            }.value

        if (response.data?.status == "success") {
            loading.value = false
            craftData = Craft(
                id = response.data?.data?.craft!!.id,
                _id = response.data?.data?.craft!!._id,
                name = response.data?.data?.craft!!.name,
                avatar = response.data?.data?.craft!!.avatar
            )
        }
    }
    Scaffold(topBar = {
        TopAppBar(title = "") {
            navController.popBackStack()
        }
    }) {
        if (!loading.value) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 15.dp, start = 10.dp, end = 10.dp)
            ) {
//            Row(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(start = 10.dp, end = 10.dp),
//                verticalAlignment = Alignment.CenterVertically,
//                horizontalArrangement = Arrangement.SpaceBetween
//            ) {
//                //Text(text = "عدد العمال في ${craftItem.craftTitle}" + "   " + craftItem.numberOfWorkers)
//                Row {
//                    //if (craftItem.numberOfWorkers == 0)
//                    IconButton(onClick = {
//
//                    }) {
//                        Icon(
//                            painter = painterResource(id = R.drawable.trash),
//                            contentDescription = null,
//                            modifier = Modifier.size(25.dp)
//                        )
//                    }
//
//                }
//            }
                Spacer(modifier = Modifier.height(10.dp))
                JobRow(craftData!!, navController, adminEditCraftViewModel)
            }
        } else {
            CircleProgress()
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@SuppressLint("Recycle")
@Composable
fun JobRow(
    craftFromGetAllCraft: Craft,
    navController: NavHostController,
    adminEditCraftViewModel: AdminEditCraftViewModel
) {
    val jobName = remember {
        mutableStateOf(craftFromGetAllCraft.name)
    }

    var selectedImage by remember {
        mutableStateOf<Uri?>(null)
    }
    val launcher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { uri ->
            selectedImage = uri
        }
    var loadingInJobRow by remember {
        mutableStateOf(false)
    }
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val keyboardController = LocalSoftwareKeyboardController.current

    if (!loadingInJobRow) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Box {
                Surface(
                    elevation = 10.dp,
                    shape = RoundedCornerShape(size = 30.dp),
                    modifier = Modifier.size(350.dp, 250.dp),
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Box {

                            InternetCraftPhoto(uri = (if (selectedImage == null) craftFromGetAllCraft.avatar else selectedImage).toString())
                            Row(
                                modifier = Modifier.size(300.dp, 200.dp),
                                horizontalArrangement = Arrangement.End,
                                verticalAlignment = Alignment.Top
                            ) {
                                IconButton(onClick = {
                                    // edit photo
                                    launcher.launch("image/*")
                                }) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.camera),
                                        contentDescription = null,
                                        tint = AdminSecondaryColor,
                                        modifier = Modifier.size(25.dp)
                                    )
                                }

                                IconButton(onClick = {
                                    // delete photo
                                    loadingInJobRow = true
                                    scope.launch {
                                        val deleteCraft = adminEditCraftViewModel.deleteCraft(
                                            authorization = "Bearer " + Constant.token,
                                            craftId = craftFromGetAllCraft.id,
                                        )
                                        when (deleteCraft.data?.status) {
                                            "success" -> {
                                                navController.navigate(route = AllScreens.AdminHomeScreen.name) {
                                                    navController.popBackStack()
                                                    navController.popBackStack()
                                                    navController.popBackStack()
                                                }
                                            }

                                            "fail" -> {
                                                loadingInJobRow = false
                                                Toast.makeText(
                                                    context,
                                                    deleteCraft.data!!.message,
                                                    Toast.LENGTH_SHORT
                                                ).show()
                                            }

                                            else -> {
                                                loadingInJobRow = false
                                                Toast.makeText(
                                                    context, "خطأ في الانترنت", Toast.LENGTH_SHORT
                                                ).show()
                                            }
                                        }

                                    }
                                }) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.trash),
                                        contentDescription = null,
                                        tint = AdminSecondaryColor,
                                        modifier = Modifier.size(25.dp)
                                    )
                                }

                            }
                        }
                        Surface(
                            color = Color.White,
                            modifier = Modifier
                                .height(50.dp)
                                .width(350.dp),
                            shape = RoundedCornerShape(bottomEnd = 30.dp, bottomStart = 30.dp),
                            elevation = 5.dp
                        ) {
                            TextField(
                                modifier = Modifier.fillMaxSize(),
                                singleLine = true,
                                maxLines = 1,
                                value = jobName.value,
                                onValueChange = { jobName.value = it },
                                textStyle = TextStyle(
                                    color = AdminMainColor,
                                    textAlign = TextAlign.Center
                                ),
                                colors = TextFieldDefaults.textFieldColors(
                                    backgroundColor = Color.White,
                                    focusedIndicatorColor = Color.White,
                                    unfocusedIndicatorColor = Color.White
                                ),
                            )
                        }
                    }
                }
            }
            LoginButton(isLogin = true, label = "ارسل") {
                keyboardController?.hide()
                val namePart = jobName.value
                    .toRequestBody("multipart/form-data".toMediaTypeOrNull())
                scope.launch {
                    loadingInJobRow = true
                    if (craftFromGetAllCraft.id.isNotEmpty()) {
                        if (selectedImage != null) {
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

                            if (jobName.value != craftFromGetAllCraft.name) {
                                val response: WrapperClass<UpdateCraft, Boolean, Exception> =
                                    adminEditCraftViewModel.updateCraft(
                                        authorization = "Bearer " + Constant.token,
                                        craftId = craftFromGetAllCraft.id,
                                        name = namePart,
                                        image = filePart
                                    )
                                if (response.data!!.status == "success") {
                                    navController.navigate(route = AllScreens.AdminHomeScreen.name) {
                                        navController.popBackStack()
                                        navController.popBackStack()
                                        navController.popBackStack()
                                    }
                                } else {
                                    loadingInJobRow = false
                                    selectedImage = null
                                    jobName.value = craftFromGetAllCraft.name
                                    Toast.makeText(
                                        context, response.data!!.message, Toast.LENGTH_SHORT
                                    ).show()
                                }
                            } else {
                                val response: WrapperClass<UpdateCraft, Boolean, Exception> =
                                    adminEditCraftViewModel.updateCraft(
                                        authorization = "Bearer " + Constant.token,
                                        craftId = craftFromGetAllCraft.id,
                                        image = filePart
                                    )
                                if (response.data!!.status == "success") {
                                    navController.navigate(route = AllScreens.AdminHomeScreen.name) {
                                        navController.popBackStack()
                                        navController.popBackStack()
                                        navController.popBackStack()
                                    }
                                } else {
                                    loadingInJobRow = false
                                    selectedImage = null
                                    Toast.makeText(
                                        context, response.data!!.message, Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                        } else {
                            if (jobName.value != craftFromGetAllCraft.name) {
                                val response: WrapperClass<UpdateCraft, Boolean, Exception> =
                                    adminEditCraftViewModel.updateCraft(
                                        authorization = "Bearer " + Constant.token,
                                        craftId = craftFromGetAllCraft.id,
                                        name = namePart,
                                    )
                                if (response.data!!.status == "success") {
                                    navController.navigate(route = AllScreens.AdminHomeScreen.name) {
                                        navController.popBackStack()
                                        navController.popBackStack()
                                        navController.popBackStack()
                                    }
                                } else {
                                    loadingInJobRow = false
                                    selectedImage = null
                                    Toast.makeText(
                                        context, response.data!!.message, Toast.LENGTH_SHORT
                                    ).show()
                                }
                            } else {
                                loadingInJobRow = false
                                selectedImage = null
                                jobName.value = craftFromGetAllCraft.name
                                Toast.makeText(
                                    context, "No Change", Toast.LENGTH_SHORT
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


// Update and nav to Home
//            loading.value = true
//            val namePart = jobName.value
//                .toRequestBody("multipart/form-data".toMediaTypeOrNull())
//            scope.launch {
//
//                if (selectedImage != null) {
//                    val imageName = selectedImage?.toString()?.let { it1 -> File(it1) }
//                    val fileUri: Uri =
//                        selectedImage!! // get the file URI from the file picker result
//                    val inputStream = context.contentResolver.openInputStream(fileUri)
//                    val file = inputStream?.readBytes()
//                        ?.toRequestBody("multipart/form-data".toMediaTypeOrNull())
//                    val filePart = file?.let {
//                        MultipartBody.Part.createFormData(
//                            "image", imageName!!.name, it
//                        )
//                    }
//
//
//                    if (jobName.value != craft.name) {
//
//                        val response :WrapperClass<CreateNewCraft, Boolean , Exception>
//                                = adminEditCraftViewModel.creatCraft(
//                            name= namePart,
//                            authorization = Constant.token,
//                            image = filePart!!
//                        )
//                        Log.d("TAG", "JobRow: ${response.data} ")
//                        if(response.data?.status =="success"){
//                            navController.navigate(route = AllScreens.AdminHomeScreen.name) {
//                                navController.popBackStack()
//                                navController.popBackStack()
//                                navController.popBackStack()
//                            }
//                        }else{
//                            loading.value = false
//                            Toast.makeText(
//                                context, response.data!!.message, Toast.LENGTH_SHORT
//                            ).show()
//                        }
//
//                        if (craft.id.isNotEmpty()) {
//                            adminEditCraftViewModel.updateCraft(
//                                name = namePart,
//                                authorization = "Bearer" + Constant.token,
//                                image = filePart!!,
//                                craftId = craft.id
//                            )
//                        }
//
//
//                    } else {
//                        Log.d("TAG", "JobRow photo only")
//                    }
//                } else {
//                    if (jobName.value != craft.name) {
//
//                    } else {
//                        loading.value = false
//                       Toast.makeText(
//                            context, "No Change", Toast.LENGTH_SHORT
//                       ).show()
//                    }
//                }
//
//            }
//            }


