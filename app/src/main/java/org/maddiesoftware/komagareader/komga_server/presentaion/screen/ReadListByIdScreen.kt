package org.maddiesoftware.komagareader.komga_server.presentaion.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import org.maddiesoftware.komagareader.R
import org.maddiesoftware.komagareader.core.data.local.ServerInfoSingleton
import org.maddiesoftware.komagareader.core.presentation.viewmodels.MainViewModel
import org.maddiesoftware.komagareader.destinations.BookInfoScreenDestination
import org.maddiesoftware.komagareader.komga_server.presentaion.componet.general.BookThumbCard
import org.maddiesoftware.komagareader.komga_server.presentaion.componet.general.PaginationStateHandler
import org.maddiesoftware.komagareader.komga_server.presentaion.componet.general.WarningMessage
import org.maddiesoftware.komagareader.komga_server.presentaion.viewmodels.LibraryViewModule
import org.maddiesoftware.komagareader.komga_server.presentaion.viewmodels.ReadListByIdViewModel

@OptIn(ExperimentalFoundationApi::class)
@Destination
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ReadListByIdScreen(
    readListId: String? = null,
    navigator: DestinationsNavigator,
    libraryViewModule: LibraryViewModule = hiltViewModel(),
    viewModel: ReadListByIdViewModel = hiltViewModel(),
    mainViewModel: MainViewModel,
) {
    mainViewModel.showTopBar.value = true
    val bookState = viewModel.bookState
        .collectAsLazyPagingItems()
    val libraryList = libraryViewModule.state.libraryList
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()
    val serverInfo = ServerInfoSingleton

    Column(
        modifier = Modifier
            .fillMaxSize()
//            .background(Color.White)
            .padding(16.dp)
    ) {
        Spacer(modifier = Modifier.height(height = 20.dp))
        Spacer(modifier = Modifier.width(8.dp))
        LazyVerticalGrid(
            columns = GridCells.Adaptive(155.dp),
            modifier = Modifier
                .fillMaxSize()
                .padding(5.dp)
        ) {
            items(bookState.itemCount) { i ->
                val book = bookState[i]
                BookThumbCard(
                    url = "${serverInfo.url}api/v1/books/${book?.id}/thumbnail",
                    book = book,
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(),
                    onItemClick = {
                        navigator.navigate(
                            BookInfoScreenDestination(
                                bookId = it,
                                groupType = "Read List",
                                readListId = readListId
                            )
                        )
                    }
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
        }
    }

}