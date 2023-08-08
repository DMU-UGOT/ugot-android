package com.example.ugotprototype.data.team


data class TeamPostData(
    val title: String,
    val content: String,
    val field: String,
    val _class: String,
    val allPersonnel: Int,
    val kakaoOpenLink: String,
    val gitHubLink: String
)