package com.example.ugotprototype.data.profile

data class ProfileMemberData(
    var name: String,
    var nickname: String,
    var email: String,
    var major: String,
    var grade: Int,
    var _class: String,
    var skill: List<String>,
    var gitHubLink: String,
    var personalBlogLink: String
)
