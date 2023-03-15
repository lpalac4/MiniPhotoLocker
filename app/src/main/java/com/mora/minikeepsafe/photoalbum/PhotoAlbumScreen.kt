package com.mora.minikeepsafe.photoalbum

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.mora.minikeepsafe.data.Response
import com.mora.minikeepsafe.util.Constants.LIST_OFFSET

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun PhotoAlbumScreen(viewModel: PhotoAlbumViewModel, onPhotoClick: () -> Unit) {
    val photoResponse = viewModel.responseState.value
    val photoList = remember { viewModel.photosState }
    val endOfResponse = viewModel.reachedEndOfList.value
    val gridState = rememberLazyGridState()

    if (photoResponse is Response.Error) {
        Column {
            Text("Unable to fetch your photos")
            Text("Error: ${photoResponse.e.message}")
        }
    } else {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            LazyVerticalGrid(modifier = Modifier.fillMaxSize(), state = gridState, columns = GridCells.Fixed(3)) {
                items(photoList.value) {
                    GlideImage(model = it.downloadUrl, contentDescription = it.author, modifier = Modifier.height(100.dp).clickable {
                        viewModel.clickedImage = photoList.value.indexOf(it)
                        onPhotoClick() })
                }
            }

            val reachedEndOfGrid by remember {
                derivedStateOf {
                    (gridState.layoutInfo.visibleItemsInfo.isEmpty())
                    || (gridState.layoutInfo.visibleItemsInfo.last().index >= photoList.value.size - LIST_OFFSET)
                }
            }

            Log.d("ComposeTag", "New composition...gridState = $reachedEndOfGrid, responseState = $endOfResponse")
            if (reachedEndOfGrid && !endOfResponse) {
                viewModel.fetchPhotoAlbum()
            }

            if(photoResponse is Response.Processing) {
                CircularProgressIndicator()
            }
        }
    }
}