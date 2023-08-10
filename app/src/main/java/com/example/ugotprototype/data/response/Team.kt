package com.example.ugotprototype.data.response

data class Team(
    val teamId: Int,
    val title: String,
    val content: String,
    val field: String,
    val _class: String,
    val allPersonnel: Int,
    val nowPersonnel: Int,
    val viewCount: Int,
    val bookmarked: Int,
    val kakaoOpenLink: String,
    val gitHubLink: String,
    var avatarUrl: String,
    val createdAt: String
)
