package org.maddiesoftware.komagareader.server_display.presentaion.componet

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.GenericShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.*
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
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
        var pageRead = book?.readProgress?.page?.toFloat()
        var totalPages = book?.media?.pagesCount?.toFloat()
        Log.d("percent", "id = ${book?.id}")
        Log.d("percent", "title = ${book?.metadata?.title}")
        Log.d("percent", "pageRead = $pageRead")
        Log.d("percent", "totalPages = $totalPages")
        Log.d("percent", "completed = ${book?.readProgress?.completed}")
        Log.d("percent", "############################################################")
        var precentRead = pageRead?.let {
            totalPages?.minus(it)?.div(totalPages)?.toFloat()
        }
        Card(
            modifier = Modifier
                .height(400.dp)
                .width(195.dp)
                .padding(5.dp)
                .background(MaterialTheme.colors.surface),
            elevation = 5.dp
        ){

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colors.surface),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Top,

                ) {
                Box(modifier = Modifier.fillMaxWidth()){
                    MyAsyncImage(
                        url = url,
                        modifier = Modifier
                            .align(Alignment.TopEnd),
                        )
                    if(book?.readProgress?.completed == false || book?.readProgress === null){
                        Chip(
                            modifier = Modifier
                                .align(Alignment.TopEnd)
                                .size(40.dp),
                            shape =  TriangleShape(),
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

                Box(modifier = Modifier
                    .fillMaxSize()
                    .align(Alignment.Start)) {
                    Text(
                        text = book?.seriesTitle.toString(),

                        color = MaterialTheme.colors.onSecondary,
                        modifier = Modifier
                            .align(Alignment.TopStart)
                            .padding(start = 5.dp, top = 5.dp),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis

                        )

                    Text(
                        text = "${book?.metadata?.number.toString()} - ${book?.metadata?.title.toString()}",
                        color = MaterialTheme.colors.onSecondary,
                        modifier = Modifier
                            .align(Alignment.CenterStart)
                            .padding(start = 5.dp, top = 1.dp),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis

                    )

                    Text(
                        text = "${book?.media?.pagesCount.toString()} Pages",
                        color = MaterialTheme.colors.onSecondary,
                        modifier = Modifier
                            .align(Alignment.BottomStart)
                            .padding(start = 5.dp, bottom = 5.dp)

                    )
                    Log.d("percent", "PercentRead = $precentRead")
                    if (precentRead != null) {
                        LinearProgressIndicator(
                            progress = precentRead,
                            modifier = Modifier
                                .align(Alignment.BottomStart)
                                .padding(5.dp)
                        )
                    }
                }
            }

        }

    }
}



