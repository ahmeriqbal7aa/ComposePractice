package com.example.composenotification.nav_deeplink_onClick_notification

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController

@Composable
fun HomePage(navController: NavHostController) {
    // in Home Page
    // we have put Text in center
    // and it's below put button click on that click we have navigate to Home Detail Page.
    Box(
        modifier = Modifier
            .background(Color.Gray)
            .fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .background(Color.Gray)
                .fillMaxSize()
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "Home Page ")

                Button(onClick = {
                    // for navigate to another page we have used below method.
                    // pass route name of second page.
                    navController.navigate("HomeDetailPage")
                }) {

                    Text(text = "Home Detail Page.")
                }
            }
        }
    }
}