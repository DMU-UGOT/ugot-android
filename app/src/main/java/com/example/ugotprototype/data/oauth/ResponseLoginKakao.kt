package com.example.ugotprototype.data.oauth

data class ResponseLoginKakao(
    val data: Data,
) {
    data class Data(
        val tokenInfo: TokenInfo?,
        val accessToken: String
    ) {
        data class TokenInfo(
            val accessToken: String,
            val memberId: Int
        )
    }
}
