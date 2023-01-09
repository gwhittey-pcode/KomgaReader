package org.maddiesoftware.komagareader.server_display.presentaion.screen.seriesbyid.tabs

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import androidx.paging.compose.collectAsLazyPagingItems
import org.maddiesoftware.komagareader.R
import org.maddiesoftware.komagareader.core.data.local.ServerInfoSingleton
import org.maddiesoftware.komagareader.server_display.presentaion.componet.BookThumbCard
import org.maddiesoftware.komagareader.server_display.presentaion.componet.PaginationStateHandler
import org.maddiesoftware.komagareader.server_display.presentaion.componet.WarningMessage
import org.maddiesoftware.komagareader.server_display.presentaion.screen.seriesbyid.SeriesByIdViewModel

@Composable
fun BooksTab(
    viewModel: SeriesByIdViewModel = hiltViewModel(),
    onItemClick: (id: String) -> Unit,
    ){
    val navController = rememberNavController()
    val serverInfo = ServerInfoSingleton
    val bookState = viewModel.bookState
        .collectAsLazyPagingItems()
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
                    onItemClick = onItemClick
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
                            text = stringResource(id = R.string.err_loading_series),
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