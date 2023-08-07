package com.example.ugotprototype.di.api.response

import android.graphics.pdf.PdfDocument

data class TeamDataResponse(
    val data: List<TeamPostResponse>,
    val pageInfo: TeamPostPageInfoResponse
)
