package com.example.ugotprototype.data.response

import com.google.gson.annotations.SerializedName

data class OrgMemberDataResponse(
    val login: String,
    @SerializedName("avatar_url")
    val avatarUrl: String,
    @SerializedName("html_url")
    val htmlUrl: String
)
