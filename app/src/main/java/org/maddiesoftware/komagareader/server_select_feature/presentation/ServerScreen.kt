package org.maddiesoftware.komagareader.server_select_feature.presentation

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import org.maddiesoftware.komagareader.core.presentation.ScreenRoutes
import org.maddiesoftware.komagareader.server_select_feature.presentation.components.OrderUiListItem
import org.maddiesoftware.komagareader.server_select_feature.presentation.state.ServerListItem
import org.maddiesoftware.komagareader.ui.theme.gray
import org.maddiesoftware.komagareader.ui.theme.orange
import org.maddiesoftware.komagareader.ui.theme.white

@Composable
fun ServerScreen(
    navController: NavController,
    serverListViewModel: ServerListViewModel = hiltViewModel()
) {

    val scaffoldState = rememberScaffoldState()

    Scaffold(
        scaffoldState = scaffoldState,
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
//                    navController.navigate(ScreenRoutes.OrderChooseDelivererScreen.route)
                },
                backgroundColor = orange
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "fab_add_order",
                    tint = white
                )
            }
        },
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Order overview",
                        color = white
                    )
                },
                backgroundColor = orange
            )
        }
    ){
        if(serverListViewModel.serverList.isEmpty()){
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ){
                Text("There are no orders yet")
            }
        }else{
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .background(gray)
                    .padding(10.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ){
               items(
                   serverListViewModel.serverList,
                   key = {serverListItem ->
                       serverListItem.serverId
                   }
               ){
                   OrderUiListItem(
                       it,
                       modifier = Modifier
                           .fillMaxWidth()
                           .clip(RoundedCornerShape(10.dp))
                           .border(1.dp, color = white, RoundedCornerShape(10.dp))
                           .clickable {
                               serverListViewModel.onServerClick(it.serverId)
                           }
                           .padding(15.dp)
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