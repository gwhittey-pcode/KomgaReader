package org.maddiesoftware.komagareader.download.presentation.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import org.maddiesoftware.komagareader.core.data.local.ServerInfoSingleton
import org.maddiesoftware.komagareader.core.presentation.viewmodels.MainViewModel

@Destination
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun DownloadMainScreen(
    navigator: DestinationsNavigator,
    mainViewModel: MainViewModel){

    mainViewModel.showTopBar.value = true
    val serverInfo = ServerInfoSingleton
    Box(modifier = Modifier.fillMaxSize()) {
        Text("Hello world!")
    }
}

