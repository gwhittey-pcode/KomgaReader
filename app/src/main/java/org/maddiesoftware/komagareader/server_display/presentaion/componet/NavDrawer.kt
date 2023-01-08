package org.maddiesoftware.komagareader.server_display.presentaion.componet

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import org.maddiesoftware.komagareader.server_display.domain.model.Library

@Composable
fun NavDrawer(
    libraryList: List<Library>?,
    modifier: Modifier = Modifier,
    itemTextStyle: TextStyle = TextStyle(fontSize = 18.sp),
    onItemClick: (id: String) -> Unit,
    viewModel: ViewModel,
) {
    DrawerHeader()
    DrawerBodySelectionScreen(
        libraryList = libraryList,
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
    libraryList: List<Library>?,
    modifier: Modifier = Modifier,
    itemTextStyle: TextStyle = TextStyle(fontSize = 18.sp),
    onItemClick: (id: String) -> Unit,

    ) {
    Column {
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
                style = itemTextStyle,
                modifier = Modifier.weight(1f),
                fontSize = MaterialTheme.typography.h4.fontSize,
                fontWeight = FontWeight.ExtraBold,
                color = MaterialTheme.colors.primary,
            )
        }
        Row(){
            NavDrawerExpanpCard(
                title = "Libraries",
                libraryList = libraryList,
                onItemClick = onItemClick
            )
        }


    }

}
