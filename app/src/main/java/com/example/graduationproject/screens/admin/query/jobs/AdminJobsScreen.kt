package com.example.graduationproject.screens.admin.query.jobs

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.graduationproject.components.TopAppBar
import com.example.graduationproject.constant.Constant.adminCraftList
import com.example.graduationproject.data.GoogleDriveList
import com.example.graduationproject.navigation.AllScreens
import com.example.graduationproject.ui.theme.AdminSecondaryColor

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun AdminJobsScreen(navController: NavHostController) {
    Scaffold(topBar = {
        TopAppBar(title = "") {
            navController.popBackStack()
        }
    }) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
        ) {
            items(items = adminCraftList) {
                JobRow(googleDriveList = it, onEditAction = { edit ->
                    navController.navigate(AllScreens.AdminEditJobsScreen.name + "/${edit}")
                })
                { craftQuery ->
                    navController.navigate(route = AllScreens.AdminAllWorkersInSpecificJob.name + "/${craftQuery.id}")
                }
            }
        }
    }
}

@Composable
fun JobRow(
    googleDriveList: GoogleDriveList,
    onEditAction: (Int) -> Unit,
    onClick: (GoogleDriveList) -> Unit
) {
    Column(modifier = Modifier
        .clickable {
            onClick.invoke(googleDriveList)
        }
        .fillMaxSize()
        .padding(10.dp), horizontalAlignment = Alignment.CenterHorizontally) {
        Box {
            Surface(
                elevation = 10.dp,
                shape = RoundedCornerShape(size = 30.dp),
                modifier = Modifier.size(350.dp, 230.dp),
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Box {

                        // to reload image
                        var refreshImage by remember { mutableStateOf(0) }
                        val painter = rememberAsyncImagePainter(
                            model = ImageRequest.Builder(LocalContext.current)
                                .data(googleDriveList.picGoogle)
                                .setParameter("refresh", refreshImage, memoryCacheKey = null)
                                .build()
                        )
                        Image(
                            painter = painter,
                            contentDescription = null,
                            modifier = Modifier.size(300.dp, 200.dp),
                        )
                        // condition to reload image
                        when (painter.state) {
                            is AsyncImagePainter.State.Error -> {
                                Box(
                                    modifier = Modifier.fillMaxSize(),
                                    contentAlignment = Alignment.Center
                                ) {
                                    IconButton(onClick = { refreshImage++ }) {
                                        Icon(
                                            imageVector = Icons.Default.Refresh,
                                            contentDescription = null,
                                            modifier = Modifier.size(60.dp)
                                        )
                                    }
                                }
                            }
                            is AsyncImagePainter.State.Loading -> {
                                Box(
                                    modifier = Modifier.fillMaxSize(),
                                    contentAlignment = Alignment.Center
                                ) {
                                    CircularProgressIndicator()
                                }
                            }
                            else -> {}
                        }

//                        SubcomposeAsyncImage(
//                            model = googleDriveList.picGoogle,
//                            contentDescription = null,
//                            modifier = Modifier.size(300.dp, 200.dp),
//                            loading = {
//                                Box(
//                                    modifier = Modifier.fillMaxSize(),
//                                    contentAlignment = Alignment.Center
//                                ) {
//                                    CircularProgressIndicator()
//                                }
//                            },
//                            error = {
//                                Box(
//                                    modifier = Modifier.fillMaxSize(),
//                                    contentAlignment = Alignment.Center
//                                ) {
//                                    Icon(
//                                        imageVector = Icons.Default.Error,
//                                        contentDescription = null,
//                                        modifier = Modifier.size(60.dp)
//                                    )
//                                }
//                            }
//                        )
                        Row(
                            modifier = Modifier.size(300.dp, 200.dp),
                            horizontalArrangement = Arrangement.End,
                            verticalAlignment = Alignment.Top
                        ) {
                            IconButton(onClick = {
                                // nav to edit
                                onEditAction.invoke(googleDriveList.id)
                            }) {
                                Icon(
                                    imageVector = Icons.Default.Edit, contentDescription = null,
                                    tint = AdminSecondaryColor
                                )
                            }
                        }
                    }
                    Surface(
                        color = Color.White,
                        modifier = Modifier
                            .height(90.dp)
                            .width(350.dp),
                        shape = RoundedCornerShape(bottomEnd = 30.dp, bottomStart = 30.dp),
                        elevation = 5.dp
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Text(text = googleDriveList.jobName)
                        }
                    }
                }
            }
        }
    }
}
