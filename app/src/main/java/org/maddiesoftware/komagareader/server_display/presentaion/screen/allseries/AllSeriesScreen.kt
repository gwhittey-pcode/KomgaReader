package org.maddiesoftware.komagareader.server_display.presentaion.screen.allseries

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items

import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.launch
import org.maddiesoftware.komagareader.R
import org.maddiesoftware.komagareader.core.util.ServerInfoSingleton
import org.maddiesoftware.komagareader.core.util.ServerInfoSingleton.url
import org.maddiesoftware.komagareader.destinations.AllSeriesScreenDestination
import org.maddiesoftware.komagareader.destinations.HomeScreenDestination
import org.maddiesoftware.komagareader.server_display.domain.model.Series
import org.maddiesoftware.komagareader.server_display.presentaion.activity.MainViewModule
import org.maddiesoftware.komagareader.server_display.presentaion.componet.*

@SuppressLint("UnusedMaterialScaffoldPaddingParameter", "SuspiciousIndentation")
@Destination
@Composable
fun AllSeriesScreen(
    navigator: DestinationsNavigator,
    viewModel: AllSeriesViewModel = hiltViewModel(),
    mainViewModule: MainViewModule = hiltViewModel(),
    libraryId: String? = null,

){
    val serverInfo = ServerInfoSingleton
    val seriesState = viewModel.seriesState
        .collectAsLazyPagingItems()


    val libraryList = mainViewModule.state.libraryList
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()
    var newList:List<MenuItem> = emptyList()
        if (libraryList != null) {
            newList =  libraryList.map { it.toMenuItem() }
        }
        Scaffold(
            scaffoldState = scaffoldState,
            topBar = {
                NavBar(
                    onNavigationIconClick = {
                        Log.d("Komga12345","Open Nav")
                        scope.launch {
                            scaffoldState.drawerState.open()
                        }
                    }
                )
            },
            drawerGesturesEnabled = scaffoldState.drawerState.isOpen,
            drawerContent = {
                DrawerHeader()
                DrawerBodySelectionScreen(
                    items = newList,
                    onItemClick = {
                        if (it == "home"){
                            navigator.navigate(HomeScreenDestination())
                        }else {
                            navigator.navigate(AllSeriesScreenDestination(libraryId = it))
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
                Row() {
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
                    columns = GridCells.Adaptive(200.dp),
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(5.dp)
                ) {
                    items(seriesState.itemCount) { i ->
                        val series = seriesState[i]
                        SeriesThumbCard(
                            url = "${serverInfo.url}api/v1/series/${series?.id}/thumbnail",
                            series = series,
                            modifier = Modifier
                                .padding(16.dp)
                                .fillMaxWidth()
                        )

                    }
                    item {
                        PaginationStateHandler(
                            paginationState = seriesState,
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
                                            modifier = Modifier.padding(start = 3.dp)
                                                .clickable(role = Role.Button) { seriesState.retry() },
                                            textDecoration = TextDecoration.Underline,
                                            fontWeight = FontWeight.Bold,
                                            color = MaterialTheme.colors.onBackground
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

@Composable
fun TvShowItem(series: Series , modifier: Modifier, onClick: () -> Unit) {
    Text(text = series.name)
}
