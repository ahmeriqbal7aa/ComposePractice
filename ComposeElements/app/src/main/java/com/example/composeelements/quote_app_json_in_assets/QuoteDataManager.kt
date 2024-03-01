package com.example.composeelements.quote_app_json_in_assets

import android.content.Context
import androidx.compose.runtime.mutableStateOf
import com.example.composeelements.quote_app_json_in_assets.model.Quote
import com.google.gson.Gson

enum class Pages {
    LISTING,
    DETAIL
}

object QuoteDataManager {

    var data = emptyArray<Quote>()
    var currentPage = mutableStateOf(Pages.LISTING)
    var currentQuote: Quote? = null
    var isDataLoaded = mutableStateOf(false)

    fun loadAssetsFromFile(context: Context) {
        val inputStream = context.assets.open("quotes.json")
        val size: Int = inputStream.available()
        val buffer = ByteArray(size)
        inputStream.read(buffer)
        inputStream.close()

        val json = String(buffer, Charsets.UTF_8)
        data = Gson().fromJson(json, Array<Quote>::class.java)
        isDataLoaded.value = true
    }

    fun switchPage(quote: Quote?) {
        if (currentPage.value == Pages.LISTING) {
            currentQuote = quote
            currentPage.value = Pages.DETAIL
        } else {
            currentPage.value = Pages.LISTING
        }
    }
}