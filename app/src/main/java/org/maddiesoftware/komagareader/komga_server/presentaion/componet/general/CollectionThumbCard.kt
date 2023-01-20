
package org.maddiesoftware.komagareader.komga_server.presentaion.componet.general

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CollectionThumbCard(
    seriesCount: Int?,
    id: String,
    name: String?,
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
                .height(295.dp)
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
                            .width(150.dp)
                        )
                }


                Box(modifier = Modifier.fillMaxSize().align(Alignment.Start)) {
                    Text(
                        text = name.toString(),
                        color = MaterialTheme.colors.onSecondary,
                        modifier = Modifier
                            .align(Alignment.TopStart)
                            .padding(start = 5.dp, top = 5.dp),
                        fontSize = 14.sp
                        )

                    Text(
                        text = "${seriesCount.toString()} series",
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


