package com.example.ugotprototype.ui.Loading.util

import androidx.fragment.app.FragmentManager

class LoadingLayoutHelper(private val fragmentManager: FragmentManager) {
    private val loadingDialog = FragmentLoadingLayout()

    fun showLoadingDialog() {
        loadingDialog.isCancelable = false
        loadingDialog.show(fragmentManager, "loadingDialog")
    }

    fun dismissLoadingDialog() {
        loadingDialog.dismiss()
    }
}