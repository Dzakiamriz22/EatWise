package com.example.eatwise.data.repository

import com.example.eatwise.data.Article
import com.example.eatwise.network.EatWiseApiService

class ArticleRepository (private val apiService: EatWiseApiService) {
    suspend fun getArticles(): List<Article> {
        return apiService.getArticle()
    }

}