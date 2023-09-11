package com.example.ugotprototype.ui.sign.view

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.ugotprototype.R
import com.example.ugotprototype.databinding.FragmentSignGradeBinding
import com.example.ugotprototype.ui.sign.viewmodel.SignViewModel

class SignGradeFragment : Fragment() {
    private lateinit var binding: FragmentSignGradeBinding
    private val signViewModel: SignViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_sign_grade, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if(signViewModel.nowGrade.value != 0) {
            binding.etNowGrade.setText(signViewModel.nowGrade.value?.toString() ?: "")
        }

        binding.etNowGrade.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                signViewModel.setNowGrade(s.toString().toIntOrNull() ?: 0)
            }
        })
    }
}