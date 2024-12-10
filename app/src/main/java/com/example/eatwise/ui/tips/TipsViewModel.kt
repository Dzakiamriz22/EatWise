package com.example.eatwise.ui.tips

import android.util.Log
import androidx.lifecycle.*
import com.example.eatwise.data.Article
import com.example.eatwise.network.ApiClient
import kotlinx.coroutines.launch

class TipsViewModel : ViewModel() {

    private val _tipsList = MutableLiveData<List<Article>>()
    val tipsList: LiveData<List<Article>> = _tipsList

    // Add MutableLiveData for loading state
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    init {
        fetchArticles()
    }

    private fun fetchArticles() {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val articleList = ApiClient.apiService.getArticle()
                Log.d("TipsViewModel", "Fetched Articles: ${articleList.size}")
                Log.d("TipsViewModel", "First Article: ${articleList.firstOrNull()}")
                _tipsList.postValue(articleList)
            } catch (e: Exception) {
                Log.e("TipsViewModel", "Error fetching articles", e)
                // Consider adding error handling
                _tipsList.postValue(emptyList())
            } finally {
                _isLoading.value = false
            }
        }
    }
}