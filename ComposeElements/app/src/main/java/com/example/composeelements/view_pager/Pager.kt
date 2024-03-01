package com.example.composeelements.view_pager

import androidx.annotation.DrawableRes
import com.example.composeelements.R

data class Pager(
    @DrawableRes val image: Int,
    val des: String
)

val dataList = listOf(
    Pager(R.drawable.page_one, "PAGE ONE"),
    Pager(R.drawable.page_two, "PAGE TWO"),
    Pager(R.drawable.page_one, "PAGE THREE")
)