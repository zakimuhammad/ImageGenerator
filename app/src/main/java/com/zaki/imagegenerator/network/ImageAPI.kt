package com.zaki.imagegenerator.network

import com.zaki.imagegenerator.model.ImageResponse
import retrofit2.Response
import retrofit2.http.GET

interface ImageAPI {
    @GET("get_memes")
    suspend fun getMemes(): Response<ImageResponse>
}