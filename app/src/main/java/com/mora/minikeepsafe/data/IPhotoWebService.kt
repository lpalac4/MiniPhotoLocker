package com.mora.minikeepsafe.data

import com.mora.minikeepsafe.util.Constants.LIMIT
import com.mora.minikeepsafe.util.Constants.PHOTOS_LIST
import retrofit2.http.GET
import retrofit2.http.Query

interface IPhotoWebService {
    @GET(PHOTOS_LIST)
    suspend fun getPhotosList(@Query("page") page: Int, @Query("limit") limit: Int = LIMIT) : ArrayList<PhotosItem>
}