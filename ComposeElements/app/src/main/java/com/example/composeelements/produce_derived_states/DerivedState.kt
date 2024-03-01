package com.example.composeelements.produce_derived_states

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun DerivedState() {

    val tableOf = remember { mutableIntStateOf(5) }
    val index = remember { mutableIntStateOf(1) }

    // derived a new state on the bases of existing states
    val message = derivedStateOf {
        "${tableOf.intValue} * ${index.intValue} = ${tableOf.intValue * index.intValue}"
    }

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(
            text = message.value,
            style = MaterialTheme.typography.h3
        )
    }
}