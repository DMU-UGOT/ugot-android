package com.example.ugotprototype.data.oauth

data class ResponseLoginNaver(
    val data: Data
) {
    data class Data(
        val tokenInfo: TokenInfo?
    ) {
        data class TokenInfo(
            val accessToken: String
        )
    }
}
