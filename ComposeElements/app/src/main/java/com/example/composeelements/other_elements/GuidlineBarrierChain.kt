package com.example.composeelements.other_elements

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout

@Composable
fun GuidLineExample() { // Like a "Spacer" in Compose

    ConstraintLayout(modifier = Modifier.fillMaxWidth()) {
        val (text1) = createRefs()
        val createGuidTop = createGuidelineFromTop(40.dp)

        Text(
            text = "Some Contents",
            modifier = Modifier.constrainAs(text1) {
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                top.linkTo(createGuidTop)
            }
        )
    }

}

@Composable
fun BarrierExample() { // Provide Space between Two Corresponding Items

    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .padding(30.dp)
    ) {
        val (text1, text2, text3) = createRefs()
        val barrierEnd = createEndBarrier(text1, text2)

        Text(
            text = "Text One",
            modifier = Modifier.constrainAs(text1) {
                start.linkTo(parent.start)
                top.linkTo(parent.top)
            }
        )

        Text(
            text = "Text Two",
            modifier = Modifier.constrainAs(text2) {
                start.linkTo(parent.start)
                top.linkTo(text1.bottom)
            }
        )

        Text(
            text = "Text Three",
            modifier = Modifier.constrainAs(text3) {
                start.linkTo(barrierEnd)
                top.linkTo(text1.bottom)
            }
        )
    }

}

@Composable
fun ChainExample() { // Like a "SpaceBetween","SpaceEvenly" etc

    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {
        val (text1, text2, text3) = createRefs()

        createHorizontalChain(
            text1,
            text2,
            text3,
            //chainStyle = ChainStyle.Spread         // Default
            //chainStyle = ChainStyle.SpreadInside  // SpaceBetween
            //chainStyle = ChainStyle.Packed       // Center but Texts will Collapsed
        )

        /*createVerticalChain(
            text1,
            text2,
            text3,
            //chainStyle = ChainStyle.Spread        // Default
            //chainStyle = ChainStyle.SpreadInside // SpaceBetween
            //chainStyle = ChainStyle.Packed      // Center but Texts will Join with each other
        )*/

        Text(
            text = "Text One",
            modifier = Modifier.constrainAs(text1) {
                start.linkTo(parent.start)
                top.linkTo(parent.top)
            }
        )

        Text(
            text = "Text Two",
            modifier = Modifier.constrainAs(text2) {
                start.linkTo(text1.end)
                top.linkTo(text1.top)
                bottom.linkTo(text1.bottom)
            }
        )

        Text(
            text = "Text Three",
            modifier = Modifier.constrainAs(text3) {
                start.linkTo(text2.end)
                top.linkTo(text2.top)
                bottom.linkTo(text2.bottom)
            }
        )
    }

}