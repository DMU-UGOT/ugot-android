package com.example.ugotprototype.ui.study.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.ugotprototype.data.study.StudyData

class StudyViewModel : ViewModel() {
    private val _studyItemList = MutableLiveData<ArrayList<StudyData>>() // 뷰 모델에서 데이터 처리를 하는 변수
    val studyItemList: LiveData<ArrayList<StudyData>> = _studyItemList

    fun setStudyData(studyData: ArrayList<StudyData>) {
        _studyItemList.value = studyData
    }
}