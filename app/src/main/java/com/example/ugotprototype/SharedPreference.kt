package com.example.ugotprototype

import android.content.Context
import android.content.SharedPreferences
import com.example.ugotprototype.MainActivity.Companion.MY_MEMBER_ID_DATA
import com.example.ugotprototype.MainActivity.Companion.MY_MEMBER_ID_KEY
import com.example.ugotprototype.ui.sign.viewmodel.SignViewModel.Companion.AUTO_LOGIN_DATA
import com.example.ugotprototype.ui.sign.viewmodel.SignViewModel.Companion.AUTO_LOGIN_TITLE
import com.example.ugotprototype.ui.sign.viewmodel.SignViewModel.Companion.MY_TOKEN_DATA
import com.example.ugotprototype.ui.sign.viewmodel.SignViewModel.Companion.MY_TOKEN_TITLE
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class SharedPreference @Inject constructor(@ApplicationContext private val context: Context) {
    fun saveToken(token: String) {
        val sharedPreferences = getSharedPreferences().edit()
        sharedPreferences.putString(MY_TOKEN_TITLE, token).apply()
    }

    fun getToken(): String {
        return getSharedPreferences().getString(MY_TOKEN_TITLE, "") ?: ""
    }

    private fun getSharedPreferences(): SharedPreferences {
        return context.getSharedPreferences(MY_TOKEN_DATA, Context.MODE_PRIVATE)
    }

    fun saveAutoLogin(enabled: Boolean) {
        val sharedPreferences = getSharedPreferencesBoolean().edit()
        sharedPreferences.putBoolean(AUTO_LOGIN_TITLE, enabled).apply()
    }

    fun getAutoLogin(): Boolean {
        return getSharedPreferencesBoolean().getBoolean(AUTO_LOGIN_TITLE, false)
    }

    private fun getSharedPreferencesBoolean(): SharedPreferences {
        return context.getSharedPreferences(AUTO_LOGIN_DATA, Context.MODE_PRIVATE)
    }

    fun saveMemberId(memberId: Int) {
        val sharedPreferences = getSharedPreferencesMemberId().edit()
        sharedPreferences.putInt(MY_MEMBER_ID_KEY, memberId).apply()
    }

    fun getMemberId(): Int {
        return getSharedPreferencesMemberId().getInt(MY_MEMBER_ID_KEY, 0)
    }

    private fun getSharedPreferencesMemberId(): SharedPreferences {
        return context.getSharedPreferences(MY_MEMBER_ID_DATA, Context.MODE_PRIVATE)
    }
}

