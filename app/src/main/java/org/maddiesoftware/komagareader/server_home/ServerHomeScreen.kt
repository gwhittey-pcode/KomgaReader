package org.maddiesoftware.komagareader.server_home

import android.annotation.SuppressLint
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import org.maddiesoftware.komagareader.core.domain.model.Server
import org.maddiesoftware.komagareader.server_select.presentation.ServerListViewModel

@Destination
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ServerHomeScreen(
    navigator: DestinationsNavigator,
    viewModel: ServerHomeViewModel = hiltViewModel(),
    serverId: Int = -1,
){
    val scope = rememberCoroutineScope()
    val scaffoldState = rememberScaffoldState()
    Scaffold(

    ){
        Text(text = serverId.toString() )
    }
}