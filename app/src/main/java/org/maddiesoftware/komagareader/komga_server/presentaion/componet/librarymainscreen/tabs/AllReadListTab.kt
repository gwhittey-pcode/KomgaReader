package org.maddiesoftware.komagareader.komga_server.presentaion.componet.librarymainscreen.tabs

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import org.maddiesoftware.komagareader.R
import org.maddiesoftware.komagareader.core.data.local.ServerInfoSingleton
import org.maddiesoftware.komagareader.core.presentation.viewmodels.MainViewModel
import org.maddiesoftware.komagareader.komga_server.presentaion.componet.general.PaginationStateHandler
import org.maddiesoftware.komagareader.komga_server.presentaion.componet.general.ReadListThumbCard
import org.maddiesoftware.komagareader.komga_server.presentaion.componet.general.WarningMessage
import org.maddiesoftware.komagareader.komga_server.presentaion.viewmodels.AllReadListViewModel
import org.maddiesoftware.komagareader.komga_server.presentaion.viewmodels.LibraryViewModule

@Composable
fun AllReadListTab (
    onItemClick: (id: String) -> Unit,
    viewModel: AllReadListViewModel = hiltViewModel(),
    libraryViewModule: LibraryViewModule = hiltViewModel(),
    libraryId: String? = null,
    mainViewModel: MainViewModel = hiltViewModel(),
) {
    mainViewModel.showTopBar.value = true
    val serverInfo = ServerInfoSingleton
    val readListState = viewModel.readListState
        .collectAsLazyPagingItems()
    val libraryList = libraryViewModule.state.libraryList

    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()

    Column(
    modifier = Modifier
        .fillMaxSize()
        .background(Color.White)
        .padding(16.dp)
    ) {
        Row {
//                    Text(
//                        text = stringResource(id = R.string.recently_add_series),
//                        color = Color.Black,
//                        style = MaterialTheme.typography.h4,
////                        color = MaterialTheme.colors.onBackground,
//                        modifier = Modifier.padding(vertical = 10.dp)
//                    )
        }
        Spacer(modifier = Modifier.width(8.dp))
        LazyVerticalGrid(
            columns = GridCells.Adaptive(155.dp),
            modifier = Modifier
                .fillMaxSize()
                .padding(5.dp)
        ) {
            items(readListState.itemCount) { i ->
                val readList = readListState[i]
                ReadListThumbCard(
                    url = "${serverInfo.url}api/v1/readlists/${readList?.id}/thumbnail",
                    booksCount = readList?.bookIds?.size,
                    id = readList?.id.toString(),
                    title = readList?.name,
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(),
                    onItemClick = onItemClick
                )

            }
            item {
                PaginationStateHandler(
                    paginationState = readListState,
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
                                        .clickable(role = Role.Button) { readListState.retry() },
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