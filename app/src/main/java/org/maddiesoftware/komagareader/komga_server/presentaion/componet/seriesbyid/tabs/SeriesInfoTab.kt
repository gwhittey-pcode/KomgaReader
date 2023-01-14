package org.maddiesoftware.komagareader.komga_server.presentaion.componet.seriesbyid.tabs

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import org.maddiesoftware.komagareader.core.presentation.theme.GoldUnreadBookCount
import org.maddiesoftware.komagareader.core.data.local.ServerInfoSingleton
import org.maddiesoftware.komagareader.komga_server.presentaion.viewmodels.MainViewModule
import org.maddiesoftware.komagareader.komga_server.presentaion.componet.general.ExpandableText
import org.maddiesoftware.komagareader.komga_server.presentaion.componet.general.MyAsyncImage
import org.maddiesoftware.komagareader.komga_server.presentaion.viewmodels.SeriesByIdViewModel

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SeriesTabInfo(
    viewModel: SeriesByIdViewModel = hiltViewModel(),
    mainViewModule: MainViewModule = hiltViewModel(),
) {
    val serverInfo = ServerInfoSingleton
    val seriesInfo = viewModel.state.seriesInfo
    val libraryList = mainViewModule.state.libraryList
    var libraryName: String = ""
    var seriesYear: String = ""
    if (libraryList != null) {
        for (library in libraryList) {
            if (library.id == seriesInfo?.libraryId) {
                libraryName = library.name.toString()
            }
        }
    }
    if (seriesInfo != null) {
        if (seriesInfo.booksMetadata?.releaseDate != null) {

            seriesYear = seriesInfo.booksMetadata.releaseDate.toString().split("-")[0]
        }
    }
    ConstraintLayout(modifier = Modifier.fillMaxSize()) {
        val (
            seriesThumb, seriesLibrary, seriesTextYear, seriesBookCount, seriesStatus,
            seriesSummary, seriesPublisher, seriesPublisherChip, topSpacer, seriesTitle, topRightSpacer,
            imageChip,
        ) = createRefs()
        val endGuideLine = createGuidelineFromStart(256.dp)
        Log.d("komaga12345", "seriesid = ${seriesInfo?.id.toString()}")
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(40.dp)
                .constrainAs(topSpacer) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                }
//                .background(Color.Red)
        )
        MyAsyncImage(
            url = "${serverInfo.url}api/v1/series/${seriesInfo?.id}/thumbnail",
            modifier = Modifier
                .height(300.dp)
                .width(195.dp)
                .constrainAs(seriesThumb) {
                    start.linkTo(parent.start, 16.dp)
                    top.linkTo(topSpacer.bottom)
                }
        )
        if (seriesInfo?.booksUnreadCount != 0) {
            Chip(
                modifier = Modifier
                    .constrainAs(imageChip) {
//                              start.linkTo(seriesThumb.end, (-20).dp)
                        top.linkTo(seriesThumb.top, (-9).dp)
                        end.linkTo(seriesThumb.end, (-9).dp)
                    },
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
                    text = "${seriesInfo?.booksUnreadCount}",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
            }
        }
        Text(
            text = seriesInfo?.metadata?.title.toString(),
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp,
            modifier = Modifier
                .constrainAs(seriesTitle) {
                    start.linkTo(endGuideLine, 5.dp)
                    top.linkTo(topSpacer.bottom, 10.dp)
                    end.linkTo(parent.end, 15.dp)
                    width = Dimension.fillToConstraints
                }
        )
        Text(
            text = seriesYear,
            fontWeight = FontWeight.Normal,
            fontSize = 16.sp,
            modifier = Modifier
                .constrainAs(seriesTextYear) {
                    start.linkTo(endGuideLine, 5.dp)
                    top.linkTo(seriesTitle.bottom, 10.dp)
                    end.linkTo(parent.end, 15.dp)
                    width = Dimension.fillToConstraints
                }
        )
        Chip(
            onClick = { /*TODO*/ },
            modifier = Modifier.constrainAs(seriesStatus) {
                start.linkTo(endGuideLine,5.dp)
                top.linkTo(seriesTextYear.bottom, 8.dp)
            },
            shape = RectangleShape,
//            colors = ChipDefaults.chipColors(
//                backgroundColor = MaterialTheme.colors.background,
//                contentColor = Color.Red
//            ),
            ) {
            Text(
                text = "${seriesInfo?.metadata?.status}",
                color = MaterialTheme.colors.onSurface,
                fontWeight = FontWeight.Black,
                textAlign = TextAlign.Justify,
                )
        }
        Text(
            text = "${seriesInfo?.metadata?.totalBookCount} books",
            fontWeight = FontWeight.Normal,
            fontSize = 16.sp,
            modifier = Modifier
                .constrainAs(seriesBookCount) {
                    start.linkTo(endGuideLine, 5.dp)
                    top.linkTo(seriesStatus.bottom, 10.dp)
                    end.linkTo(parent.end, 15.dp)
                    width = Dimension.fillToConstraints
                }
        )



        Text(
            text = "PUBLISHER:",
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.constrainAs(seriesPublisher) {
                start.linkTo(parent.start, 16.dp)
                top.linkTo(seriesThumb.bottom, 16.dp)
            }
        )

        Chip(
            onClick = { /*TODO*/ },
            modifier = Modifier
                .constrainAs(seriesPublisherChip) {
                    start.linkTo(seriesPublisher.end, 10.dp)
                    top.linkTo(seriesThumb.bottom, 4.dp)
                }
                .border(3.dp, color = MaterialTheme.colors.onSurface)
            ,
            shape = RectangleShape,
            colors = ChipDefaults.chipColors(
                backgroundColor = MaterialTheme.colors.background,
                contentColor = Color.Red
            ),

        ) {
            Text(
                text = "${seriesInfo?.metadata?.publisher}",
                color = MaterialTheme.colors.onSurface,
                fontWeight = FontWeight.Black,
                textAlign = TextAlign.Justify,

                )

        }
        LazyColumn(
            modifier = Modifier.constrainAs(seriesSummary) {
                start.linkTo(parent.start, 16.dp)
                top.linkTo(seriesPublisher.bottom, 20.dp)
                end.linkTo(parent.end, 20.dp)
                width = Dimension.fillToConstraints
            }
        ){
          item {
              ExpandableText(
                  text = "Summary from book ${seriesInfo?.booksMetadata?.summaryNumber.toString()}:\n" +
                          "${seriesInfo?.booksMetadata?.summary}",
                  showMoreStyle = SpanStyle(
                      fontWeight = FontWeight.W500,
                      color = MaterialTheme.colors.primaryVariant,
                      fontStyle = FontStyle.Italic,
                      textDecoration = TextDecoration.Underline
                  ),
                  collapsedMaxLine = 5,
                  style = TextStyle(
                      color = MaterialTheme.colors.onSurface,
                      fontSize = 18.sp,
                      fontWeight = FontWeight.Normal,
                      textAlign = TextAlign.Justify,
                  )
              )
          }
        }



    }

}

//@Preview(showSystemUi = true)
//@Composable
//fun PrevSeriesTabInfo() {
//    val seriesInfo = Series(
//        booksCount = 25,
//        booksInProgressCount = 3,
//        booksMetadata = BookMetadataAggregation(
//            authors = listOf(Author("Stand Lee", "Writer")),
//            created = "March 5",
//            lastModified = "April 5",
//            releaseDate = "Sept",
//            summary = "You've seen Earth's Mightiest Heroes face off against a raging zombie virus - now, go back to the beginning in this special prelude issue of the epic manga adaptation! The Avengers risk their lives to make the world a safer place for all, oftentimes suffering severe injury and great loss. When Tony Stark faces a more personal threat - one leveled against Pepper Potts - the risk is too great to ignore, and he's forced to act fast. What lengths will Tony go to in order to keep Pepper safe? Iron Man is an important part of his life, but can he figure out a way to replace the armor and step away from being an Avenger, if that's what it takes? There are some things more menacing than monsters.\n",
//            summaryNumber = "1",
//            tags = null,
//
//            ),
//        booksReadCount = 3,
//        booksUnreadCount = 22,
//        created = null,
//        deleted = false,
//        fileLastModified = null,
//        id = "09YPSVSZX9Q1K",
//        lastModified = null,
//        libraryId = "09YPSVS759MM2",
//        metadata = SeriesMetadata(
//            created = null,
//            genres = null,
//            lastModified = null,
//            publisher = "Marvel",
//            readingDirection = null,
//            status = null,
//            summary = "",
//            tags = null,
//            title = "Zombies Assemble (2017)",
//            totalBookCount = null,
//        ),
//        name = "Zombies Assemble (Marvel)",
//        url = null
//    )
//    SeriesTabInfo()
//}
