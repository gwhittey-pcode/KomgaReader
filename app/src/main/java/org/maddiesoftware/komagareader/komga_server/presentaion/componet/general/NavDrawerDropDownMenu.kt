package org.maddiesoftware.komagareader.komga_server.presentaion.componet.general

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import org.maddiesoftware.komagareader.komga_server.domain.model.Library


@Composable
fun NavDrawerDropDownMenu(
    libraryList: List<Library>
) {

    var expanded by remember { mutableStateOf(false) }

    var selectedText by remember { mutableStateOf("All") }

    var textfieldSize by remember { mutableStateOf(Size.Zero) }

    val icon = if (expanded)
        Icons.Filled.KeyboardArrowUp
    else
        Icons.Filled.KeyboardArrowDown


    Column(Modifier.padding(20.dp)) {
        Button(
            onClick = { expanded = !expanded },
            modifier = Modifier
                .fillMaxWidth(.5f)
                .onGloballyPositioned { coordinates ->
                    //This value is used to assign to the DropDown the same width
                    textfieldSize = coordinates.size.toSize()
                },
        ) {
            Text(selectedText, color = MaterialTheme.colors.onSurface)
            Icon(
                imageVector = Icons.Filled.ArrowDropDown,
                contentDescription = null,
            )
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .width(with(LocalDensity.current) { textfieldSize.width.toDp() })
        ) {
            DropdownMenuItem(
                onClick = {
                    selectedText = "All"
                    expanded = false
                }) {
                Text(text = "All", color = MaterialTheme.colors.onSurface)
            }
            libraryList.forEach { library ->
                DropdownMenuItem(
                    onClick = {
                        selectedText = library.name as String
                        expanded = false
                        Log.d("NavDrop", "LibraryId = ${library.id}")
                    }) {
                    Text(text = library.name as String)
                }
            }
        }
    }

}

