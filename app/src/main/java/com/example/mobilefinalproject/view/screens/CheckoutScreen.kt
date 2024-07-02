package com.example.mobilefinalproject.view.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.mobilefinalproject.R
import com.example.mobilefinalproject.config.CONSTANT
import com.example.mobilefinalproject.model.Course
import com.example.mobilefinalproject.navigation.NavigationItem
import com.example.mobilefinalproject.view.components.InfoDialog
import com.example.mobilefinalproject.view.components.MyButton
import com.example.mobilefinalproject.view.components.TopBar
import com.example.mobilefinalproject.viewmodel.CourseViewModel

@Composable
fun CheckoutScreen(
    userID:Int,
    courseID:Int,
    navController: NavController,
    courseViewModel: CourseViewModel
) {
    val course by courseViewModel.infoCourse.collectAsState()
    val isPaymentSuccess by courseViewModel.isPaymentSuccess.collectAsState()
    LaunchedEffect(key1 = Unit) {
        courseViewModel.fetchInfoCourseById(courseID)
    }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),

        ) {
            // Phần hiển thị thông tin sản phẩm
            TopBar(navController = navController, title = "Payment" )


            Column (
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Spacer(modifier = Modifier.height(60.dp))
                Column (
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {

                    Image(
                        painter = painterResource(id = R.drawable.qr_code),
                        contentDescription = "QR Code",
                        modifier = Modifier.size(300.dp)
                    )
                }
                Spacer(modifier = Modifier.height(30.dp))
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Số tài khoản",
                        style = MaterialTheme.typography.titleLarge
                    )
                    Text(
                        text = "123456789",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.SemiBold
                    )
                    Spacer(modifier = Modifier.height(30.dp))
                    Text(
                        text = "$" + course?.price.toString() ,
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.SemiBold,
                        textAlign = TextAlign.Center,

                    )
                    Spacer(modifier = Modifier.height(30.dp))
                    MyButton(
                        text = "PAYMENT" ,
                        modifier = Modifier.fillMaxWidth() ,
                        onClick = {
                            courseViewModel.payment(userID, courseID)
                        }
                    )
                }


            }

        }
    if (isPaymentSuccess){
        InfoDialog(
            title = "Payment Successfully!!",
            desc = "Check your email to get detailed information",
            onDismiss = {
                courseViewModel.deleteDialogPayment()
                navController.navigate("${NavigationItem.Home.route}/$userID")
            },
            urlIcon = CONSTANT.APP.SUCCESS_ICON_URL
        )

    }

}
