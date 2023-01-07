package org.maddiesoftware.komagareader.server_display.presentaion.componet

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.*
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.maddiesoftware.komagareader.core.presentation.theme.GoldUnreadBookCount
import org.maddiesoftware.komagareader.server_display.domain.model.Book
import org.maddiesoftware.komagareader.server_display.domain.model.TriangleShape


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun BookThumbCard(
    book: Book?,
    modifier: Modifier = Modifier,
    url: String,
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
//        Log.d("percent", "id = ${book?.id}")
//        Log.d("percent", "title = ${book?.metadata?.title}")
//        Log.d("percent", "pageRead = $pageRead")
//        Log.d("percent", "totalPages = $totalPages")
//        Log.d("percent", "completed = ${book?.readProgress?.completed}")
//        Log.d("percent", "############################################################")
        val precentRead = pageRead?.div(totalPages!!)
        Card(
            modifier = Modifier
                .height(300.dp)
                .width(155.dp)
                .padding(5.dp)
                .background(MaterialTheme.colors.surface),
            elevation = 5.dp
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
                    Log.d("percent", "PercentRead = $precentRead")
                    if (book?.readProgress?.completed == false || book?.readProgress === null) {
                        if (precentRead != null && precentRead != 1f) {
                            LinearProgressIndicator(
                                progress = precentRead,
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



