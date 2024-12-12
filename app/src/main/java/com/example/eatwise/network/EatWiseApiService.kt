package com.example.eatwise.network

import com.example.eatwise.data.Article
import com.example.eatwise.data.User
import okhttp3.MultipartBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Part
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

    @Multipart
    @POST("predict")
    suspend fun uploadImage(
        @Part image: MultipartBody.Part
    ): Response<PredictionResponse>
}