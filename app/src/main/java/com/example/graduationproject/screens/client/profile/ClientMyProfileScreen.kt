package com.example.graduationproject.screens.client.profile

import android.annotation.SuppressLint
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.graduationproject.R
import com.example.graduationproject.components.CircleProgress
import com.example.graduationproject.components.DefaultButton
import com.example.graduationproject.components.GetSmallPhoto
import com.example.graduationproject.components.ProfilePhoto
import com.example.graduationproject.components.SmallPhoto
import com.example.graduationproject.components.StarsNumber
import com.example.graduationproject.components.TopAppBar
import com.example.graduationproject.data.MyCraftOrderData
import com.example.graduationproject.data.WrapperClass
import com.example.graduationproject.model.shared.profile.GetProfile
import com.example.graduationproject.model.shared.profile.User
import com.example.graduationproject.model.shared.updateProflePhoto.UpdateProfilePhoto
import com.example.graduationproject.sharedpreference.SharedPreference
import com.example.graduationproject.ui.theme.MainColor
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File

@SuppressLint(
    "UnusedMaterialScaffoldPaddingParameter", "CoroutineCreationDuringComposition",
    "StateFlowValueCalledInComposition", "Recycle"
)
@Composable
fun ClientMyProfileScreen(
    navController: NavController,
    completeProject: Boolean = false,
    clientProfileViewModel: ClientProfileViewModel
) {
    //Shared preference variables
    val context = LocalContext.current
    val sharedPreference = SharedPreference(context)
    val token = sharedPreference.getToken.collectAsState(initial = "")
    val userId = sharedPreference.getUserId.collectAsState(initial = "")

    //coroutineScope
    val scope = rememberCoroutineScope()

    //state flow list
    val getProfileUser = MutableStateFlow<List<User>>(emptyList())

    var loading by remember {
        mutableStateOf(true)
    }
    var changeProfilePhoto by remember {
        mutableStateOf(false)
    }
    var exception by remember {
        mutableStateOf(false)
    }
    var uri by remember {
        mutableStateOf<Uri?>(null)
    }
    if (userId.value.toString().isNotEmpty() && token.value.toString().isNotEmpty()) {
        val getProfile: WrapperClass<GetProfile, Boolean, Exception> =
            produceState<WrapperClass<GetProfile, Boolean, Exception>>(
                initialValue = WrapperClass(data = null)
            ) {
                value = clientProfileViewModel.getProfile(
                    userId = userId.value.toString(),
                    authorization = "Bearer " + token.value.toString()
                )
            }.value
        if (getProfile.data?.status == "fail" || getProfile.data?.status == "error" || getProfile.e != null) {
            exception = true
            Toast.makeText(
                context,
                "خطأ في الانترنت",
                Toast.LENGTH_SHORT
            ).show()
        }
        else {
            if (getProfile.data != null) {
                scope.launch {
                    getProfileUser.emit(getProfile.data!!.data?.user!!)
                    loading = false
                    exception = false
                }
            }
        }
    }
    val isSelect = remember {
        mutableStateOf(false)
    }
    val changeCompleteState = remember {
        mutableStateOf(true)
    }

    Scaffold(topBar = {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 10.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            if (changeProfilePhoto) {
                TextButton(onClick = {
                    val imageName = uri?.toString()?.let { it1 -> File(it1) }
                    val fileUri: Uri =
                        uri!! // get the file URI from the file picker result
                    val inputStream = context.contentResolver.openInputStream(fileUri)
                    val file = inputStream?.readBytes()
                        ?.toRequestBody("multipart/form-data".toMediaTypeOrNull())
                    val filePart = file?.let {
                        MultipartBody.Part.createFormData(
                            "image", imageName!!.name, it
                        )
                    }
                    scope.launch {
                        loading = true
                        val response: WrapperClass<UpdateProfilePhoto, Boolean, Exception> =
                            clientProfileViewModel.updateProfilePhoto(
                                authorization = "Bearer " + token.value.toString(),
                                image = filePart!!,
                                userId = userId.value.toString()
                            )
                        when (response.data?.status) {
                            "success" -> {
                                val newResponse: WrapperClass<GetProfile, Boolean, Exception> =
                                    clientProfileViewModel.getProfile(
                                        userId = userId.value.toString(),
                                        authorization = "Bearer " + token.value.toString()
                                    )
                                if (newResponse.data?.status == null) {
                                    getProfileUser.emit(newResponse.data!!.data?.user!!)
                                    uri = null
                                    loading = false
                                    changeProfilePhoto = false
                                }
                            }
                            "error" -> {
                                uri = null
                                changeProfilePhoto = false
                                loading = false
                                Toast.makeText(
                                    context,
                                    "${response.data?.message}",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }

                            else -> {
                                uri = null
                                loading = false
                                changeProfilePhoto = false
                                Toast.makeText(
                                    context,
                                    "خطأ في الانترنت",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    }
                }) {
                    Text(
                        text = "تحديث", style = TextStyle(
                            color = MainColor,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 18.sp
                        )
                    )
                }
            } else {
                Row {

                }
            }
            TopAppBar(title = "", isProfile = true) {
                navController.popBackStack()
            }

        }
    }) {
        if (!loading && !exception) {
            if (completeProject && changeCompleteState.value) {
                isSelect.value = true
                changeCompleteState.value = false
            }
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 30.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 50.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    val launcher =
                        rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { URI ->
                            uri = URI
                        }
                    if (uri == null) {
                        GetSmallPhoto(
                            isProfile = true,
                            uri = if (getProfileUser.value[0].avatar != null) getProfileUser.value[0].avatar.toString() else null
                        )
                    } else {
                        ProfilePhoto(uri = uri)
                        changeProfilePhoto = true
                    }
                    IconButton(onClick = {
                        //nav to edit photo
                        launcher.launch("image/*")
                    }, modifier = Modifier.padding(top = 85.dp)) {
                        Icon(
                            painter = painterResource(id = R.drawable.camera),
                            contentDescription = null, tint = MainColor
                        )
                    }

                }
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = getProfileUser.value[0].name,
                    style = TextStyle(
                        color = MainColor,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                )
                Spacer(modifier = Modifier.height(5.dp))
                Text(
                    text = getProfileUser.value[0].address, style = TextStyle(
                        color = Color.Gray,
                        fontSize = 15.sp
                    )
                )

                Spacer(modifier = Modifier.height(15.dp))
                DefaultButton(label = "مشاريع مكتملة") {
                    isSelect.value = !isSelect.value
                }
                Spacer(modifier = Modifier.height(15.dp))
                if (isSelect.value) {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(400.dp)
                    ) {
                        items(completeist) {
                            MyCompleteProjectRow(item = it)
                            Spacer(modifier = Modifier.height(20.dp))
                        }
                        item {
                            //Spacer(modifier = Modifier.height(150.dp))
                        }
                    }
                }
            }
        }
        else if (loading && !exception) {
            CircleProgress()
        }
        else if (exception) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                IconButton(onClick = {
                    exception = false
                    loading = true
                    scope.launch {
                        val getProfile: WrapperClass<GetProfile, Boolean, Exception> =
                            clientProfileViewModel.getProfile(
                                userId = userId.value.toString(),
                                authorization = "Bearer " + token.value.toString()
                            )
                        if (getProfile.data?.status == "fail" || getProfile.data?.status == "error" || getProfile.e != null) {
                            exception = true
                            Toast.makeText(
                                context,
                                "خطأ في الانترنت",
                                Toast.LENGTH_SHORT
                            ).show()
                        } else {
                            if (getProfile.data != null) {
                                scope.launch {
                                    getProfileUser.emit(getProfile.data!!.data?.user!!)
                                    loading = false
                                    exception = false
                                }
                            }
                        }
                    }
                }) {
                    Icon(
                        imageVector = Icons.Default.Refresh, contentDescription = null,
                        modifier = Modifier.size(60.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun MyCompleteProjectRow(
    item: MyCraftOrderData
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .padding(start = 15.dp, end = 15.dp),
        elevation = 5.dp,
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 5.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            // photo url from data base workers photos
            SmallPhoto()
            Spacer(modifier = Modifier.width(5.dp))
            Column {
                Text(
                    text = item.workerName.toString(), style = TextStyle(
                        color = MainColor,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                )
                Text(
                    text = item.problemTitle + "- " + item.problemType, style = TextStyle(
                        color = MainColor,
                        fontSize = 12.sp,
                    )
                )
                StarsNumber(stars = item.workerRate!!)

            }
        }
    }
}

val completeist = listOf(
    MyCraftOrderData(
        workerName = "أحمد محمد",
        workerRate = 5,
        problemType = "صيانة بسيطة",
        problemTitle = "تصليح كرسي"
    ),
    MyCraftOrderData(
        workerName = "أحمد محمد",
        workerRate = 5,
        problemType = "صيانة بسيطة",
        problemTitle = "تصليح كرسي"
    ),
    MyCraftOrderData(
        workerName = "محمد أحمد",
        workerRate = 3,
        problemType = "صيانة بسيطة",
        problemTitle = "تصليح كرسي"
    ),
    MyCraftOrderData(
        workerName = "محمد محمد",
        workerRate = 2,
        problemType = "صيانة بسيطة",
        problemTitle = "تصليح كرسي"
    ),
    MyCraftOrderData(
        workerName = "محمود محمد",
        workerRate = 4,
        problemType = "صيانة بسيطة",
        problemTitle = "تصليح كرسي"
    ),
    MyCraftOrderData(
        workerName = "محمود أحمد",
        workerRate = 3,
        problemType = "صيانة بسيطة",
        problemTitle = "تصليح كرسي"
    ),
    MyCraftOrderData(
        workerName = "عباس احمد",
        workerRate = 3,
        problemType = "صيانة بسيطة",
        problemTitle = "تصليح كرسي"
    ),
)