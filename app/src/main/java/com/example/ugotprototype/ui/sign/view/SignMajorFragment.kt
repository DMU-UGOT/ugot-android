package com.example.ugotprototype.ui.sign.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.children
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.ugotprototype.R
import com.example.ugotprototype.databinding.FragmentSignMajorBinding
import com.example.ugotprototype.ui.sign.viewmodel.SignViewModel
import com.google.android.material.chip.Chip


class SignMajorFragment : Fragment() {
    private lateinit var binding: FragmentSignMajorBinding
    private val signViewModel: SignViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_sign_major, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        signViewModel.major.value?.let { selectedMajor ->
            val selectedChip = binding.chipGroup.children
                .filterIsInstance<Chip>()
                .firstOrNull { it.text == selectedMajor }

            selectedChip?.let {
                binding.chipGroup.check(it.id)
            }
        }

        binding.chipGroup.setOnCheckedChangeListener { _, checkedId ->
            val clickedChip = view.findViewById<Chip>(checkedId)
            if (clickedChip?.text != signViewModel.major.value) {
                signViewModel.setMajor(clickedChip?.text.toString())
            }
        }
    }
}