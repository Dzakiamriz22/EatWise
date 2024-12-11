package com.example.eatwise.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.eatwise.data.repository.UserRepository
import com.example.eatwise.ui.profile.ProfileViewModel

@Suppress("UNCHECKED_CAST")
class UserViewModelFactory(
    private val userRepository: UserRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(ProfileViewModel::class.java) -> {
                ProfileViewModel(userRepository) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}