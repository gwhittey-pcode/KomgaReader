package org.maddiesoftware.komagareader.komga_server.presentaion.componet.general

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Download
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.*
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.maddiesoftware.komagareader.core.presentation.theme.GoldUnreadBookCount
import org.maddiesoftware.komagareader.komga_server.domain.model.Book
import org.maddiesoftware.komagareader.komga_server.domain.model.TriangleShape


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun BookThumbCard(
    book: Book?,
    modifier: Modifier = Modifier,
    url: String,
    onItemClick: (id: String) -> Unit,
    boxColor: Color = MaterialTheme.colors.surface,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        val pageRead = book?.readProgress?.page?.toFloat()
        val totalPages = book?.media?.pagesCount?.toFloat()
        val presentRead = pageRead?.div(totalPages!!)
        val isDownloaded = true
        Card(
            elevation = 5.dp,
            modifier = Modifier
                .height(295.dp)
                .width(155.dp)
                .padding(5.dp)
                .background(MaterialTheme.colors.surface)
                .clickable {
                    onItemClick(book?.id.toString())
                },

        ) {

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colors.surface),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Top,
                ) {
                Box(modifier = Modifier.fillMaxWidth()) {
                    MyAsyncImage(
                        url = url,
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .height(200.dp)
                            .width(150.dp),
                    )
                   if (!isDownloaded) {
                    IconButton(
                        onClick = {Log.d("Komga","This is icon clicked")},
                        modifier = Modifier
                            .align(Alignment.BottomEnd)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Download,
                            contentDescription = "Download",
                            tint = Color.Red,
                            modifier = Modifier.size(48.dp)
                        )
                    }
                   }else{
                       IconButton(
                           onClick = {},
                           modifier = Modifier
                               .align(Alignment.BottomEnd)
                       ) {
                           Icon(
                               imageVector = Icons.Default.Check,
                               contentDescription = "Download",
                               tint = Color.Green,
                               modifier = Modifier.size(48.dp)
                           )
                       }
                   }
                    if (book?.readProgress?.completed == false || book?.readProgress === null) {
                        Chip(
                            modifier = Modifier
                                .align(Alignment.TopEnd)
                                .size(30.dp),
                            shape = TriangleShape(),
                            onClick = { /* Do something! */ },
                            border = BorderStroke(
                                ChipDefaults.OutlinedBorderSize,
                                GoldUnreadBookCount
                            ),
                            colors = ChipDefaults.chipColors(
                                backgroundColor = GoldUnreadBookCount,
                                contentColor = Color.Red
                            ),
                        ) {
                            Text(
                                text = "",
                                color = Color.White,
                                fontWeight = FontWeight.Bold,
                                )
                        }
                    }

                }

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .align(Alignment.Start)
                        .background(boxColor)
                ) {
                    Text(
                        text = book?.seriesTitle.toString(),
                        fontSize = 14.sp,
                        color = MaterialTheme.colors.onSecondary,
                        modifier = Modifier
                            .align(Alignment.TopStart)
                            .padding(start = 5.dp, top = 2.dp),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        text = "${book?.metadata?.number.toString()} - ${book?.metadata?.title.toString()}",
                        fontSize = 14.sp,
                        color = MaterialTheme.colors.onSecondary,
                        modifier = Modifier
                            .align(Alignment.CenterStart)
                            .offset(0.dp, (-10).dp)
                            .padding(start = 5.dp, top = 2.dp),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        text = "${book?.media?.pagesCount.toString()} Pages",
                        fontSize = 14.sp,
                        color = MaterialTheme.colors.onSecondary,
                        modifier = Modifier
                            .align(Alignment.BottomStart)
                            .offset(0.dp, (-15).dp)
                            .padding(start = 5.dp, bottom = 2.dp)
                    )

                    Log.d("percent", "PercentRead = $presentRead")
                    if (book?.readProgress?.completed == false || book?.readProgress === null) {
                        if (presentRead != null && presentRead != 1f) {
                            LinearProgressIndicator(
                                progress = presentRead,
                                modifier = Modifier
                                    .align(Alignment.BottomStart)
                                    .padding(10.dp),
                                color = GoldUnreadBookCount
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun FavoriteButton(
    modifier: Modifier = Modifier,
    color: Color = Color.Red
) {

    var isDownloaded by remember { mutableStateOf(true) }

    IconToggleButton(
        checked = isDownloaded,
        onCheckedChange = {
            isDownloaded = !isDownloaded
        }
    ) {
        Icon(
            tint = color,

            imageVector = if (isDownloaded) {
                Icons.Filled.Check
            } else {
                Icons.Default.Download
            },
            contentDescription = null
        )
    }

}


