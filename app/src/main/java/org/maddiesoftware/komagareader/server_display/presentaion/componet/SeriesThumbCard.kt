package org.maddiesoftware.komagareader.server_display.presentaion.componet

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import org.maddiesoftware.komagareader.core.presentation.theme.GoldUnreadBookCount
import org.maddiesoftware.komagareader.server_display.domain.model.Series

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SeriesThumbCard(
    series: Series?,
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
                    if(series?.booksUnreadCount != 0){
                        Chip(
                            modifier = Modifier.align(Alignment.TopEnd),
                            shape = RectangleShape,
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
                                text = "${series?.booksUnreadCount}",
                                color = Color.White,
                                fontWeight = FontWeight.Bold,                        )
                        }
                    }
                    }


                Box(modifier = Modifier.fillMaxSize().align(Alignment.Start)) {
                    Text(
                        text = series?.metadata?.title.toString(),
                        color = MaterialTheme.colors.onSecondary,
                        modifier = Modifier
                            .align(Alignment.TopStart)
                            .padding(start = 5.dp, top = 5.dp),

                    )

                    Text(
                        text = "${series?.booksCount.toString()} Books",
                        color = MaterialTheme.colors.onSecondary,
                        modifier = Modifier
                            .align(Alignment.BottomStart)
                            .padding(start = 5.dp, bottom = 5.dp)

                    )
                }
            }

        }

    }
}


