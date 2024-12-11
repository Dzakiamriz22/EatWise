package com.example.eatwise.ui.profile

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.eatwise.data.User
import com.example.eatwise.data.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProfileViewModel (private val userRepository: UserRepository): ViewModel() {
    val userProfile = MutableLiveData<User>()
    val exception = MutableLiveData<Boolean>()
    val messageSuccess = MutableLiveData<Boolean>()

    fun getUserProfile(userId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                userProfile.postValue(userRepository.getUser(userId))
                exception.postValue(false)
            } catch(e:Exception) {
                exception.postValue(true)
            }
        }
    }

    fun updateUserProfile(user: User) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                userRepository.updateUser(user)
                exception.postValue(false)
                messageSuccess.postValue(true)
            } catch(e:Exception) {
                exception.postValue(true)
                messageSuccess.postValue(false)
            }
        }
    }

    fun editUserProfile(id: String, user: User) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                userRepository.editUser(id, user)
                exception.postValue(false)
            } catch(e:Exception) {
                exception.postValue(true)
            }
        }
    }
}