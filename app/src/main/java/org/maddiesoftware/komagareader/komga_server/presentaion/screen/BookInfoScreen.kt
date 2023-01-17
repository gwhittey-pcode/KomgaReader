package org.maddiesoftware.komagareader.komga_server.presentaion.screen

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import org.maddiesoftware.komagareader.core.data.local.ServerInfoSingleton
import org.maddiesoftware.komagareader.core.presentation.theme.GoldUnreadBookCount
import org.maddiesoftware.komagareader.destinations.BookReaderScreenDestination
import org.maddiesoftware.komagareader.komga_server.domain.model.TriangleShape
import org.maddiesoftware.komagareader.komga_server.presentaion.componet.general.ExpandableText
import org.maddiesoftware.komagareader.komga_server.presentaion.componet.general.MyAsyncImage
import org.maddiesoftware.komagareader.komga_server.presentaion.viewmodels.BookInfoViewModel
import org.maddiesoftware.komagareader.komga_server.presentaion.viewmodels.MainViewModule
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterialApi::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Destination
@Composable
fun BookInfoScreen(
    bookId: String? = null,
    groupType: String? = null,
    readListId: String? = null,
    viewModel: BookInfoViewModel = hiltViewModel(),
    mainViewModule: MainViewModule = hiltViewModel(),
    navigator: DestinationsNavigator,
) {
    val serverInfo = ServerInfoSingleton
    val bookInfo = viewModel.state.bookInfo
    val date = bookInfo?.metadata?.releaseDate

//    val formattedDate  =  date.format(formatter)
    if (bookInfo != null) {

            ConstraintLayout(modifier = Modifier.fillMaxSize()) {
                val (
                    bookThumb, bookSeriesTitle, bookReadTag, bookTitle, bookNumber, bookPageCount, bookReleaseDate,
                    topSpacer, bookSummary, floatingActionButton
                ) = createRefs()
                val endGuideLine = createGuidelineFromStart(256.dp)
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
                    url = "${serverInfo.url}api/v1/books/${bookInfo.id}/thumbnail",
                    modifier = Modifier
                        .height(300.dp)
                        .width(195.dp)
                        .constrainAs(bookThumb) {
                            start.linkTo(parent.start, 16.dp)
                            top.linkTo(topSpacer.bottom)
                        }
                )
                if (bookInfo.readProgress?.completed == false || bookInfo.readProgress === null) {
                    Chip(
                        modifier = Modifier
                            .size(30.dp)
                            .constrainAs(bookReadTag) {
                                end.linkTo(bookThumb.end)
                                top.linkTo(bookThumb.top)
                            },
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
                Text(
                    text = bookInfo.seriesTitle.toString(),
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    modifier = Modifier
                        .constrainAs(bookSeriesTitle) {
                            start.linkTo(endGuideLine, 5.dp)
                            top.linkTo(topSpacer.bottom, 10.dp)
                            end.linkTo(parent.end, 15.dp)
                            width = Dimension.fillToConstraints
                        }
                )
                Text(
                    text = bookInfo.metadata?.title.toString(),
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 22.sp,
                    modifier = Modifier
                        .constrainAs(bookTitle) {
                            start.linkTo(endGuideLine, 5.dp)
                            top.linkTo(bookSeriesTitle.bottom, 10.dp)
                            end.linkTo(parent.end, 15.dp)
                            width = Dimension.fillToConstraints
                        }
                )
                Text(
                    text = "${bookInfo.readProgress?.page?:0} of ${bookInfo.media?.pagesCount.toString()} Pages Read -" +
                            "    ${genMonth(date.toString())}   ",
                    fontWeight = FontWeight.Normal,
                    fontSize = 15.sp,
                    modifier = Modifier
                        .constrainAs(bookNumber) {
                            start.linkTo(endGuideLine, 5.dp)
                            top.linkTo(bookTitle.bottom, 10.dp)
                            end.linkTo(parent.end, 15.dp)
                            width = Dimension.fillToConstraints
                        }
                )
                LazyColumn(
                    modifier = Modifier.constrainAs(bookSummary) {
                        start.linkTo(parent.start, 16.dp)
                        top.linkTo(bookThumb.bottom, 20.dp)
                        end.linkTo(parent.end, 20.dp)
                        width = Dimension.fillToConstraints
                    }
                ) {
                    item {
                        ExpandableText(
                            text = "${bookInfo.metadata?.summary}",
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
                FloatingActionButton(
                    modifier = Modifier.constrainAs(floatingActionButton){
                        bottom.linkTo(parent.bottom,30.dp)
                        end.linkTo(parent.end,30.dp)

                    },
                    onClick = {
                        navigator.navigate(
                            BookReaderScreenDestination(
                                bookId = bookInfo.id.toString(),
                                seriesId = bookInfo.seriesId.toString(),
                                groupType = groupType,
                                readListId = readListId
                            )
                        )
                    },
                    backgroundColor = MaterialTheme.colors.primary,
                    shape = CircleShape
                ) {
                    Row(modifier = Modifier.width(125.dp)) {
                        Spacer(modifier = Modifier.width(16.dp))
                        Icon(
                            imageVector = Icons.Default.MenuBook,
                            contentDescription = "fab_add_order",
                            tint = MaterialTheme.colors.onSurface
                        )
                        Spacer(modifier = Modifier.width(16.dp))
                        Text(
                            text = "Read",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Normal
                        )

                    }

                }
            }
        }
}

@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("SimpleDateFormat")
fun genMonth(pubDate: String): String {
    val formatter = SimpleDateFormat("yyyy-MM-dd")
    val current = formatter.parse(pubDate)
    val simpleDateFormat = SimpleDateFormat("MMMM dd, yyyy")
    return simpleDateFormat.format(current as Date).toString()


}


fun genDay(date: String) {

}

fun genYear(date: String) {

}
