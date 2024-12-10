package com.example.eatwise.network

import com.example.eatwise.data.Article
import retrofit2.http.GET

interface EatWiseApiService {
    @GET("articles")
    suspend fun getArticle(): List<Article>
}