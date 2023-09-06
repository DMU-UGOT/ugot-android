package com.example.ugotprototype

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment

class FragmentLoadingLayout : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = Dialog(requireContext())
        dialog.setContentView(R.layout.fragment_loading_layout)
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        return dialog
    }
}





