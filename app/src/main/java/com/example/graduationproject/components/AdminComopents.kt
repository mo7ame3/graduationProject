package com.example.graduationproject.components

import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.graduationproject.R
import com.example.graduationproject.constant.Constant
import com.example.graduationproject.data.AdminUsersQuery
import com.example.graduationproject.ui.theme.MainColor
import com.example.graduationproject.ui.theme.RedColor

@Composable
fun AdminWorkerOrClientRow(
    adminUsersQuery: AdminUsersQuery,
    showCard: MutableState<Boolean> = mutableStateOf(false),
    cardData: MutableState<String>? = null,
    reportCLick: Boolean = false,
    onReportAction: (Int) -> Unit,
    onProfileAction: (String) -> Unit,
) {

    Row(
        modifier = if (!reportCLick) Modifier
            .fillMaxWidth()
            .padding(start = 10.dp, end = 10.dp)
            .clickable {
                onProfileAction.invoke(adminUsersQuery.name)
            } else Modifier
            .fillMaxWidth()
            .clickable {
                onProfileAction.invoke(adminUsersQuery.name)
            },
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            SmallPhoto()
            Spacer(modifier = Modifier.width(10.dp))
            Column {
                Text(
                    text = adminUsersQuery.name, style = TextStyle(
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                )
                StarsNumber(stars = adminUsersQuery.rate)
            }
        }

        Row(verticalAlignment = Alignment.CenterVertically) {
            if (!reportCLick) {
                IconButton(onClick = {
                    onReportAction.invoke(adminUsersQuery.id)
                }) {
                    Icon(
                        painter = painterResource(id = R.drawable.report_problem),
                        contentDescription = null,
                        modifier = Modifier.size(25.dp),
                        tint = if (adminUsersQuery.report >= Constant.reportLimit) RedColor else Color.Black
                    )
                }
            }
            IconButton(onClick = {
                //  if (adminUsersQuery.report >= Constant.reportLimit)
                showCard.value = true
                cardData?.value = adminUsersQuery.name
            }) {
                Icon(
                    painter = painterResource(id = R.drawable.block),
                    contentDescription = null,
                    modifier = Modifier.size(25.dp),
                )
            }
        }
    }
}

@Composable
fun CardReport(
    name: String,
    onAccept: () -> Unit,
    onReject: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp)
            .padding(start = 20.dp, end = 20.dp),
        elevation = 3.dp,
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            Text(
                text = "هل انت متأكد من حظر $name ؟", style = TextStyle(
                    fontWeight = FontWeight.SemiBold,
                    color = RedColor
                )
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                LoginButton(isLogin = true, label = "تأكيد") {
                    onAccept.invoke()
                }
                LoginButton(isLogin = false, label = "الغاء") {
                    onReject.invoke()
                }
            }
        }
    }
}

@Composable
fun FloatingAction(onAction: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.End
    ) {
        Surface(
            shape = CircleShape,
            color = MainColor,
            modifier = Modifier.size(50.dp)
        ) {
            IconButton(onClick = { onAction.invoke() }) {
                Icon(imageVector = Icons.Default.Add, contentDescription = null)
            }
        }
    }
}