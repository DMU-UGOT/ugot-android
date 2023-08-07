package com.example.ugotprototype.di.api.response

import com.google.gson.annotations.SerializedName

data class OrgDataResponse(
    @SerializedName("avatar_url")
    val avatarUrl: String
)
