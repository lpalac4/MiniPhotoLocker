package com.mora.minikeepsafe

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.mora.minikeepsafe.ui.theme.MiniKeepsafeTheme
import dagger.hilt.android.AndroidEntryPoint
import androidx.navigation.compose.rememberNavController
import com.mora.minikeepsafe.photoalbum.PhotoAlbumScreen
import com.mora.minikeepsafe.photoalbum.PhotoAlbumViewModel
import com.mora.minikeepsafe.photoalbum.PhotoDetailScreen

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MiniKeepsafeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    AppWithNavigation()
                }
            }
        }
    }
}

@Composable
fun AppWithNavigation() {
    val navController = rememberNavController()
    Scaffold(
        scaffoldState = rememberScaffoldState(),
        topBar = { AppBar() },
        bottomBar = { BottomNavBar() }) {
            NavContainer(navController, it)
    }
}

private enum class Screens {
    PhotoAlbum,
    PhotoDetails
}

@Composable
fun NavContainer(navController: NavHostController, paddingValues: PaddingValues) {
    val viewModel = hiltViewModel<PhotoAlbumViewModel>()
    NavHost(navController = navController, startDestination = Screens.PhotoAlbum.name, Modifier.padding(paddingValues)) {
        composable(Screens.PhotoAlbum.name) {
            PhotoAlbumScreen(viewModel) { navController.navigate(Screens.PhotoDetails.name) }
        }
        composable(Screens.PhotoDetails.name) {
            PhotoDetailScreen(viewModel)
        }
    }
}

@Composable
fun AppBar() {

}

@Composable
fun BottomNavBar() {

}