package com.example.ugotprototype.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.ugotprototype.data.TeamData

class TeamViewModel : ViewModel() {
    private val _teamItemList = MutableLiveData<ArrayList<TeamData>>()
    val teamItemList: LiveData<ArrayList<TeamData>>
        get() = _teamItemList

    private var teamItems = ArrayList<TeamData>()

    init {
        testData()
    }

    fun testData() {
        teamItems = arrayListOf(
            TeamData(
                "DmuIT 프로젝트를 함께 할 팀원모집",
                "팀명은 UGOT에 프로젝트명은 DmuIT로 동양미래대학교의 컴퓨터공학부 팀프로젝트에서 반 변경을 위한 앱입니다",
                "분야 : Android",
                "인원 : 3/8",
                "댓글 : 8"
            ),
            TeamData(
                "Web 팀원 모집",
                "Web FrontEnd 팀원모집중",
                "분야 : FrontEnd",
                "인원 : 2/4",
                "댓글 : 4"
            )
        )
        _teamItemList.value = teamItems
    }
}