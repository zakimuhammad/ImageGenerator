package com.zaki.imagegenerator.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.zaki.imagegenerator.model.ImageResponse
import com.zaki.imagegenerator.repository.ImageRepository
import com.zaki.imagegenerator.utils.Resource
import kotlinx.coroutines.launch
import retrofit2.Response
import java.io.IOException

class ImageViewModel(
    val app: Application,
    private val imageRepository: ImageRepository
) : AndroidViewModel(app) {

    val allImage: MutableLiveData<Resource<ImageResponse>> = MutableLiveData()
    var imageResponse: ImageResponse? = null

    init {
        getAllImage()
    }

    fun getAllImage() = viewModelScope.launch { safeImageCall() }

    private suspend fun safeImageCall() {
        allImage.postValue(Resource.Loading())
        try {
            val response = imageRepository.getMemes()
            allImage.postValue(handleImageResponse(response))
        } catch (t: Throwable) {
            when(t) {
                is IOException -> allImage.postValue(Resource.Error("Network Failure!"))
                else -> allImage.postValue(Resource.Error("Conversion Error!"))
            }
        }
    }

    private fun handleImageResponse(response: Response<ImageResponse>): Resource<ImageResponse> {
        if (response.isSuccessful) {
            response.body()?.let { resultReponse ->
                imageResponse = resultReponse
                return Resource.Success(imageResponse ?: resultReponse)
            }
        }
        return Resource.Error(response.message())
    }
}