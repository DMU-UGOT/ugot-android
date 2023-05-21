package com.example.ugotprototype.ui.study

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.ugotprototype.R
import com.example.ugotprototype.databinding.FragmentStudyBinding

class StudyFragment : Fragment() {
    private lateinit var binding: FragmentStudyBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_study, container, false)
    }
}