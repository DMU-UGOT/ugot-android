package com.example.ugotprototype.data.group

data class GroupDetailData (
    val groupId: Int,
    val groupName: String,
    val nickname: String,
    var nowPersonnel: Int,
    val content: String
)