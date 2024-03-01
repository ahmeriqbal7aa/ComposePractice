package com.example.composeelements.other_elements

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ParentContent() {
    var name by remember { mutableStateOf("") }
    ChildContent(name = name) { name = it }
}

@Composable
fun ChildContent(name: String, onChange: (String) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Hello $name")

        Spacer(modifier = Modifier.height(20.dp))

        TextField(
            value = name,
            onValueChange = { onChange(it) },
            placeholder = { Text(text = "Type Something...") }
        )
    }
}

@Composable
fun Stateful() {
    var name by remember { mutableStateOf("") }

    TextField(
        value = name,
        onValueChange = { name = it }
    )
}

@Composable
fun Stateless() {
    Text(text = "Hello world")
}