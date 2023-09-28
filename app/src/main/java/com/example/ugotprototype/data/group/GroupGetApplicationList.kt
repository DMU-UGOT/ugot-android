package com.example.ugotprototype.data.group

data class GroupGetApplicationList(
    val applicationId: Int,
    val memberId: Int,
    val nickname: String,
    val major: String,
    val grade: Int,
    val _class: String,
    val gitHubLink: String,
    val personalBlogLink: String,
    val skill: List<String>
)
