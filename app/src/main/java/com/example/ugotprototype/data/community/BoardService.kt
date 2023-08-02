package com.example.ugotprototype.data.community

class BoardService(): CommunityDetailViewInterface {
    override fun checkBoarderOwner(): Boolean {
       return true
    }

    override fun modify() {
       // 게시글 수정하는 비즈니스 로직 추가
    }

    override fun remove(boardId: Int) {
        // 게시글 지우는 비즈니스 로직 추가
    }
}