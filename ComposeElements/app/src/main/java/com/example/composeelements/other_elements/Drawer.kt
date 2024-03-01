package com.example.composeelements.other_elements

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import kotlinx.coroutines.launch

@Composable
fun Drawer() {

    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()

    Scaffold(
        scaffoldState = scaffoldState,
        drawerContent = {
            DrawerContent(scaffoldState)
        }, drawerGesturesEnabled = false
    ) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues)) {
            IconButton(onClick = {
                scope.launch { scaffoldState.drawerState.open() }
            }) {
                Icon(Icons.Default.Menu, contentDescription = "", tint = Color.Blue)
            }
        }
    }
}

@Composable
fun DrawerContent(scaffoldState: ScaffoldState) {

    val scope = rememberCoroutineScope()

    IconButton(onClick = {
        scope.launch { scaffoldState.drawerState.close() }
    }) {
        Icon(
            Icons.Default.Close,
            contentDescription = "",
            tint = Color.Blue
        )
    }
}