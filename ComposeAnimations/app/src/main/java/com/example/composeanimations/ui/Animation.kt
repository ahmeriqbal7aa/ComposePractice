package com.example.composeanimations.ui

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavBackStackEntry
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController

const val animationTime: Int = 400

@ExperimentalAnimationApi
@Composable
fun Slide_In_Out_HorizontalAnimation() {
    val navController = rememberAnimatedNavController()

    AnimatedNavHost(navController, startDestination = "login") {

        composable("login") { Login(navController) }

        composable(
            "signup",
            enterTransition = enterTransitionFor("login"),
            exitTransition = exitTransitionFor("login"),
        ) { Signup(navController) }

        composable(
            "home",
            enterTransition = enterTransitionFor("signup"),
            exitTransition = exitTransitionFor("signup"),
        ) { HomeScreen(navController) }

        composable(
            "first",
            enterTransition = enterTransitionFor("home"),
            exitTransition = exitTransitionFor("home"),
        ) { FirstScreen(navController) }
    }
}


@OptIn(ExperimentalAnimationApi::class)
fun enterTransitionFor(initialDestination: String): AnimatedContentScope<NavBackStackEntry>.() -> EnterTransition? {
    return {
        when (initialState.destination.route) {
            initialDestination ->
                slideIntoContainer(
                    AnimatedContentScope.SlideDirection.Left,
                    animationSpec = tween(animationTime)
                )
            else -> null
        }
    }
}

@OptIn(ExperimentalAnimationApi::class)
fun exitTransitionFor(targetDestination: String): AnimatedContentScope<NavBackStackEntry>.() -> ExitTransition? {
    return {
        when (targetState.destination.route) {
            targetDestination ->
                slideOutOfContainer(
                    AnimatedContentScope.SlideDirection.Right,
                    animationSpec = tween(animationTime)
                )
            else -> null
        }
    }
}
