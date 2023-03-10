package com.mora.minikeepsafe.photoalbum

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import kotlinx.coroutines.launch

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun PhotoDetailScreen(viewModel: PhotoAlbumViewModel) {
    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    LazyRow(
        modifier = Modifier.fillMaxSize(),
        state = listState
    ) {
        if(viewModel.clickedImage != -1){
            coroutineScope.launch {
                listState.scrollToItem(viewModel.clickedImage)
            }
        }

        items(viewModel.photosState.value) {
            GlideImage(
                model = it.downloadUrl,
                contentDescription = it.author,
                modifier = Modifier
                    .fillParentMaxSize()
            )
        }
    }
}