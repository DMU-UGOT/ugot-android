package com.example.ugotprototype.di.api.response

data class TeamPostResponse(
    val title: String,
    val content: String,
    val field: String,
    val _class: String,
    var personnel: Int,
    val viewCount: Int
)
