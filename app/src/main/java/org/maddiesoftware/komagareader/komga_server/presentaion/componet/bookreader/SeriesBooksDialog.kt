package org.maddiesoftware.komagareader.komga_server.presentaion.componet.bookreader

import android.annotation.SuppressLint
import android.content.Context.MODE_PRIVATE
import android.util.Log
import android.view.Gravity
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.compose.ui.window.DialogWindowProvider
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import com.ramcosta.composedestinations.spec.DestinationStyle
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import org.maddiesoftware.komagareader.R
import org.maddiesoftware.komagareader.core.data.local.ServerInfoSingleton
import org.maddiesoftware.komagareader.core.presentation.theme.GoldUnreadBookCount
import org.maddiesoftware.komagareader.komga_server.presentaion.componet.general.BookThumbCard
import org.maddiesoftware.komagareader.komga_server.presentaion.componet.general.PaginationStateHandler
import org.maddiesoftware.komagareader.komga_server.presentaion.componet.general.WarningMessage
import org.maddiesoftware.komagareader.komga_server.presentaion.viewmodels.SeriesByIdViewModel

@SuppressLint("CoroutineCreationDuringComposition")
@OptIn(ExperimentalComposeUiApi::class, FlowPreview::class)
@Composable
fun SeriesBooksDialog(
    currentBookNumber: Int,
    setShowDialog: (Boolean) -> Unit,
    screenWidth: Dp,
    onItemClick: (id: String) -> Unit,
) {
    val viewModel: SeriesByIdViewModel = hiltViewModel()
    val serverInfo = ServerInfoSingleton
    val bookState = viewModel.bookState
        .collectAsLazyPagingItems()
    val seriesInfo = viewModel.state
    var currentBookIndex by remember {
        mutableIntStateOf(0)
    }
    var boxColor: androidx.compose.ui.graphics.Color = MaterialTheme.colors.surface

    Log.d("BookCount", "before currentBookNumber = $currentBookNumber")
    if (currentBookNumber != 0) currentBookIndex = currentBookNumber.minus(1)
    Log.d("komga1", " $currentBookNumber")
    val context = LocalContext.current
    val prefs by lazy {
        context.getSharedPreferences("prefs", MODE_PRIVATE)
    }
    if (currentBookNumber != 0) currentBookIndex = currentBookNumber.minus(1)
    val scrollPosition = currentBookNumber -1
    val lazyGridState = rememberLazyListState(
        initialFirstVisibleItemIndex = scrollPosition
    )

    LaunchedEffect(lazyGridState) {
        lazyGridState.scrollToItem(currentBookIndex)
        snapshotFlow {
            lazyGridState.firstVisibleItemIndex
        }
            .debounce(500L)
            .collectLatest { index ->
                Log.d("BookState1","scrollPosition=$scrollPosition currentBookNumber=$currentBookNumber")
                prefs.edit()
                    .putInt("scroll_position", index)
                    .apply()
            }

    }


//    LaunchedEffect(true) {
//        Log.d("komga1", "Launch Scrollto")
//            Log.d("komga1", " $currentBookNumber")
//            scrollState.scrollToItem(currentBookIndex)
//    }

    Log.d(
        "BookCount",
        "after currentBookNumber = $currentBookNumber  bookState.itemCount=${bookState.itemCount} seriesCount = ${seriesInfo.seriesInfo?.booksCount}"
    )

    Dialog(
        onDismissRequest = { setShowDialog(false) },
        properties = DestinationStyle.Dialog.properties.let {
            DialogProperties(
                dismissOnBackPress = it.dismissOnBackPress,
                dismissOnClickOutside = it.dismissOnClickOutside,
                securePolicy = it.securePolicy,
                usePlatformDefaultWidth = false
            )
        },
    ) {
        val dialogWindowProvider = LocalView.current.parent as DialogWindowProvider
        dialogWindowProvider.window.setGravity(Gravity.TOP)

        Surface(
            shape = RoundedCornerShape(16.dp),
            color = androidx.compose.ui.graphics.Color.White
        ) {
            when (bookState.itemCount) {
                0 -> CircularProgressIndicator()
                else -> {
                    LazyRow (
                        modifier = Modifier
                            .width(screenWidth.minus(20.dp))
                            .height(280.dp),
                        state = lazyGridState,

                    )

                    {
                        items(
                            bookState.itemCount,

                        ) { i ->
                            val book = bookState[i]
                            boxColor = if (currentBookIndex == i) {
                                GoldUnreadBookCount
                            } else {
                                MaterialTheme.colors.surface
                            }
                            BookThumbCard(
                                url = "${serverInfo.url}api/v1/books/${book?.id}/thumbnail",
                                boxColor = boxColor,
                                book = book,
                                modifier = Modifier
                                    .padding(16.dp)
                                    .fillMaxWidth(),
                                onItemClick = { onItemClick(book?.id.toString()) }
                            )

                        }
                        item {
                            PaginationStateHandler(
                                paginationState = bookState,
                                loadingComponent = {
                                    Box(
                                        modifier = Modifier.fillMaxWidth(),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        CircularProgressIndicator()
                                    }
                                },
                                errorComponent = {
                                    WarningMessage(
                                        text = stringResource(id = R.string.err_loading_string),
                                        trailingContent = {
                                            Text(
                                                text = stringResource(id = R.string.lbl_retry),
                                                modifier = Modifier
                                                    .padding(start = 3.dp)
                                                    .clickable(role = Role.Button) { bookState.retry() },
                                                textDecoration = TextDecoration.Underline,
                                                fontWeight = FontWeight.Bold,
                                                color = MaterialTheme.colors.onSecondary,
                                            )
                                        }
                                    )
                                }
                            )
                        }


//                        composableScope.launch {
//                            scrollState.scrollToItem(currentBookIndex)
//
//                        }
                    }
                    Spacer(modifier = Modifier.height(25.dp) )

                }
            }
        }
    }
}



