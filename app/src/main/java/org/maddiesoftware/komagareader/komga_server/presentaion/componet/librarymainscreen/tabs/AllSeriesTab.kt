package org.maddiesoftware.komagareader.komga_server.presentaion.componet.librarymainscreen.tabs

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.*
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
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import org.maddiesoftware.komagareader.R
import org.maddiesoftware.komagareader.core.data.local.ServerInfoSingleton
import org.maddiesoftware.komagareader.destinations.*
import org.maddiesoftware.komagareader.komga_server.presentaion.componet.general.*
import org.maddiesoftware.komagareader.komga_server.presentaion.viewmodels.AllSeriesViewModel
import org.maddiesoftware.komagareader.komga_server.presentaion.viewmodels.LibraryViewModule

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun AllSeriesTab(
    navigator: DestinationsNavigator,
    viewModel: AllSeriesViewModel = hiltViewModel(),
    libraryViewModule: LibraryViewModule = hiltViewModel(),
    libraryId: String? = null,
) {
    val serverInfo = ServerInfoSingleton
    val seriesState = viewModel.seriesState
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
        LazyVerticalGrid(
            columns = GridCells.Adaptive(155.dp),
            modifier = Modifier
                .fillMaxSize()
                .padding(5.dp)
        ) {
            items(seriesState.itemCount) { i ->
                val series = seriesState[i]
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
                    paginationState = seriesState,
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
                                        .clickable(role = Role.Button) { seriesState.retry() },
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