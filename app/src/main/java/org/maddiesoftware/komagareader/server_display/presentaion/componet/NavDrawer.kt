package org.maddiesoftware.komagareader.server_display.presentaion.componet

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.maddiesoftware.komagareader.server_display.domain.model.Library

@Composable
fun NavDrawer(
    items: List<MenuItem>,
    modifier: Modifier = Modifier,
    itemTextStyle: TextStyle = TextStyle(fontSize = 18.sp),
    onItemClick: (id:String) -> Unit
){
    DrawerHeader()
    DrawerBodySelectionScreen(
        items = items,
        modifier = modifier,
        itemTextStyle = itemTextStyle,
        onItemClick = onItemClick
    )

}

@Composable
fun DrawerHeader(

) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 14.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Header",
            fontSize = 24.sp,
            color = MaterialTheme.colors.onSecondary,
        )
    }
}

@Composable
fun DrawerBodySelectionScreen(
    items: List<MenuItem>,
    modifier: Modifier = Modifier,
    itemTextStyle: TextStyle = TextStyle(fontSize = 18.sp),
    onItemClick: (id:String) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onItemClick("home")
            }
            .padding(16.dp)
    ) {
        Icon(
            imageVector = Icons.Default.Home,
            contentDescription = "Home",
            tint = MaterialTheme.colors.onSecondary,
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = "Home",
            color = MaterialTheme.colors.onSecondary,
            style = itemTextStyle,
            modifier = Modifier.weight(1f)
        )
    }
    Column() {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    onItemClick("home")
                }
                .padding(16.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Home,
                contentDescription = "Libraries",
                tint = MaterialTheme.colors.onSecondary,
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = "Libraries",
                style = itemTextStyle,
                color = MaterialTheme.colors.onSecondary,
                modifier = Modifier.weight(1f)
            )
        }
        Row() {
            Spacer(modifier = Modifier.width(20.dp))
            LazyColumn(modifier) {
                items(items) { item ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                onItemClick(item.id)
                            }
                            .padding(16.dp)
                    ) {
                        Spacer(modifier = Modifier.width(46.dp))
                        Text(
                            text = item.title,
                            color = MaterialTheme.colors.onSecondary,
                            style = itemTextStyle,
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
            }
        }

    }

}

