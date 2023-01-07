package org.maddiesoftware.komagareader.server_select.presentation.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.maddiesoftware.komagareader.server_select.domain.model.Server


@Composable
fun ServerUiListItem(
    serverListItem: Server,
    modifier: Modifier = Modifier,
    onDeleteClick: () -> Unit

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
                color = MaterialTheme.colors.onSurface,
                fontSize = 20.sp
            )
            IconButton(
                onClick = onDeleteClick,
                modifier = Modifier.align(Alignment.Bottom)
            ) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Delete note",
                    tint = Color.Red
                )
            }
        }
        Divider(color = MaterialTheme.colors.onSurface)
        Row(
            modifier = Modifier
                .fillMaxWidth(),
//            contentAlignment = Alignment.Center
        ){
            Text(
                serverListItem.userName,
                color = MaterialTheme.colors.onSurface,
                fontSize = 16.sp,
                modifier = Modifier.fillMaxWidth(.5F)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                serverListItem.url,
                color = MaterialTheme.colors.onSurface,
                fontSize = 16.sp,
                textAlign = TextAlign.Right,
                modifier = Modifier.fillMaxWidth()

            )
        }
    }
}