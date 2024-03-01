package com.example.composeelements.voyager_navigation.bottom_navigation

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.bottomSheet.LocalBottomSheetNavigator

object BottomSheetScreen : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalBottomSheetNavigator.current
        Button(onClick = {
            navigator.show(SheetContent)
        }) {
            Text(text = "Click Here")
        }
    }
}

object SheetContent : Screen {
    @Composable
    override fun Content() {
        LazyColumn {
            item {
                val navigator = LocalBottomSheetNavigator.current
                IconButton(onClick = {
                    navigator.hide()
                }) {
                    Icon(Icons.Default.Close, contentDescription = "")
                }
            }

            items(10) { data ->
                Text("Count : $data")
            }
        }
    }

}