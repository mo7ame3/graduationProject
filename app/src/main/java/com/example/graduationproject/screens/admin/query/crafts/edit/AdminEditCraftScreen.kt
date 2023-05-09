package com.example.graduationproject.screens.admin.query.crafts.edit

import android.annotation.SuppressLint
import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Error
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.SubcomposeAsyncImage
import com.example.graduationproject.R
import com.example.graduationproject.components.LoginButton
import com.example.graduationproject.components.TopAppBar
import com.example.graduationproject.constant.Constant.adminCraftList
import com.example.graduationproject.data.GoogleDriveList
import com.example.graduationproject.data.WrapperClass
import com.example.graduationproject.model.getCraft.GetCraft
import com.example.graduationproject.navigation.AllScreens
import com.example.graduationproject.sharedpreference.SharedPreference
import com.example.graduationproject.ui.theme.AdminMainColor
import com.example.graduationproject.ui.theme.AdminSecondaryColor
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun AdminEditCraftScreen(
    navController: NavHostController,
    craftId: String,
    adminEditCraftViewModel: AdminEditCraftViewModel
) {

    val context = LocalContext.current
    val sharedPreference = SharedPreference(context)
    val token = sharedPreference.getToken.collectAsState(initial = "")
    val scope = rememberCoroutineScope()

    val craftItem = adminCraftList[4]
    Scaffold(topBar = {
        TopAppBar(title = "") {
            navController.popBackStack()
        }
    }) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 15.dp, start = 10.dp, end = 10.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 10.dp, end = 10.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                //Text(text = "عدد العمال في ${craftItem.craftTitle}" + "   " + craftItem.numberOfWorkers)
                Row {
                    if (craftItem.numberOfWorkers == 0)
                        IconButton(onClick = {
                            scope.launch {
                                // Delete craft from data base
                                Log.d("TAG", "AdminEditCraftScreen")
                                Log.d("TAG", "AdminEditCraftScreen ${token.value.toString()}")
                                if (token.value.toString().isNotEmpty() ){
                                    val response:WrapperClass<GetCraft,Boolean,Exception> =  adminEditCraftViewModel.getOneCraft(
                                        authorization = token.value.toString(),
                                        craftId = craftId
                                    )
                                    Log.d("TAG", "AdminEditCraftScreen: ${response.data?.data}")
                                    Log.d("TAG", "AdminEditCraftScreen: ${response.data?.status}")
                                }
                            }

                        }) {
                            Icon(
                                painter = painterResource(id = R.drawable.trash),
                                contentDescription = null,
                                modifier = Modifier.size(25.dp)
                            )
                        }

                }
            }
            Spacer(modifier = Modifier.height(10.dp))
            JobRow(craftItem, navController)
        }

    }
}

@Composable
fun JobRow(
    googleDriveList: GoogleDriveList,
    navController: NavHostController
) {
    val jobName = remember {
        mutableStateOf(googleDriveList.jobName)
    }

    var selectedImage by remember {
        mutableStateOf<Uri?>(null)
    }
    val launcher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { uri ->
            selectedImage = uri
        }

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
                        SubcomposeAsyncImage(
                            model = if (selectedImage == null) googleDriveList.picGoogle else selectedImage,
                            contentDescription = null,
                            modifier = Modifier.size(300.dp, 200.dp),
                            loading = {
                                Box(
                                    modifier = Modifier.fillMaxSize(),
                                    contentAlignment = Alignment.Center
                                ) {
                                    CircularProgressIndicator()
                                }
                            },
                            error = {
                                Box(
                                    modifier = Modifier.fillMaxSize(),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Error,
                                        contentDescription = null,
                                        modifier = Modifier.size(60.dp)
                                    )
                                }

                            },
                            contentScale = ContentScale.Crop
                        )
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
                            )
                        )
                    }
                }
            }
        }
        LoginButton(isLogin = true, label = "save") {
            // Update and nav to Home
            navController.navigate(route = AllScreens.AdminHomeScreen.name) {
                navController.popBackStack()
                navController.popBackStack()
                navController.popBackStack()
            }
        }
    }
}