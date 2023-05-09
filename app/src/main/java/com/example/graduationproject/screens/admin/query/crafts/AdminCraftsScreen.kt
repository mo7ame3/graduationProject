package com.example.graduationproject.screens.admin.query.crafts

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.graduationproject.components.FloatingAction
import com.example.graduationproject.components.InternetCraftName
import com.example.graduationproject.components.InternetCraftPhoto
import com.example.graduationproject.components.TopAppBar
import com.example.graduationproject.data.WrapperClass
import com.example.graduationproject.model.getAllCrafts.Craft
import com.example.graduationproject.model.getAllCrafts.GetAllCrafts
import com.example.graduationproject.navigation.AllScreens
import com.example.graduationproject.sharedpreference.SharedPreference
import com.example.graduationproject.ui.theme.AdminSecondaryColor
import com.example.graduationproject.ui.theme.MainColor

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun AdminCraftsScreen(
    navController: NavHostController,
    craftsViewModel: CraftsViewModel
) {
    val context = LocalContext.current
    val sharedPreference = SharedPreference(context)
    val token = sharedPreference.getToken.collectAsState(initial = "")
    var craftList: List<Craft>? = null
    val loading = remember {
        mutableStateOf(true)
    }
    if (token.value.toString().isNotEmpty()) {
        val craftData: WrapperClass<GetAllCrafts, Boolean, Exception> =
            produceState<WrapperClass<GetAllCrafts, Boolean, Exception>>(
                initialValue = WrapperClass(data = null)
            ) {
                value = craftsViewModel.getAllCrafts(token = "Bearer ${token.value.toString()}")
            }.value
        if (craftData.data != null) {
            craftList = craftData.data!!.data?.crafts
            loading.value = false
        }
    }

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
        if (!loading.value) {
            if (craftList != null) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    items(items = craftList) {
                        JobRow(craft = it, onEditAction = { edit ->
                            navController.navigate(AllScreens.AdminEditJobsScreen.name + "/${edit}")
                        })
                        { craftQuery ->
                            navController.navigate(route = AllScreens.AdminAllWorkersInSpecificJob.name + "/${craftQuery.id}")
                        }
                    }
                }
            }
        } else {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                CircularProgressIndicator(color = MainColor)
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
