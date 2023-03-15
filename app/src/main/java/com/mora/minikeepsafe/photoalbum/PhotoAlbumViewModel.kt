package com.mora.minikeepsafe.photoalbum

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mora.minikeepsafe.data.PhotosItem
import com.mora.minikeepsafe.data.Repository
import com.mora.minikeepsafe.data.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PhotoAlbumViewModel @Inject constructor(val repo: Repository) : ViewModel() {
    var clickedImage: Int = -1
    private var pageToFetch: Int = 1
    val responseState: MutableState<Response<ArrayList<PhotosItem>>?> = mutableStateOf(null)
    val photosState: MutableState<ArrayList<PhotosItem>> = mutableStateOf(arrayListOf())
    val reachedEndOfList: MutableState<Boolean> = mutableStateOf(false)

    fun fetchPhotoAlbum(page: Int = pageToFetch) {
        if(responseState.value != null && responseState.value is Response.Processing) return

        viewModelScope.launch {
            repo.getPhotosList(page).onEach {
                responseState.value = it
                if (it is Response.Success) {

                    if (it.data.isEmpty()) {
                        reachedEndOfList.value = true
                    } else {
                        photosState.value.addAll(it.data)
                        pageToFetch = page + 1
                    }
                }
            }.flowOn(Dispatchers.IO).launchIn(viewModelScope)
        }
    }
}