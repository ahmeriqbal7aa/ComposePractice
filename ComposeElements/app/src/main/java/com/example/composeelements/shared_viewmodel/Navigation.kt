package com.example.composeelements.shared_viewmodel

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun ScreenNavigationWithSharedViewModel() {

    val navHostController = rememberNavController()
    val viewModel: SharedViewModel = viewModel()

    NavHost(navController = navHostController, startDestination = FIRST_SCREEN) {
        composable(FIRST_SCREEN) {
            FirstScreen(navHostController, viewModel)
        }
        composable(SECOND_SCREEN) {
            SecondScreen(viewModel)
        }
    }

}

const val FIRST_SCREEN = "FIRST"
const val SECOND_SCREEN = "SECOND"