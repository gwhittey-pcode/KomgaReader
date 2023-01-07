package org.maddiesoftware.komagareader.server_display.presentaion.componet

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LibraryBooks
import androidx.compose.material.icons.filled.LocalLibrary
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import org.maddiesoftware.komagareader.server_display.domain.model.Library


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun NavDrawerExpanpCard(
    title: String,
    titleFontSize: TextUnit = MaterialTheme.typography.h4.fontSize,
    titleFontWeight: FontWeight = FontWeight.ExtraBold,
    descriptionFontSize: TextUnit = MaterialTheme.typography.h6.fontSize,
    descriptionFontWeight: FontWeight = FontWeight.Bold,
    descriptionMaxLines: Int = 4,
    shape: Shape = MaterialTheme.shapes.small,
    padding: Dp = 12.dp,
    libraryList: List<Library>?,
    onItemClick: (id: String) -> Unit,
) {
    var expandedState by remember { mutableStateOf(false) }
    val rotationState by animateFloatAsState(
        targetValue = if (expandedState) 180f else 0f
    )
    if (libraryList != null) {
        expandedState = libraryList.size <= 10
    }
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .animateContentSize(
                animationSpec = tween(
                    durationMillis = 300,
                    easing = LinearOutSlowInEasing
                )
            )
            .padding(5.dp)
            .background(MaterialTheme.colors.surface),
        elevation = 5.dp,
        shape = shape,
        onClick = {
            expandedState = !expandedState
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(padding)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.LocalLibrary,
                    contentDescription = "",
                    tint = MaterialTheme.colors.onSecondary,
                )
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    modifier = Modifier
                        .weight(6f),
                    text = title,
                    fontSize = titleFontSize,
                    fontWeight = titleFontWeight,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    color = MaterialTheme.colors.primary,
                )

                IconButton(
                    modifier = Modifier
                        .weight(1f)
                        .alpha(ContentAlpha.medium)
                        .rotate(rotationState),
                    onClick = {
                        expandedState = !expandedState
                    }) {
//                    Icon(
//                        imageVector = Icons.Default.ArrowDropDown,
//                        contentDescription = "Drop-Down Arrow",
//                        tint = MaterialTheme.colors.onSecondary,
//                    )
                }
            }
            Divider(color = MaterialTheme.colors.primary)
            if (expandedState) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()

                )
                {
                    item {
                        Spacer(modifier = Modifier.height(15.dp))
                        Row(modifier = Modifier.clickable {
                            onItemClick("null")
                        }) {
                            Spacer(modifier = Modifier.width(35.dp))
                            Icon(
                                imageVector = Icons.Default.LibraryBooks,
                                contentDescription = "",
                                tint = MaterialTheme.colors.onSecondary,
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(5.dp))
                            Text(
                                text = "All",
                                fontSize = descriptionFontSize,
                                fontWeight = descriptionFontWeight,
                                maxLines = descriptionMaxLines,
                                overflow = TextOverflow.Ellipsis,
                                color = MaterialTheme.colors.onSecondary,
                            )
                        }
                    }
                    libraryList?.forEach { library ->
                        item {
                            Spacer(modifier = Modifier.height(15.dp))
                            Row(
                                modifier = Modifier.clickable {
                                    onItemClick(library.id.toString())
                                }
                            ) {
                                Spacer(modifier = Modifier.width(35.dp))
                                Icon(
                                    imageVector = Icons.Default.LibraryBooks,
                                    contentDescription = "",
                                    tint = MaterialTheme.colors.onSecondary,
                                    modifier = Modifier.size(20.dp)
                                )
                                Spacer(modifier = Modifier.width(5.dp))
                                Text(
                                    text = library.name.toString(),
                                    fontSize = descriptionFontSize,
                                    fontWeight = descriptionFontWeight,
                                    maxLines = descriptionMaxLines,
                                    overflow = TextOverflow.Ellipsis,
                                    color = MaterialTheme.colors.onSecondary,
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}


