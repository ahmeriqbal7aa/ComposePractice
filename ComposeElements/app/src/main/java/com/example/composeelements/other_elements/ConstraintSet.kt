package com.example.composeelements.other_elements

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet

@Composable
fun LoginPageWithConstraintSet() {

    var name by remember { mutableStateOf("") }
    var pwd by remember { mutableStateOf("") }
    val context = LocalContext.current

    val constraints = ConstraintSet {

        val username = createRefFor("username")
        val password = createRefFor("password")
        val button = createRefFor("button")

        constrain(username) {
            start.linkTo(parent.start)
            end.linkTo(parent.end)
            top.linkTo(parent.top)
        }

        constrain(password) {
            start.linkTo(parent.start)
            end.linkTo(parent.end)
            top.linkTo(username.bottom, margin = 10.dp)
        }

        constrain(button) {
            start.linkTo(parent.start)
            end.linkTo(parent.end)
            top.linkTo(password.bottom, margin = 10.dp)
        }
    }

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        ConstraintLayout(constraintSet = constraints) {
            TextField(
                value = name,
                onValueChange = { name = it },
                modifier = Modifier.layoutId("username")
            )
            TextField(
                value = pwd,
                onValueChange = { pwd = it },
                modifier = Modifier.layoutId("password"),
                visualTransformation = PasswordVisualTransformation()
            )
            Button(
                onClick = {
                    Toast.makeText(context, "Login Called", Toast.LENGTH_SHORT).show()
                },
                modifier = Modifier.layoutId("button")
            ) {
                Text(text = "Login")
            }
        }
    }
}