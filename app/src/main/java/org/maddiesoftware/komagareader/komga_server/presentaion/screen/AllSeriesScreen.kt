package org.maddiesoftware.komagareader.komga_server.presentaion.screen

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
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.launch
import org.maddiesoftware.komagareader.R
import org.maddiesoftware.komagareader.core.data.local.ServerInfoSingleton
import org.maddiesoftware.komagareader.destinations.AllSeriesScreenDestination
import org.maddiesoftware.komagareader.destinations.HomeScreenDestination
import org.maddiesoftware.komagareader.destinations.SeriesByIdScreenDestination
import org.maddiesoftware.komagareader.destinations.SettingsScreenDestination
import org.maddiesoftware.komagareader.komga_server.presentaion.componet.general.*
import org.maddiesoftware.komagareader.komga_server.presentaion.viewmodels.AllSeriesViewModel
import org.maddiesoftware.komagareader.komga_server.presentaion.viewmodels.MainViewModule

@SuppressLint("UnusedMaterialScaffoldPaddingParameter", "SuspiciousIndentation")
@Destination
@Composable
fun AllSeriesScreen(
    navigator: DestinationsNavigator,
    viewModel: AllSeriesViewModel = hiltViewModel(),
    mainViewModule: MainViewModule = hiltViewModel(),
    libraryId: String? = null,

    ) {
    val serverInfo = ServerInfoSingleton
    val seriesState = viewModel.seriesState
        .collectAsLazyPagingItems()


    val libraryList = mainViewModule.state.libraryList
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()
    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            NavBar(
                onNavigationIconClick = { navigator.navigateUp() },
                onMenuItemClick = {
                    scope.launch {
                        scaffoldState.drawerState.open()
                    }
                }
            )
        },
        drawerGesturesEnabled = scaffoldState.drawerState.isOpen,
        drawerContent = {
            NavDrawer(
                libraryList = libraryList,
                onItemClick = { id ->
                    scope.launch {
                        scaffoldState.drawerState.close()
                    }
                    when (id) {
                        "home" -> {
                            navigator.navigate(HomeScreenDestination())
                        }
                        "settings" -> {
                            navigator.navigate(SettingsScreenDestination())
                        }
                        else -> {
                            navigator.navigate(AllSeriesScreenDestination(libraryId = id))
                        }
                    }
                }
            )
        }
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(16.dp)
        ) {


            Spacer(modifier = Modifier.height(height = 20.dp))
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
}

