package com.example.composeelements.other_elements

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import java.util.Locale

@Composable
fun SearchView() {

    var search by remember { mutableStateOf("") }

    val fruitList = listOf(
        "Lion",
        "Tiger",
        "Apple",
        "Orange",
        "Apricot",
        "Monkey",
        "Cheetah",
        "Beer",
        "Apple",
        "Mango",
        "Money",
        "Banana"
    )


    LazyColumn(modifier = Modifier.padding(20.dp)) {

        val filterList: List<String> = if (search.isEmpty()) {
            fruitList
        } else {
            val result = arrayListOf<String>()
            for (item in fruitList) {
                if (item.lowercase(Locale.getDefault()).contains(
                        search.lowercase(Locale.getDefault())
                    )
                ) {
                    result.add(item)
                }
            }
            result
        }

        item {
            CustomSearchView(text = search, onValueChange = {
                search = it
            })
        }

        items(filterList) { data ->
            Text(text = data, modifier = Modifier.padding(top = 10.dp))
        }
    }
}

@Composable
fun CustomSearchView(
    text: String,
    onValueChange: (String) -> Unit
) {
    TextField(
        value = text, onValueChange = { onValueChange(it) },
        modifier = Modifier.fillMaxWidth(),
        placeholder = { Text(text = "Search") },
        leadingIcon = { Icon(Icons.Default.Search, contentDescription = "", tint = Color.Black) },
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = Color.Transparent
        )
    )
}