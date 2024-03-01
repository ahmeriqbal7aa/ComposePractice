package com.example.composeelements.chat_app_ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.composeelements.chat_app_ui.screens.ChatScreen
import com.example.composeelements.chat_app_ui.screens.HomeScreen
import com.example.composeelements.chat_app_ui.screens.StartScreen

@Composable
fun ChatAppMainNavigation() {

    val navHostController = rememberNavController()

    NavHost(navController = navHostController, startDestination = START_SCREEN) {
        composable(START_SCREEN) {
            StartScreen(navHostController)
        }
        composable(HOME_SCREEN) {
            HomeScreen(navHostController)
        }
        composable(CHAT_SCREEN) {
            ChatScreen(navHostController)
        }
    }

}

const val START_SCREEN = "Start screen"
const val HOME_SCREEN = "Home screen"
const val CHAT_SCREEN = "Char screen"