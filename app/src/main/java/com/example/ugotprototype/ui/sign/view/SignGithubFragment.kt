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
import com.example.ugotprototype.databinding.FragmentSignGithubBinding
import com.example.ugotprototype.ui.sign.viewmodel.SignViewModel

class SignGithubFragment : Fragment() {
    private lateinit var binding: FragmentSignGithubBinding
    private val signViewModel: SignViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_sign_github, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //다음페이지나 이전페이지에 갔다왔을때
        //입력된 값이 초기화되는걸 방지
        binding.etInputGithub.setText(signViewModel.gitHubLink.value?.toString() ?: "")

        binding.etInputGithub.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                signViewModel.setGitHubLink(s.toString())
            }
        })
    }
}