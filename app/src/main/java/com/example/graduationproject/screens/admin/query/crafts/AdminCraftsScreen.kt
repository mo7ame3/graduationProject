package com.example.graduationproject.screens.admin.query.crafts

import android.annotation.SuppressLint
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.graduationproject.components.CircleProgress
import com.example.graduationproject.components.FloatingAction
import com.example.graduationproject.components.InternetCraftName
import com.example.graduationproject.components.InternetCraftPhoto
import com.example.graduationproject.components.TopAppBar
import com.example.graduationproject.data.WrapperClass
import com.example.graduationproject.model.shared.getAllCrafts.Craft
import com.example.graduationproject.model.shared.getAllCrafts.GetAllCrafts
import com.example.graduationproject.navigation.AllScreens
import com.example.graduationproject.sharedpreference.SharedPreference
import com.example.graduationproject.ui.theme.AdminSecondaryColor
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

@SuppressLint(
    "UnusedMaterialScaffoldPaddingParameter", "CoroutineCreationDuringComposition",
    "StateFlowValueCalledInComposition")
@Composable
fun AdminCraftsScreen(
    navController: NavHostController,
    craftsViewModel: CraftsViewModel
) {
    //Shared preference variables
    val context = LocalContext.current
    val sharedPreference = SharedPreference(context)
    val token = sharedPreference.getToken.collectAsState(initial = "")

    //coroutineScope
    val scope = rememberCoroutineScope()

    //check home loading
    var loading by remember {
        mutableStateOf(true)
    }
    var exception by remember {
        mutableStateOf(false)
    }

    //state flow list
    val craftList = MutableStateFlow<List<Craft>>(emptyList())

    //response
    if (token.value.toString().isNotEmpty()) {
        val craftData: WrapperClass<GetAllCrafts, Boolean, Exception> =
            produceState<WrapperClass<GetAllCrafts, Boolean, Exception>>(
                initialValue = WrapperClass(data = null)
            ) {
                value = craftsViewModel.getAllCrafts(token = "Bearer ${token.value.toString()}")
            }.value
        if (craftData.data?.status == "success") {
            if (craftData.data != null) {
                scope.launch {
                    craftList.emit(craftData.data!!.data?.crafts!!)
                    loading = false
                    exception = false
                }
            }
        } else if (craftData.data?.status == "fail" || craftData.e != null) {
            exception = true
            Toast.makeText(
                context,
                "خطأ في الانترنت",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    // Swipe Refresh
    var swipeLoading by remember {
        mutableStateOf(false)
    }

    val swipeRefreshState = rememberSwipeRefreshState(isRefreshing = swipeLoading)


    SwipeRefresh(state = swipeRefreshState,
        onRefresh = {
            swipeLoading = true
            loading = false
            scope.launch {
                val craftData: WrapperClass<GetAllCrafts, Boolean, Exception> =
                    craftsViewModel.getAllCrafts(token = "Bearer ${token.value.toString()}")
                if (craftData.data?.status == "success") {
                    if (craftData.data != null) {
                        craftList.emit(craftData.data!!.data?.crafts!!)
                        swipeLoading = false
                    }
                } else {
                    swipeLoading = false
                }
            }
        }) {
        Scaffold(topBar = {
            TopAppBar(title = "") {
                navController.popBackStack()
            }
        },
            floatingActionButton = {
                FloatingAction {
                    navController.navigate(AllScreens.AdminCreateNewCraft.name)
                }
            }
        ) {
            if (!loading && !exception) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    items(items = craftList.value) {
                        JobRow(craft = it, onEditAction = { edit ->
                            navController.navigate(AllScreens.AdminEditJobsScreen.name + "/${edit}")
                        })
                        { craftQuery ->
                            navController.navigate(route = AllScreens.AdminAllWorkersInSpecificJob.name + "/${craftQuery.id}")
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
                            val craftData: WrapperClass<GetAllCrafts, Boolean, Exception> =
                                craftsViewModel.getAllCrafts(token = "Bearer ${token.value.toString()}")
                            if (craftData.data?.status == "success") {
                                if (craftData.data != null) {
                                    scope.launch {
                                        craftList.emit(craftData.data!!.data?.crafts!!)
                                        loading = false
                                        exception = false
                                    }
                                }
                            } else if (craftData.data?.status == "fail" || craftData.e != null) {
                                exception = true
                                Toast.makeText(
                                    context,
                                    "خطأ في الانترنت",
                                    Toast.LENGTH_SHORT
                                ).show()
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
}

@Composable
fun JobRow(
    craft: Craft,
    onEditAction: (String) -> Unit,
    onClick: (Craft) -> Unit
) {
    Column(modifier = Modifier
        .clickable {
            onClick.invoke(craft)
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
                        InternetCraftPhoto(craft.avatar)
                        Row(
                            modifier = Modifier.size(300.dp, 200.dp),
                            horizontalArrangement = Arrangement.End,
                            verticalAlignment = Alignment.Top
                        ) {
                            IconButton(onClick = {
                                // nav to edit
                                onEditAction.invoke(craft.id)
                            }) {
                                Icon(
                                    imageVector = Icons.Default.Edit, contentDescription = null,
                                    tint = AdminSecondaryColor
                                )
                            }
                        }
                    }
                    InternetCraftName(jobName = craft.name)
                }
            }
        }
    }
}
