package org.maddiesoftware.komagareader.komga_server.presentaion.screen

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
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import org.maddiesoftware.komagareader.R
import org.maddiesoftware.komagareader.core.data.local.ServerInfoSingleton
import org.maddiesoftware.komagareader.core.presentation.viewmodels.MainViewModel
import org.maddiesoftware.komagareader.destinations.CollectionByIdScreenDestination
import org.maddiesoftware.komagareader.komga_server.presentaion.componet.general.CollectionThumbCard
import org.maddiesoftware.komagareader.komga_server.presentaion.componet.general.PaginationStateHandler
import org.maddiesoftware.komagareader.komga_server.presentaion.componet.general.WarningMessage
import org.maddiesoftware.komagareader.komga_server.presentaion.viewmodels.AllCollectionsViewModel
import org.maddiesoftware.komagareader.komga_server.presentaion.viewmodels.LibraryViewModule

@Destination
@Composable
fun AllCollectionsScreen(
    navigator: DestinationsNavigator,
    viewModel: AllCollectionsViewModel = hiltViewModel(),
    libraryViewModule: LibraryViewModule = hiltViewModel(),
    libraryId: String? = null,
    mainViewModel: MainViewModel = hiltViewModel()
) {
    mainViewModel.showTopBar.value = true
    val serverInfo = ServerInfoSingleton
    val collectionState = viewModel.collectionState
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
            items(collectionState.itemCount) { i ->
                val collection = collectionState[i]
                CollectionThumbCard(
                    url = "${serverInfo.url}api/v1/collections/${collection?.id}/thumbnail",
                    seriesCount = collection?.seriesIds?.size,
                    id = collection?.id.toString(),
                    name = collection?.name,
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(),
                    onItemClick = { navigator.navigate(CollectionByIdScreenDestination(collectionId = collection?.id)) }
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