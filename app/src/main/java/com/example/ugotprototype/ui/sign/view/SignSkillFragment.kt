package com.example.ugotprototype.ui.sign.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.children
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.ugotprototype.R
import com.example.ugotprototype.databinding.FragmentSignSkillBinding
import com.example.ugotprototype.ui.sign.viewmodel.SignViewModel
import com.google.android.material.chip.Chip


class SignSkillFragment : Fragment() {
    private lateinit var binding: FragmentSignSkillBinding
    private val signViewModel: SignViewModel by activityViewModels()
    private val selectedChips = mutableSetOf<Chip>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_sign_skill, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.chipGroup.setOnCheckedChangeListener(null)

        Log.d("test", signViewModel.selectedChipTexts.value.toString())

        //다음페이지나 이전페이지에 갔다왔을때
        //입력된 값이 초기화되는걸 방지
        binding.chipGroup.children.filterIsInstance<Chip>().forEach { chip ->
            chip.isChecked =
                signViewModel.selectedChipTexts.value?.contains(chip.text.toString()) ?: false
        }

        binding.chipGroup.children.filterIsInstance<Chip>().forEach { chip ->
            chip.setOnClickListener {
                signViewModel.updateSelectedChips(chip)
            }
        }
    }
}