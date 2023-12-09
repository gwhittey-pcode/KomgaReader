package org.maddiesoftware.komagareader.komga_server.presentaion.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.*
import androidx.compose.runtime.Composable
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
import org.maddiesoftware.komagareader.destinations.SeriesByIdScreenDestination
import org.maddiesoftware.komagareader.komga_server.presentaion.componet.general.PaginationStateHandler
import org.maddiesoftware.komagareader.komga_server.presentaion.componet.general.SeriesThumbCard
import org.maddiesoftware.komagareader.komga_server.presentaion.componet.general.WarningMessage
import org.maddiesoftware.komagareader.komga_server.presentaion.viewmodels.CollectionByIdViewModel


@Destination
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun CollectionByIdScreen(
    collectionId: String? = null,
    navigator: DestinationsNavigator,
    viewModel: CollectionByIdViewModel = hiltViewModel(),
    mainViewModel: MainViewModel,
) {
    mainViewModel.showTopBar.value = true
    val collectionState = viewModel.collectionState
        .collectAsLazyPagingItems()
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
            items(collectionState.itemCount) { i ->
                val series = collectionState[i]
                SeriesThumbCard(
                    url = "${serverInfo.url}api/v1/series/${series?.id}/thumbnail",
                    booksCount = series?.booksCount,
                    booksUnreadCount = series?.booksUnreadCount,
                    id = series?.id.toString(),
                    title = series?.metadata?.title,
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(),
                    onItemClick = { navigator.navigate(SeriesByIdScreenDestination(seriesId = series?.id)) }
                )
            }
            item {
                PaginationStateHandler(
                    paginationState = collectionState,
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
                                        .clickable(role = Role.Button) { collectionState.retry() },
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