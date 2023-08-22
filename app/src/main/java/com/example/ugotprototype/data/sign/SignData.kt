package com.example.ugotprototype.data.sign

data class SignData(
    val name: String,
    var nickname: String,
    var email: String,
    var password: String,
    var major: String,
    var grade: Int,
    var _class: String,
    var skill: List<String>,
    var gitHubLink: String,
    var personalBlogLink: String

)
