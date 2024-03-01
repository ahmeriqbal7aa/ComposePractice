package com.example.composeelements.tweetsy_app.nav_host

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.composeelements.tweetsy_app.screens.CategoryScreen
import com.example.composeelements.tweetsy_app.screens.DetailScreen

// Data of Tweets from "jsonbin.io"

@Composable
fun TweetsyAppNavHost() {

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Tweetsy") },
                backgroundColor = Color.Black,
                contentColor = Color.White,
            )
        }
    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)) {
            val navController = rememberNavController()

            NavHost(navController = navController, startDestination = "category") {

                composable(route = "category") {
                    CategoryScreen { category ->
                        navController.navigate("detail/${category}")
                    }
                }

                composable(route = "detail/{category}", arguments = listOf(
                    navArgument("category") {
                        type = NavType.StringType
                    }
                )) {
                    DetailScreen()
                }
            }
        }
    }
}