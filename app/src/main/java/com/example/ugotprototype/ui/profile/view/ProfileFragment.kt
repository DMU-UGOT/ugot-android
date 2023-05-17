package com.example.ugotprototype.ui.profile.view

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import com.example.ugotprototype.R
import com.example.ugotprototype.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding

    // 초기화
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false)
        var btnPressed = MutableLiveData<Boolean>(false)

        return binding.root
    }

    // 화면 전환
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        goToSchool()
        goToStack()
        goToAlarm()
    }



    // 개인 정보 페이지 이동
    private fun goToSchool() {

        var goToSchoolResultLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == Activity.RESULT_OK) {
                    val data: Intent? = result.data
                    val resultText = data?.getStringExtra("resultText")
                    if (resultText != null) {
                        Log.d("main", resultText)
                    }
                }
            }

        binding.profileSchoolBtn.setOnClickListener {
            goToSchoolResultLauncher.launch(Intent(requireContext(),SchoolActivity::class.java))
        }

    }

    // 스택관리
    private fun goToStack(){
        var goToStackResultLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == Activity.RESULT_OK) {
                    val data: Intent? = result.data
                    val resultText = data?.getStringExtra("resultText")
                    if (resultText != null) {
                        Log.d("main", resultText)
                    }
                }
            }

        binding.profileStackBtn.setOnClickListener {
            goToStackResultLauncher.launch(Intent(requireContext(),StackActivity::class.java))
        }

    }



    // 알람 설정
    private fun goToAlarm(){
        var goToAlarmResultLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == Activity.RESULT_OK) {
                    val data: Intent? = result.data
                    val resultText = data?.getStringExtra("resultText")
                    if (resultText != null) {
                        Log.d("main", resultText)
                    }
                }
            }

        binding.profileAlarmOptionBtn.setOnClickListener {
            goToAlarmResultLauncher.launch(Intent(requireContext(),AlarmActivity::class.java))
        }

    }
}