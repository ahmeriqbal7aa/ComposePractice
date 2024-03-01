package com.example.composeelements.other_elements

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout

@Composable
fun ConstraintLayoutArrangeHorizontally() {
    ConstraintLayout(modifier = Modifier.fillMaxWidth()) {
        val (text1, text2, text3) = createRefs()

        Text(
            text = "Text One",
            modifier = Modifier.constrainAs(text1) {
                start.linkTo(parent.start)
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
            }
        )

        Text(
            text = "Text Two",
            modifier = Modifier.constrainAs(text2) {
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
            }
        )

        Text(
            text = "Text Three",
            modifier = Modifier.constrainAs(text3) {
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
            }
        )
    }
}

@Composable
fun ConstraintLayoutExampleDemo() {

    ConstraintLayout(modifier = Modifier.fillMaxWidth()) {

        val (box1, box2, box3) = createRefs()

        Box(
            modifier = Modifier
                .size(150.dp)
                .background(color = Color.Red)
                .constrainAs(box1) {}
        )

        Box(
            modifier = Modifier
                .size(100.dp)
                .background(color = Color.Yellow)
                .constrainAs(box2) {}
        )

        Box(
            modifier = Modifier
                .size(50.dp)
                .background(color = Color.Green)
                .constrainAs(box3) {}
        )
    }
}