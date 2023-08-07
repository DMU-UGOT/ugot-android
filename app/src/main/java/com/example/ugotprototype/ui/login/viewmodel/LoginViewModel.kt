package com.example.ugotprototype.ui.login.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class LoginViewModel : ViewModel() {
    private val _githubUserName = MutableLiveData<String>()
    val githubUserName : LiveData<String> = _githubUserName

    fun setUserName(githubUserName: String) {
        _githubUserName.value = githubUserName
    }
}