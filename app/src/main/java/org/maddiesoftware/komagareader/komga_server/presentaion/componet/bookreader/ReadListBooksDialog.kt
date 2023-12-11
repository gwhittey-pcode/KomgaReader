package org.maddiesoftware.komagareader.komga_server.presentaion.componet.bookreader

import android.content.Context
import android.util.Log
import android.view.Gravity
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
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
import androidx.compose.ui.graphics.Color
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
import kotlinx.coroutines.launch
import org.maddiesoftware.komagareader.R
import org.maddiesoftware.komagareader.core.data.local.ServerInfoSingleton
import org.maddiesoftware.komagareader.core.presentation.theme.GoldUnreadBookCount
import org.maddiesoftware.komagareader.komga_server.presentaion.componet.general.BookThumbCard
import org.maddiesoftware.komagareader.komga_server.presentaion.componet.general.PaginationStateHandler
import org.maddiesoftware.komagareader.komga_server.presentaion.componet.general.WarningMessage
import org.maddiesoftware.komagareader.komga_server.presentaion.viewmodels.ReadListByIdViewModel

@OptIn(ExperimentalComposeUiApi::class, FlowPreview::class)
@Composable
fun ReadListBooksDialog(
    setShowDialog: (Boolean) -> Unit,
    screenWidth:Dp,
    onItemClick: (id: String) -> Unit,
    currentBookId: String
    ) {
    val viewModel: ReadListByIdViewModel = hiltViewModel()
    val serverInfo = ServerInfoSingleton
    val bookState = viewModel.bookState
        .collectAsLazyPagingItems()
    val composableScope = rememberCoroutineScope()
    val scrollState = LazyGridState()
    var currentBookIndex by remember() {
        mutableStateOf(0)
    }
    var boxColor: Color = MaterialTheme.colors.surface
    val context = LocalContext.current
    val prefs by lazy {
        context.getSharedPreferences("prefs", Context.MODE_PRIVATE)
    }
    currentBookIndex = viewModel.calculateBookIndexInReadList(currentBookId)
    Log.d("komga1", " $currentBookId")
    val scrollPosition =currentBookIndex
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
                Log.d("BookState1","scrollPosition=$scrollPosition currentBookNumber=$currentBookIndex")
                prefs.edit()
                    .putInt("scroll_position", index)
                    .apply()
            }

    }
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
            color = Color.White
        ) {
            LazyRow (
                modifier = Modifier
                    .width(screenWidth.minus(20.dp))
                    .height(280.dp),
                state = lazyGridState,

                )
            {
                items(bookState.itemCount) { i ->
                    val book = bookState[i]
                    boxColor = if (currentBookIndex == i) {
                        GoldUnreadBookCount
                    }else{
                        MaterialTheme.colors.surface
                    }
                    BookThumbCard(
                        url = "${serverInfo.url}api/v1/books/${book?.id}/thumbnail",
                        book = book,
                        boxColor = boxColor,
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxWidth(),
                        onItemClick = {onItemClick(book?.id.toString())}
                    )
                }
                item {
                    PaginationStateHandler(
                        paginationState = bookState,
                        loadingComponent = {
                            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                                CircularProgressIndicator()
                            }
                        },
                        errorComponent = {
                            WarningMessage(
                                text = stringResource(id = R.string.err_loading_string),
                                trailingContent = {
                                    Text(
                                        text  = stringResource(id = R.string.lbl_retry),
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
            }
        }
    }
}
