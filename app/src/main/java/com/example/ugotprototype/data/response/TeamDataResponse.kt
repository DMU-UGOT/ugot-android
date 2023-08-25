package com.example.ugotprototype.data.response

import android.graphics.pdf.PdfDocument

data class TeamDataResponse(
    val data: List<TeamPostResponse>,
    val pageInfo: TeamPostPageInfoResponse
)
