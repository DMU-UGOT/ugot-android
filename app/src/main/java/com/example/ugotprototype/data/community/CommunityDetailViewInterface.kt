package com.example.ugotprototype.data.community

interface CommunityDetailViewInterface {
    fun checkBoarderOwner(): Boolean
    fun modify() // need arguments of board info entity
    fun remove(boardId: Int)
}

