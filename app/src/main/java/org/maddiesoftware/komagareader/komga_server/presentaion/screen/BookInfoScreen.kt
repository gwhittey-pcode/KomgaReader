package org.maddiesoftware.komagareader.komga_server.presentaion.screen

import android.annotation.SuppressLint
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
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
import org.maddiesoftware.komagareader.core.presentation.viewmodels.MainViewModel
import org.maddiesoftware.komagareader.destinations.BookInfoScreenDestination
import org.maddiesoftware.komagareader.destinations.BookReaderScreenDestination
import org.maddiesoftware.komagareader.komga_server.domain.model.TriangleShape
import org.maddiesoftware.komagareader.komga_server.presentaion.componet.general.ExpandableText
import org.maddiesoftware.komagareader.komga_server.presentaion.componet.general.MyAsyncImage
import org.maddiesoftware.komagareader.komga_server.presentaion.viewmodels.BookInfoViewModel
import org.maddiesoftware.komagareader.komga_server.presentaion.viewmodels.LibraryViewModule
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
    libraryViewModule: LibraryViewModule = hiltViewModel(),
    navigator: DestinationsNavigator,
    mainViewModel: MainViewModel
) {
    mainViewModel.showTopBar.value = true
    val serverInfo = ServerInfoSingleton
    val bookInfo = viewModel.state.bookInfo
    val nextBookInfo = viewModel.state.nextBookInfo
    val prevBookInfo = viewModel.state.prevBookInfo
    val context = LocalContext.current

    var date = ""
    if (bookInfo?.metadata?.releaseDate != null) {
        date = genMonth(bookInfo.metadata.releaseDate.toString())

    }
    mainViewModel.topBarTitle.value =
        "#${bookInfo?.number.toString()} ${bookInfo?.seriesTitle.toString()}"
//    val formattedDate  =  date.format(formatter)
    if (bookInfo != null) {
        ConstraintLayout(modifier = Modifier.fillMaxSize()) {
            val (
                bookThumb, bookSeriesTitle, bookReadTag, bookTitle, bookNumber, bookPageCount, bookReleaseDate,
                topSpacer, bookSummary, floatingReadActionButton, nextPrevBookNavBox, floatingDownloadActionButton
            ) = createRefs()
            val endGuideLine = createGuidelineFromStart(256.dp)
            val centerGuideline = createGuidelineFromStart(.5f)
            val hasPrevBook = viewModel.hasPrevBook
            val hasNextBook = viewModel.hasNextBook
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(40.dp)
                    .constrainAs(topSpacer) {
                        top.linkTo(parent.top)

                    }
//                .background(Color.Red)
            )
            Row(modifier = Modifier
                .constrainAs(nextPrevBookNavBox) {
                    top.linkTo(parent.top, 5.dp)
                    start.linkTo(parent.start, 20.dp)

                }
                .fillMaxWidth(.3f),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                Log.d("Bookinfo","hasPrevBook = $hasPrevBook")
                IconButton(
                    onClick = {
                        if (hasPrevBook) {
                            navigator.navigate(
                                BookInfoScreenDestination(
                                    bookId = prevBookInfo?.id,
                                    groupType = groupType,
                                    readListId = readListId
                                )
                            )
                        }else{
                            Toast.makeText(context, "At First book", Toast.LENGTH_LONG).show()
                        }

                    }) {

                    Icon(
                        imageVector = Icons.Default.ChevronLeft,
                        contentDescription = "Toggle drawer"
                    )
                }

                IconButton(
                    onClick = {

                        if (hasNextBook) {
                            Log.d("BookInfo", "hasNextBook if  = ${hasNextBook}) ")
                            navigator.navigate(
                                BookInfoScreenDestination(
                                    bookId = nextBookInfo?.id,
                                    groupType = groupType,
                                    readListId = readListId
                                )
                            )
                        }else{
                            Toast.makeText(context, "At Last book", Toast.LENGTH_LONG).show()
                        }
                    }) {
                    Icon(
                        imageVector = Icons.Default.ChevronRight,
                        contentDescription = "Toggle drawer"
                    )
                }


            }
            MyAsyncImage(
                url = "${serverInfo.url}api/v1/books/${bookInfo.id}/thumbnail",
                modifier = Modifier
                    .height(300.dp)
                    .width(195.dp)
                    .constrainAs(bookThumb) {
                        start.linkTo(parent.start, 16.dp)
                        top.linkTo(topSpacer.bottom, 20.dp)
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
                text = "${bookInfo.seriesTitle.toString()} # ${bookInfo.number.toString()}",
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                modifier = Modifier
                    .constrainAs(bookSeriesTitle) {
                        start.linkTo(endGuideLine, 5.dp)
                        top.linkTo(topSpacer.bottom, 20.dp)
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
                text = "${bookInfo.readProgress?.page ?: 0} of ${bookInfo.media?.pagesCount.toString()} Pages Read -" +
                        "   $date   ",
                fontWeight = FontWeight.Normal,
                fontSize = 15.sp,
                modifier = Modifier
                    .constrainAs(bookPageCount) {
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
                modifier = Modifier.constrainAs(floatingReadActionButton) {
                    bottom.linkTo(parent.bottom, 30.dp)
                    end.linkTo(parent.end, 30.dp)

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
            FloatingActionButton(//Floating Action Button for Downloading comic
                modifier = Modifier.constrainAs(floatingDownloadActionButton) {
                    bottom.linkTo(parent.bottom, 30.dp)
                    start.linkTo(parent.start, 30.dp)

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
                shape = RectangleShape
            ) {
                Row(modifier = Modifier.width(225.dp)) {
                    Spacer(modifier = Modifier.width(18.dp))
                    Icon(
                        imageVector = Icons.Default.Download,
                        contentDescription = "fab_add_order",
                        tint = MaterialTheme.colors.onSurface
                    )
                    Spacer(modifier = Modifier.width(18.dp))
                    Text(
                        text = "Download Issue",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Normal
                    )

                }

            }
        }
    }
}

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
