package org.maddiesoftware.komagareader.server_select_feature.presentation.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.maddiesoftware.komagareader.server_select_feature.presentation.state.ServerListItem
import org.maddiesoftware.komagareader.ui.theme.white


@Composable
fun OrderUiListItem(
    serverListItem: ServerListItem,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ){
            Text(
                serverListItem.serverName,
                fontWeight = FontWeight.Bold,
                color = white,
                fontSize = 20.sp
            )
            Text(
                serverListItem.url,
                fontWeight = FontWeight.Bold,
                color = white,
                fontSize = 20.sp
            )
        }
        Divider(color = white)
        Box(
            modifier = Modifier
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ){
            Text(
                serverListItem.userName,
                color = white,
                fontSize = 16.sp
            )
        }
    }
}