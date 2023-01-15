package org.maddiesoftware.komagareader.komga_server.presentaion.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavBackStackEntry
import androidx.paging.compose.collectAsLazyPagingItems
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.launch
import org.maddiesoftware.komagareader.R
import org.maddiesoftware.komagareader.core.data.local.ServerInfoSingleton
import org.maddiesoftware.komagareader.destinations.*
import org.maddiesoftware.komagareader.komga_server.presentaion.componet.*
import org.maddiesoftware.komagareader.komga_server.presentaion.componet.general.NavBar
import org.maddiesoftware.komagareader.komga_server.presentaion.componet.general.NavDrawer
import org.maddiesoftware.komagareader.komga_server.presentaion.componet.general.SeriesThumbCard
import org.maddiesoftware.komagareader.komga_server.presentaion.viewmodels.HomeViewModule
import org.maddiesoftware.komagareader.komga_server.presentaion.viewmodels.MainViewModule

@Destination
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun HomeScreen(

    navigator: DestinationsNavigator,
    navBackStackEntry: NavBackStackEntry,
    viewModel: HomeViewModule = hiltViewModel(),
    mainViewModule: MainViewModule = hiltViewModel()
//    lastUpdated: Resource<PageSeries>,

) {
    val state = viewModel.state
    val libraryList = mainViewModule.state.libraryList
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()
    val serverInfo = ServerInfoSingleton
    val keepReadingState = viewModel.keepReadingState
        .collectAsLazyPagingItems()
    val onDeckBooksState = viewModel.onDeckBooksState
        .collectAsLazyPagingItems()
    val recentlyAddedBooks = viewModel.recentlyAddedBooks
        .collectAsLazyPagingItems()
    val newSeries = viewModel.newSeries
        .collectAsLazyPagingItems()
    val updatedSeries = viewModel.updatedSeries
        .collectAsLazyPagingItems()
    if (state.error == null) {
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
                        when(id){
                            "home" -> {navigator.navigate(HomeScreenDestination())}
                            "settings" -> {navigator.navigate(SettingsScreenDestination())}
                            else -> {navigator.navigate(AllSeriesScreenDestination(libraryId = id))}
                        }
                    }
                )

            }
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
//            .background(Color.White)
                    .padding(16.dp)
            ) {

                //----Keep Reading start
                item{
                    Spacer(modifier = Modifier.height(height = 20.dp))
                    Row {
                        Text(
                            text = stringResource(id = R.string.keep_reading),
                            color = Color.Black,
                            style = MaterialTheme.typography.h4,
//                        color = MaterialTheme.colors.onBackground,
                            modifier = Modifier.padding(vertical = 10.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                }
                item {
                    LazyRow {//Start Keep Reading items LazyRow
                        items(keepReadingState.itemCount) { i ->
                            val book = keepReadingState[i]
                            BookThumbCard(
                                url = "${serverInfo.url}api/v1/books/${book?.id}/thumbnail",
                                book = book,
                                modifier = Modifier
                                    .padding(16.dp)
                                    .fillMaxWidth(),
                                onItemClick = {navigator.navigate(BookInfoScreenDestination(bookId = it))}
                            )
                        }
                    }
                }//End Keep Reading items LazyRo

                item{//-On Deck Start
                    Spacer(modifier = Modifier.height(height = 20.dp))
                    Row() {
                        Text(
                            text = stringResource(id = R.string.on_deck),
                            color = Color.Black,
                            style = MaterialTheme.typography.h4,
//                        color = MaterialTheme.colors.onBackground,
                            modifier = Modifier.padding(vertical = 10.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                }
                item {
                    LazyRow() {//Start  On Dek items LazyRow
                        items(onDeckBooksState.itemCount) { i ->
                            val onDeckBook = onDeckBooksState[i]
                            BookThumbCard(
                                url = "${serverInfo.url}api/v1/books/${onDeckBook?.id}/thumbnail",
                                book = onDeckBook,
                                modifier = Modifier
                                    .padding(16.dp)
                                    .fillMaxWidth(),
                                onItemClick = {navigator.navigate(BookInfoScreenDestination(bookId = it))}
                            )
                        }
                    }
                }//End On Dek items LazyRow
                //----Keep recently_added_books start
                item{
                    Spacer(modifier = Modifier.height(height = 20.dp))
                    Row {
                        Text(
                            text = stringResource(id = R.string.recently_added_books),
                            color = Color.Black,
                            style = MaterialTheme.typography.h4,
//                        color = MaterialTheme.colors.onBackground,
                            modifier = Modifier.padding(vertical = 10.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                }
                item {
                    LazyRow {//Start recently_added_books items LazyRow
                        items(recentlyAddedBooks.itemCount) { i ->
                            val book = recentlyAddedBooks[i]
                            BookThumbCard(
                                url = "${serverInfo.url}api/v1/books/${book?.id}/thumbnail",
                                book = book,
                                modifier = Modifier
                                    .padding(16.dp)
                                    .fillMaxWidth(),
                              onItemClick = {navigator.navigate(BookInfoScreenDestination(bookId = it))}
                            )
                        }
                    }
                }//End recently_added_booksitems LazyRo

                //Start New Series
                item{
                    Spacer(modifier = Modifier.height(height = 20.dp))
                    Row {
                        Text(
                            text = stringResource(id = R.string.recently_add_series),
                            color = Color.Black,
                            style = MaterialTheme.typography.h4,
//                        color = MaterialTheme.colors.onBackground,
                            modifier = Modifier.padding(vertical = 10.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                }
                item {
                    LazyRow {//Start New Series items LazyRow
                        items(newSeries.itemCount) { i ->
                            val series = newSeries[i]
                            SeriesThumbCard(
                                url = "${serverInfo.url}api/v1/series/${series?.id}/thumbnail",
                                booksCount = series?.booksCount,
                                booksUnreadCount = series?.booksUnreadCount,
                                id = series?.id.toString(),
                                title = series?.metadata?.title,
                                modifier = Modifier
                                    .padding(16.dp)
                                    .fillMaxWidth(),
                                onItemClick = { navigator.navigate(SeriesByIdScreenDestination(seriesId = it))}
                            )
                        }
                    }
                }//End New Series

                //Start Updated Series
                item{
                    Spacer(modifier = Modifier.height(height = 20.dp))
                    Row {
                        Text(
                            text = stringResource(id = R.string.recently_updated_series),
                            color = Color.Black,
                            style = MaterialTheme.typography.h4,
//                        color = MaterialTheme.colors.onBackground,
                            modifier = Modifier.padding(vertical = 10.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                }
                item {
                    LazyRow {//Start New Series items LazyRow
                        items(updatedSeries.itemCount) { i ->
                            val series = updatedSeries[i]
                            SeriesThumbCard(
                                url = "${serverInfo.url}api/v1/series/${series?.id}/thumbnail",
                                booksCount = series?.booksCount,
                                booksUnreadCount = series?.booksUnreadCount,
                                id = series?.id.toString(),
                                title = series?.metadata?.title,
                                modifier = Modifier
                                    .padding(16.dp)
                                    .fillMaxWidth(),
                                onItemClick = { navigator.navigate(SeriesByIdScreenDestination(seriesId = it))}
                            )
                        }
                    }
                }//End Updated Series
            }//--End Main LazyColum

        }

    }

}
