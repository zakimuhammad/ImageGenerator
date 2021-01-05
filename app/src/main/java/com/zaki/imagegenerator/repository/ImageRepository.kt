package com.zaki.imagegenerator.repository

import com.zaki.imagegenerator.network.RetrofitInstance

class ImageRepository {

    suspend fun getMemes() = RetrofitInstance.api.getMemes()

}