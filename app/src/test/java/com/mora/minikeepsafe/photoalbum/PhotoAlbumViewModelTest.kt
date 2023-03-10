package com.mora.minikeepsafe.photoalbum

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.mora.minikeepsafe.data.IPhotoWebService
import com.mora.minikeepsafe.data.PhotosItem
import com.mora.minikeepsafe.data.Repository
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class PhotoAlbumViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()
    private lateinit var viewModel: PhotoAlbumViewModel

    @OptIn(DelicateCoroutinesApi::class)
    @Before
    fun setUp() {
        Dispatchers.setMain(newSingleThreadContext("ViewModelScope"))
        val mockService: IPhotoWebService = object: IPhotoWebService {
            override suspend fun getPhotosList(page: Int, limit: Int): ArrayList<PhotosItem> {
                return arrayListOf()
            }

        }

        val mockRepository = Repository(mockService)
        viewModel = PhotoAlbumViewModel(mockRepository)
    }

    @After
    fun tearDown() {
    }

    @Test
    fun fetchPhotoAlbum() {
        viewModel.fetchPhotoAlbum(1)
        assertTrue(viewModel.photosState.value.isEmpty())
    }
}