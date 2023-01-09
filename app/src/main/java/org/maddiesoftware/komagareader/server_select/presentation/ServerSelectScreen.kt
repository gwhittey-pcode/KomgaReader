package org.maddiesoftware.komagareader.server_select.presentation

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.launch
import org.maddiesoftware.komagareader.core.presentation.DataStoreViewModel
import org.maddiesoftware.komagareader.core.data.local.ServerInfoSingleton
import org.maddiesoftware.komagareader.destinations.HomeScreenDestination
import org.maddiesoftware.komagareader.destinations.ServerAddScreenDestination
import org.maddiesoftware.komagareader.server_select.presentation.components.ServerUiListItem

@RootNavGraph(start = true)
@Destination
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ServerSelectScreen(
    navigator: DestinationsNavigator,
    viewModel: ServerListViewModel = hiltViewModel(),
    dataStoreViewModel: DataStoreViewModel = hiltViewModel()

) {
    val scope = rememberCoroutineScope()
    val scaffoldState = rememberScaffoldState()
    val state = viewModel.state.value

    Scaffold(
        scaffoldState = scaffoldState,
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navigator.navigate(ServerAddScreenDestination())
                },
                backgroundColor = MaterialTheme.colors.primary
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "fab_add_order",
                    tint =MaterialTheme.colors.onSurface
                )
            }
        },
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Order overview",
                        color =MaterialTheme.colors.onSurface
                    )
                },
                backgroundColor = MaterialTheme.colors.surface
            )
        }
    ){
        if(viewModel.state.value.servers.isEmpty()){
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ){
                Text("There are no Servers added yet")
            }
        }else{
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colors.surface)
                    .padding(10.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp),
                   horizontalAlignment = Alignment.CenterHorizontally
                   ){
                   items(
                       state.servers,
                       key = {serverListItem ->
                           serverListItem.serverId!!
                       }
                   ){
                       Spacer(modifier = Modifier.width(16.dp))
                   ServerUiListItem(
                       it,
                       modifier = Modifier
                           .fillMaxWidth(.7F)
                           .clip(RoundedCornerShape(10.dp))
                           .border(1.dp, color =MaterialTheme.colors.onSurface, RoundedCornerShape(10.dp))
                           .clickable {
//                               dataStoreViewModel.savePassword(it.password)
//                               dataStoreViewModel.saveUrl(it.url)
//                               dataStoreViewModel.saveUserName(it.userName)
                               ServerInfoSingleton.serverName = it.serverName
                               ServerInfoSingleton.userName = it.userName
                               ServerInfoSingleton.password = it.password
                               ServerInfoSingleton.url = it.url
                               navigator.navigate(HomeScreenDestination())
                           }
                           .padding(15.dp),
                       onDeleteClick = {
                           viewModel.onEvent(ServersEvent.DeleteServer(it))
                           scope.launch {
                               val result = scaffoldState.snackbarHostState.showSnackbar(
                                   message = "Server deleted",
                                   actionLabel = "Undo"
                               )
                               if(result == SnackbarResult.ActionPerformed) {
                                   viewModel.onEvent(ServersEvent.RestoreServer)
                               }
                           }
                       }

                   )
               }
            }
        }
    }

//    if(serverListViewModel.isServerDialogShown && serverListViewModel.clickedServerItem != null){
//        OrderDetailDialog(
//            onDismiss = {
//                serverListViewModel.onDismissOrderDialog()
//            },
//            orderDetailListItem = serverListViewModel.clickedOrderItem!!
//        )
//    }

}