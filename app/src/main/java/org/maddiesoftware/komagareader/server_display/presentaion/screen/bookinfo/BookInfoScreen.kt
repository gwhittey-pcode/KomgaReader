package org.maddiesoftware.komagareader.server_display.presentaion.screen.bookinfo

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import org.maddiesoftware.komagareader.core.util.ServerInfoSingleton
import org.maddiesoftware.komagareader.server_display.presentaion.activity.MainViewModule
import org.maddiesoftware.komagareader.server_display.presentaion.componet.MyAsyncImage

@Destination
@Composable
fun BookInfoScreen(
    bookId: String? = null,
    viewModel: BookInfoViewModel = hiltViewModel(),
    mainViewModule: MainViewModule = hiltViewModel(),
){
    val serverInfo = ServerInfoSingleton
    val bookInfo = viewModel.state.bookInfo
    ConstraintLayout(modifier = Modifier.fillMaxSize()) {
        val (
            bookThumb, bookSeriesTitle, bookTitle, bookNumber, bookPageCount, bookReleaseDate,
            BookSummary, topSpacer
        )  = createRefs()
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
            url = "${serverInfo.url}api/v1/books/${bookInfo?.id}/thumbnail",
            modifier = Modifier
                .height(300.dp)
                .width(195.dp)
                .constrainAs(bookThumb) {
                    start.linkTo(parent.start, 16.dp)
                    top.linkTo(topSpacer.bottom)
                }
        )
    }
}