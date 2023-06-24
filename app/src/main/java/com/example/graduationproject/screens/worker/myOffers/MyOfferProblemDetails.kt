package com.example.graduationproject.screens.worker.myOffers

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.graduationproject.components.*
import com.example.graduationproject.navigation.AllScreens
import com.example.graduationproject.ui.theme.MainColor

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MyOfferProblemDetails(
    navController: NavController,
   // userId: String,
) {

    Scaffold(topBar = {
        TopAppBar(title = "تفاصيل الطلب") {
            navController.popBackStack()
        }
    }) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 25.dp, end = 25.dp),
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        //get Client id from database and nav to client profile
                        navController.navigate(route = AllScreens.ClientProfileScreen.name + "/${true}/${false}/${false}/ ")
                    },
                verticalAlignment = Alignment.CenterVertically
            ) {
                SmallPhoto()
                Spacer(modifier = Modifier.width(5.dp))
                Text(
                    text = "أحمد محمد", style = TextStyle(
                        color = MainColor,
                        fontSize = 15.sp
                    )
                )
            }
            Spacer(modifier = Modifier.height(20.dp))
            Row(
                modifier = Modifier.padding(start = 25.dp),
            ) {
                Text(
                    text = "تصليح كرسي- صيانة بسيطة", style = TextStyle(
                        color = MainColor,
                        fontSize = 18.sp
                    )
                )
            }
            Spacer(modifier = Modifier.height(15.dp))
            //ProblemDescription
            ProblemDescription(
                problemDescription = "لقد قمت بتحتطيم الكرسي الخاص بي و اريد من يقوم بمساعدتي في حل هذه المشكله واللتي ادت اللي عدم استعمالي لهذا الكرسي مره اخري و قمت بتحطيم الدولاب الخاص بي و قد تدني ويكون نجار كويس للتصليح و السعر يكون معقول جداااااا " +
                        "لقد قمت بتحتطيم الكرسي الخاص بي و اريد من يقوم بمساعدتي في حل هذه المشكله واللتي ادت اللي عدم استعمالي لهذا الكرسي مره اخري و قمت بتحطيم الدولاب الخاص بي و قد تدني ويكون نجار كويس للتصليح و السعر يكون معقول جدا ",
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 50.dp)
            )
        }
    }
}