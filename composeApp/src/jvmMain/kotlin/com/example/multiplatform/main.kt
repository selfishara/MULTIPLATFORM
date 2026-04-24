package com.example.multiplatform

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.example.multiplatform.data.WgerExerciseRepository
import com.example.multiplatform.data.remote.WgerApi
import com.example.multiplatform.data.remote.WgerDataSource
import com.example.multiplatform.navigation.NavigationWrapper
import com.example.multiplatform.network.HttpClientProvider

fun main() = application {
    val api = WgerApi(HttpClientProvider.client)
    val dataSource = WgerDataSource(api)
    val repository = WgerExerciseRepository(dataSource)

    Window(
        onCloseRequest = ::exitApplication,
        title = "GymSpot Lite"
    ) {
        App(repository)
    }
}