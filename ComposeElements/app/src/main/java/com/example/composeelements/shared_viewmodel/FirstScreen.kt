package com.example.composeelements.shared_viewmodel

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController

@Composable
fun FirstScreen(navHostController: NavHostController, viewModel: SharedViewModel) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
        Button(onClick = {
            viewModel.setData("Hello how are you?")
            navHostController.navigate(SECOND_SCREEN)
        }) {
            Text(text = "Send Data")
        }
    }
}