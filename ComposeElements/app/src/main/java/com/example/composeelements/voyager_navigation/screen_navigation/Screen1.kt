package com.example.composeelements.voyager_navigation.screen_navigation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow

object FirstScreen : Screen {
    @Composable
    override fun Content() {
        // it keep - navigate to which screen, backStackInfo, Lifecycle aware etc
        val navigator = LocalNavigator.currentOrThrow
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Button(onClick = {
                // push is an "infix" function, so we write it in following different ways

                navigator.push(ThirdScreen("Ahmer"))

                //navigator push SecondScreen
                //navigator += SecondScreen
            }) {
                Text(text = "Click here")
            }
        }
    }
}