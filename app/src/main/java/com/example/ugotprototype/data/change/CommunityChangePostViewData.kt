package com.example.ugotprototype.data.change

data class CommunityChangePostViewData(
    val classChangeId : Int,
    val grade : String,
    val createdAt : String,
    val nickname : String,
    var currentClass : String,
    val changeClass : String,
    val status : String,
    val memberId : Int
)
