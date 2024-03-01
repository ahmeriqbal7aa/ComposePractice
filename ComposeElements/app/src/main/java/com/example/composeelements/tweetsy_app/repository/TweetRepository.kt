package com.example.composeelements.tweetsy_app.repository

import com.example.composeelements.tweetsy_app.api.TweetAPIService
import com.example.composeelements.tweetsy_app.models.TweetItems
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class TweetRepository @Inject constructor(private val tweetAPIService: TweetAPIService) {

    private val _categories = MutableStateFlow<List<String>>(emptyList())
    val categories: StateFlow<List<String>> get() = _categories

    private val _tweets = MutableStateFlow<List<TweetItems>>(emptyList())
    val tweets: StateFlow<List<TweetItems>> get() = _tweets

    suspend fun getCategories() {
        val response = tweetAPIService.getCategories()
        if (response.isSuccessful && response.body() != null) {
            _categories.emit(response.body()!!)
        }
    }

    suspend fun getTweets(category: String) {
        val response = tweetAPIService.getTweets("tweets[?(@.category==\"$category\")]")
        if (response.isSuccessful && response.body() != null) {
            _tweets.emit(response.body()!!)
        }
    }
}