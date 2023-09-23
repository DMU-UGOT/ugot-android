package com.example.ugotprototype.data.study

data class StudyGetPost(
    val studyId: Int,
    val title: String,
    val content: String,
    val isContact: String,
    val allPersonnel: Int,
    val nowPersonnel: Int,
    val kakaoOpenLink: String,
    var gitHubLink: String,
    val viewCount: Int,
    val createdAt: String,
    var avatarUrl: String,
    var subject: String,
    var field: String,
    var isDelete: Int,
    var bookmark: Boolean
)
