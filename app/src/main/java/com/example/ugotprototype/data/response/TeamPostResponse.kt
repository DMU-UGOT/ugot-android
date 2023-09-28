package com.example.ugotprototype.data.response

data class TeamPostResponse(
    val teamId : Int,
    val title: String,
    val content: String,
    val field: String,
    val nickname: String,
    val _class: String,
    var allPersonnel: Int,
    val viewCount: Int,
    val nowPersonnel: Int,
    val kakaoOpenLink: String,
    val gitHubLink: String,
    var avatarUrl: String,
    val createdAt: String,
    val goal: String,
    val language: String,
    var isDelete: Int,
    var bookmark: Boolean,
    var groupId: Int,
    var groupName: String
)
