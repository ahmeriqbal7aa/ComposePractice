package com.example.composeelements.other_elements

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.composeelements.R
import com.example.composeelements.ui.theme.DarkPink
import com.example.composeelements.ui.theme.LightPink
import com.example.composeelements.ui.theme.greenColor

@Composable
fun CustomToggleButtonWithImage() {

    var isToggle by remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Card(shape = RoundedCornerShape(39.dp), elevation = 0.dp) {
            Box(
                modifier = Modifier
                    .background(if (isToggle) greenColor else Color.Black)
                    .clickable { isToggle = !isToggle },
                contentAlignment = Alignment.Center
            ) {
                if (isToggle) {
                    Row(modifier = Modifier.padding(5.dp)) {
                        Text(
                            text = "On",
                            style = TextStyle(fontSize = 12.sp, fontWeight = FontWeight.W400),
                            color = Color.White,
                            modifier = Modifier
                                .padding(start = 10.dp)
                                .align(Alignment.CenterVertically)
                        )
                        Icon(
                            painter = painterResource(id = R.drawable.on), contentDescription = "",
                            tint = Color.Unspecified,
                            modifier = Modifier.padding(start = 10.dp)
                        )
                    }
                } else {
                    Row(modifier = Modifier.padding(5.dp)) {
                        Icon(
                            painter = painterResource(id = R.drawable.off), contentDescription = "",
                            tint = Color.Unspecified
                        )
                        Text(
                            text = "Off",
                            style = TextStyle(fontSize = 12.sp, fontWeight = FontWeight.W400),
                            color = Color.White,
                            modifier = Modifier
                                .padding(start = 10.dp, end = 10.dp)
                                .align(Alignment.CenterVertically)
                        )
                    }
                }
            }
        }
    }
}


@Composable
fun CustomToggleButtonPink() {
    var selected by remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Card(
            modifier = Modifier.width(50.dp),
            elevation = 0.dp,
            shape = RoundedCornerShape(16.dp)
        ) {
            Box(
                modifier = Modifier
                    .background(if (selected) DarkPink else LightPink)
                    .clickable { selected = !selected },
                contentAlignment = if (selected) Alignment.TopEnd else Alignment.TopStart
            ) {
                CustomCheck(Modifier.padding(5.dp))
            }
        }
    }
}


@Composable
fun CustomCheck(modifier: Modifier) {
    Card(
        modifier = modifier.size(20.dp),
        elevation = 0.dp,
        shape = CircleShape
    ) {
        Box(modifier = Modifier.background(Color.White))
    }
}