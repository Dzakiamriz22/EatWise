package com.example.eatwise.ui.tips

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class TipsViewModel : ViewModel() {

    private val _tipsList = MutableLiveData<List<String>>().apply {
        value = listOf(
            "Tip 1: Lorem ipsum dolor sit amet",
            "Tip 2: Consectetur adipiscing elit",
            "Tip 3: Sed do eiusmod tempor incididunt",
            "Tip 4: Sed do eiusmod temper incididunt",
            "Tip 5: Sed do eiusmod tempor incididunt",
        )
    }
    val tipsList: LiveData<List<String>> = _tipsList
}