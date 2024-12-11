package com.example.eatwise.network

import com.example.eatwise.data.Article
import com.example.eatwise.data.User
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path

interface EatWiseApiService {
    @GET("articles")
    suspend fun getArticle(): List<Article>

    @GET("users/{uid}")
    suspend fun getUserById(
        @Path("uid") userId: String
    ): User

    @POST("users")
    suspend fun updateUser(
        @Body user: User
    )

    @PATCH("users/{uid}")
    suspend fun editUser(
        @Path("uid") userId: String,
        @Body user: User
    )
}