package org.maddiesoftware.komagareader.server_display.presentaion.componet

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.LibraryBooks
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun NavDrawerList(
    sections: List<CollapsableSection>,
    modifier: Modifier = Modifier,
    onItemClick: (id: String) -> Unit,
    itemTextStyle: TextStyle = TextStyle(fontSize = 18.sp),
) {
    val collapsedState = remember(sections) { sections.map { true }.toMutableStateList() }
    LazyColumn(modifier) {
        sections.forEachIndexed { i, dataItem ->
            val collapsed = collapsedState[i]
            item(key = "header_$i") {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .clickable {
                            collapsedState[i] = !collapsed
                        }
                ) {
                    Icon(
                        Icons.Default.run {
                            if (collapsed)
                                KeyboardArrowDown
                            else
                                KeyboardArrowUp
                        },
                        contentDescription = "",
                        tint = Color.Black,
                    )
                    Text(
                        dataItem.title,
                        fontWeight = FontWeight.Bold,
                        style = itemTextStyle,
                        color = MaterialTheme.colors.onSecondary,
                        modifier = Modifier
                            .padding(vertical = 10.dp)
                            .weight(1f)
                    )
                }
                Divider(color = MaterialTheme.colors.primary, modifier = Modifier.fillMaxWidth(.5f))
            }
            if (!collapsed) {
                items(dataItem.rows) { row ->

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                onItemClick(row.id)
                            }
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.Center,

                    ) {
                        Spacer(modifier = Modifier.width(20.dp))
                        Icon(
                            imageVector = Icons.Default.LibraryBooks,
                            contentDescription = row.title,
                            tint = MaterialTheme.colors.onSecondary,
                        )
                        Text(
                            text = row.title,
                            color = MaterialTheme.colors.onSecondary,
                            style = itemTextStyle,
                            modifier = Modifier.weight(1f)
                        )
                    }
//                    Divider()
                }
            }
        }
    }
}



data class CollapsableSection(val title: String, val rows: List<MenuItem>)


const val MaterialIconDimension = 24f