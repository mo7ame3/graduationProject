package com.example.graduationproject.components

import android.net.Uri
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.graduationproject.R
import com.example.graduationproject.constant.Constant
import com.example.graduationproject.data.DrawerData
import com.example.graduationproject.ui.theme.GoldColor
import com.example.graduationproject.ui.theme.MainColor
import com.example.graduationproject.ui.theme.SecondaryColor

@Composable
fun BottomBar(
    selected: MutableState<String>,
    title: MutableState<String> = mutableStateOf(""),
    home: () -> Unit = {},
    order: () -> Unit = {},
    chat: () -> Unit = {},
    isClient: Boolean = true

) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(55.dp),
        shape = RoundedCornerShape(topEnd = 20.dp, topStart = 20.dp), color = MainColor
    ) {
        BottomNavigation {
            BottomNavigationItem(selected = selected.value == "chat", onClick = {
                selected.value = "chat"
                title.value = "المحادثات"
                chat.invoke()
            }, icon = {
                Icon(
                    painter = painterResource(id = R.drawable.chat),
                    contentDescription = null, modifier = Modifier.size(35.dp)
                )
            }, selectedContentColor = Color.White, unselectedContentColor = SecondaryColor)

            BottomNavigationItem(selected = selected.value == "home", onClick = {
                selected.value = "home"
                title.value = Constant.title
                home.invoke()
            }, icon = {
                Icon(
                    painter = painterResource(id = R.drawable.home_icon_white),
                    contentDescription = null, modifier = Modifier.size(30.dp)
                )
            }, selectedContentColor = Color.White, unselectedContentColor = SecondaryColor)

            BottomNavigationItem(selected = selected.value == "order", onClick = {
                selected.value = "order"
                title.value = if (isClient) "طلباتي" else "مشاريعي"
                order.invoke()
            }, icon = {
                Icon(
                    painter = painterResource(id = R.drawable.my_orders),
                    contentDescription = null, modifier = Modifier.size(25.dp)
                )
            }, selectedContentColor = Color.White, unselectedContentColor = SecondaryColor)
        }
    }
}

@Composable
fun TopMainBar(
    title: MutableState<String>,
    onClick: () -> Unit
) {
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(55.dp)
                .padding(start = 15.dp, end = 25.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            IconButton(onClick = onClick) {
                Icon(
                    imageVector = Icons.Default.Menu, contentDescription = null,
                    modifier = Modifier.size(30.dp), tint = MainColor
                )
            }
            Text(
                text = title.value,
                style = TextStyle(
                    fontSize = 22.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = MainColor
                )
            )
            Icon(
                painter = painterResource(id = Constant.logo), contentDescription = null,
                modifier = Modifier.size(30.dp), tint = MainColor
            )

        }
        Spacer(modifier = Modifier.height(10.dp))
    }
}

@Composable
fun DrawerHeader() {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(170.dp),
        shape = RoundedCornerShape(bottomEnd = 300.dp, bottomStart = 160.dp),
        color = MainColor
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                painter = painterResource(id = Constant.logo), contentDescription = null,
                modifier = Modifier.size(50.dp),
                tint = Color.White
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = Constant.title,
                style = TextStyle(
                    color = Color.White,
                    fontSize = 16.sp
                )
            )
        }
    }
}

@Composable
fun DrawerBody(
    isClient: Boolean,
    name:String,
    onClick: (DrawerData) -> Unit
) {
    val drawerListClient = if (isClient) listOf(
        DrawerData(title = name, pic = R.drawable.person),
        DrawerData(title = "إعدادات حسابي", pic = R.drawable.settings),
        DrawerData(title = "طلباتي", pic = R.drawable.my_orders),
        DrawerData(title = "مكتملة", pic = R.drawable.completed_orders),
        DrawerData(title = "تسجيل الخروج", pic = R.drawable.logout),
    )
    else listOf(
        DrawerData(title = name, pic = R.drawable.person),
        DrawerData(title = "إعدادات حسابي", pic = R.drawable.settings),
        DrawerData(title = "مشاريعي", pic = R.drawable.my_orders),
        DrawerData(title = "تسجيل الخروج", pic = R.drawable.logout),
    )

    LazyColumn {
        items(drawerListClient) {
            DrawerRow(DrawerData = it) { select ->
                onClick.invoke(select)
            }
            Spacer(modifier = Modifier.height(10.dp))
        }
    }
}

@Composable
fun DrawerRow(
    DrawerData: DrawerData,
    onClick: (DrawerData) -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .padding(start = 20.dp, end = 20.dp)
            .clickable {
                onClick.invoke(DrawerData)
            }
    ) {
        Icon(
            painter = painterResource(id = DrawerData.pic),
            contentDescription = null,
            tint = MainColor,
            modifier = Modifier.size(25.dp)
        )
        Spacer(modifier = Modifier.width(10.dp))
        Text(
            text = DrawerData.title, style = TextStyle(
                fontSize = 20.sp, color = MainColor
            )
        )
    }
}


@Composable
fun TopAppBar(
    title: String,
    onAction: () -> Unit
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 10.dp, end = 10.dp)
    ) {

        Row {}
        Text(text = title, style = TextStyle(color = MainColor, fontSize = 18.sp))
        IconButton(onClick = onAction) {
            Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null, tint = MainColor)
        }
    }
}


@Composable
fun ProfilePhoto(uri: Uri? = null) {
    Surface(
        shape = CircleShape,
        color = MainColor,
        modifier = Modifier.size(120.dp)
    ) {
        PickPhoto(uri, isProfile = true)
    }
}

// needs editing

@Composable
fun SmallPhoto(uri: Uri? = null) {
    Surface(
        shape = CircleShape,
        color = MainColor,
        modifier = Modifier.size(50.dp)
    ) {
        PickPhoto(isProfile = true, selectImage = uri)
    }
}

//post Screen
@Composable
fun PickPhoto(
    selectImage: Uri? = null,
    isProfile: Boolean = false
) {
    if (selectImage != null) {
        Image(
            painter = rememberAsyncImagePainter(selectImage),
            contentDescription = null,
            modifier = if (isProfile) Modifier.clip(CircleShape) else Modifier.clip(
                RoundedCornerShape(25.dp)
            ), contentScale = ContentScale.Crop
        )
    } else {
        Icon(
            imageVector = if (!isProfile) Icons.Default.ArrowUpward else Icons.Default.Person,
            contentDescription = null,
            tint = if (!isProfile) Color.Gray.copy(alpha = 0.2f) else Color.White
        )
    }
}



//craft photo

@Composable
fun InternetCraftPhoto( uri: String){
    Box {

        // to reload image
        var refreshImage by remember { mutableStateOf(0) }
        val painter = rememberAsyncImagePainter(
            model = ImageRequest.Builder(LocalContext.current)
                .data(uri)
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
    }
}

//craft name
@Composable
fun InternetCraftName(jobName :String){
    Surface(
        color = Color.White,
        modifier = Modifier
            .height(90.dp)
            .width(350.dp),
        shape = RoundedCornerShape(bottomEnd = 30.dp, bottomStart = 30.dp),
        elevation = 5.dp
    ) {
        Box(contentAlignment = Alignment.Center) {
            Text(text = jobName)
        }
    }
}


@Composable
fun ProblemDescription(
    problemDescription: String, modifier: Modifier = Modifier
        .fillMaxWidth()
        .height(250.dp)
) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(25.dp),
        border = BorderStroke(width = 1.dp, color = MainColor)
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 25.dp, end = 25.dp, top = 10.dp)
        ) {
            item {
                Text(
                    text = problemDescription,
                    style = TextStyle(
                        color = MainColor, fontSize = 20.sp
                    )
                )
            }
        }
    }
}

@Composable
fun StarsNumber(stars: Int) {
    Row {
        if (stars >= 1) {
            Icon(
                imageVector = Icons.Default.Star,
                contentDescription = null,
                tint = GoldColor
            )
        }
        if (stars >= 2) {
            Icon(
                imageVector = Icons.Default.Star,
                contentDescription = null,
                tint = GoldColor
            )
        }
        if (stars >= 3) {
            Icon(
                imageVector = Icons.Default.Star,
                contentDescription = null,
                tint = GoldColor
            )
        }
        if (stars >= 4) {
            Icon(
                imageVector = Icons.Default.Star,
                contentDescription = null,
                tint = GoldColor
            )
        }
        if (stars == 5) {
            Icon(
                imageVector = Icons.Default.Star,
                contentDescription = null,
                tint = GoldColor
            )
        }
    }
}