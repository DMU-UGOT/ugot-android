package com.example.ugotprototype.data.team

data class TeamBookmarkData(
    val teamId: Int,
    val title: String,
    val content: String,
    val field: String,
    val _class: String,
    val nickname: String,
    val bookmarked: Int,
    val allPersonnel: Int,
    val nowPersonnel: Int,
    val goal: String,
    val language: String,
    val kakaoOpenLink: String,
    val gitHubLink: String,
    val viewCount: Int,
    val createdAt: String
)
