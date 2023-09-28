package com.example.ugotprototype.data.group

data class GroupPostWriteData (
    var groupId: Int,
    var groupName: String,
    var content: String,
    var nowPersonnel: Int,
    var githubUrl: String
)