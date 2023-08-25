package com.example.ugotprototype.data.response

data class TeamPostResponse(
    val title: String,
    val content: String,
    val field: String,
    val _class: String,
    var allPersonnel: Int,
    val viewCount: Int,
    val nowPersonnel: Int,
    val kakaoOpenLink: String,
    val gitHubLink: String,
    var avatarUrl: String,
    val createdAt: String
)
