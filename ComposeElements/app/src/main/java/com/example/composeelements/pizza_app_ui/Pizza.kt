package com.example.composeelements.pizza_app_ui

import androidx.annotation.DrawableRes
import com.example.composeelements.R

data class Pizza(
    @DrawableRes val image: Int,
    val name: String,
    val description: String,
    val price: String
)

val pizzaList = listOf(
    Pizza(
        R.drawable.pizza_two,
        "Fresh Farm House",
        "crisp capsicum, succulent mushrooms and fresh tomatoes",
        "£14.25"
    ),
    Pizza(
        R.drawable.pizza_three,
        "Peppy Paneer",
        "Chunky paneer with crisp capsicum and spicy red pepper",
        "£16.75"
    ),
    Pizza(
        R.drawable.pizza_four,
        "Mexican Green Wave",
        "A pizza loaded with crunchy onions, crisp capsicum, juicy tomatoes",
        "£10.25"
    ),
    Pizza(
        R.drawable.pizza_three,
        "Fresh Farm House",
        "crisp capsicum, succulent mushrooms and fresh tomatoes",
        "£14.25"
    ),
    Pizza(
        R.drawable.pizza_two,
        "Fresh Farm House",
        "crisp capsicum, succulent mushrooms and fresh tomatoes",
        "£14.25"
    ),
    Pizza(
        R.drawable.pizza_three,
        "Peppy Paneer",
        "Chunky paneer with crisp capsicum and spicy red pepper",
        "£19.25"
    ),
    Pizza(
        R.drawable.pizza_four,
        "Fresh Farm House",
        "crisp capsicum, succulent mushrooms and fresh tomatoes",
        "£14.25"
    ),
    Pizza(
        R.drawable.pizza_two,
        "Fresh Farm House",
        "crisp capsicum, succulent mushrooms and fresh tomatoes",
        "£14.25"
    ),
)
