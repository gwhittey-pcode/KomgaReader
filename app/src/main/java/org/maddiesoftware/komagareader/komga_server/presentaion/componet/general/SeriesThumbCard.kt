
package org.maddiesoftware.komagareader.komga_server.presentaion.componet.general

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.maddiesoftware.komagareader.core.presentation.theme.GoldUnreadBookCount

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SeriesThumbCard(
    booksCount: Int?,
    booksUnreadCount: Int? = 0,
    id: String,
    title: String?,
    modifier: Modifier = Modifier,
    url: String,
    onItemClick: (id: String) -> Unit,
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
                .height(300.dp)
                .width(155.dp)
                .padding(5.dp)
                .background(MaterialTheme.colors.surface)
                .clickable {
                     onItemClick(id) 
                },
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
                            .align(Alignment.TopEnd)
                            .height(200.dp)
                            .width(155.dp)

                        )
                    if(booksCount != 0){
                        Chip(
                            modifier = Modifier.align(Alignment.TopEnd).offset((9).dp, (-8).dp),
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
                                text = "$booksUnreadCount",
                                color = Color.White,
                                fontWeight = FontWeight.Bold,
                                fontSize = 18.sp
                            )
                        }
                    }
                }


                Box(modifier = Modifier.fillMaxSize().align(Alignment.Start)) {
                    Text(
                        text = title.toString(),
                        color = MaterialTheme.colors.onSecondary,
                        modifier = Modifier
                            .align(Alignment.TopStart)
                            .padding(start = 5.dp, top = 5.dp),
                        fontSize = 14.sp
                        )

                    Text(
                        text = "${booksCount.toString()} Books",
                        color = MaterialTheme.colors.onSecondary,
                        modifier = Modifier
                            .align(Alignment.BottomStart)
                            .padding(start = 5.dp, bottom = 5.dp),
                        fontSize = 14.sp

                    )
                }
            }

        }

    }
}


