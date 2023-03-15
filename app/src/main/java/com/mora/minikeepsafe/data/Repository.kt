package com.mora.minikeepsafe.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class Repository @Inject constructor(private val photoWebService: IPhotoWebService) {

    fun getPhotosList(page: Int) : Flow<Response<ArrayList<PhotosItem>>> = flow {
        emit(Response.Processing)
        try {
            val photos = photoWebService.getPhotosList(page)
            emit(Response.Success(photos))
        } catch(e: Exception) {
            emit(Response.Error(e))
        }
    }
}