package com.example.composeelements.voyager_navigation.screen_navigation

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen

object SecondScreen : Screen {
    @Composable
    override fun Content() {
        Text(text = "Hey")
    }
}