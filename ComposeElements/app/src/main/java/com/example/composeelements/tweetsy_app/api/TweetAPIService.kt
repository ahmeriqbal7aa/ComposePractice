package com.example.composeelements.tweetsy_app.api

import com.example.composeelements.tweetsy_app.models.TweetItems
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers

interface TweetAPIService {

    @GET("/v3/b/657863f4dc746540188234e0?meta=false")
    @Headers("X-JSON-Path: tweets..category")
    suspend fun getCategories(): Response<List<String>>

    @GET("/v3/b/657863f4dc746540188234e0?meta=false")
    suspend fun getTweets(@Header("X-JSON-Path") category: String): Response<List<TweetItems>>

}