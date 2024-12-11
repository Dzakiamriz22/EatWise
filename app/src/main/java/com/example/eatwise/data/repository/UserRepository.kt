package com.example.eatwise.data.repository

import com.example.eatwise.data.User
import com.example.eatwise.network.EatWiseApiService

class UserRepository (private val apiService: EatWiseApiService){
    suspend fun getUser(id: String): User {
        return apiService.getUserById(id)
    }

    suspend fun updateUser(user: User) {
        apiService.updateUser(user)
    }

    suspend fun editUser(id: String, user: User) {
        apiService.editUser(id, user)
    }
}