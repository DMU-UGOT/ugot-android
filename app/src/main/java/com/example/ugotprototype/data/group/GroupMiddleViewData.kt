package com.example.ugotprototype.data.group

data class GroupMiddleViewData(
    val groupId: Int,
    val groupName: String,
    val content: String,
    val nowPersonnel: Int,
    val githubUrl: String,
    var avatarUrl: String,
    var isFavorites: Boolean
)
