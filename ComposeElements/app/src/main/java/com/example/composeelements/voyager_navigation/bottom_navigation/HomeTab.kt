package com.example.composeelements.voyager_navigation.bottom_navigation

import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions

object HomeTab : Tab {
    override val options: TabOptions
        @Composable
        get() {
            val title = "HomeTitle"
            val icon = rememberVectorPainter(Icons.Default.Home)
            return remember { TabOptions(0u, title, icon) }
        }

    @Composable
    override fun Content() {
        Text(text = "Home")
    }
}