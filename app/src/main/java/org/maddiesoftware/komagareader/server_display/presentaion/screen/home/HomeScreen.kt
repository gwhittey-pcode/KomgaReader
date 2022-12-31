package org.maddiesoftware.komagareader.server_display.presentaion.screen.home

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.background
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
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.launch
import org.maddiesoftware.komagareader.R
import org.maddiesoftware.komagareader.core.presentation.DataStoreViewModel
import org.maddiesoftware.komagareader.destinations.AllSeriesScreenDestination
import org.maddiesoftware.komagareader.destinations.HomeScreenDestination
import org.maddiesoftware.komagareader.server_display.presentaion.activity.MainViewModule
import org.maddiesoftware.komagareader.server_display.presentaion.componet.*

@Destination
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun HomeScreen(

    navigator: DestinationsNavigator,
    viewModel: HomeViewModule = hiltViewModel(),
    dataStoreViewModel: DataStoreViewModel = hiltViewModel(),
    mainViewModule: MainViewModule = hiltViewModel()
//    lastUpdated: Resource<PageSeries>,

){
    val state = viewModel.state
    val libraryList = mainViewModule.state.libraryList
    val url = dataStoreViewModel.getUrl()
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()
    var newList:List<MenuItem> = emptyList()
    if (state.error == null) {
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
                NavDrawer(
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

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colors.background)
                    .padding(16.dp)
            ) {

                //----Keep Reading start
                item{
                    Spacer(modifier = Modifier.height(height = 20.dp))
                    Row() {
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
                    LazyRow(
                        modifier = Modifier.fillMaxSize()
                    ) {
//                Log.d("komga1","state.getLatestResult =$state")
                        state.getKeepReading?.books?.let {
                            items(it.size) { i ->
                                val book = state.getKeepReading.books[i]
//                        Log.d("komga1","series= ${series.name}")
                                BookThumbCard(
                                    url = "${url}api/v1/books/${book.id}/thumbnail",
                                    book = book ,
                                    modifier = Modifier
                                        .padding(16.dp)
                                        .fillMaxWidth()
                                )
                            }
                        }
                    }
                }//----Keep Reading end

                //----On Deck start
                item{
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
                    LazyRow(
                        modifier = Modifier.fillMaxSize()
                    ) {
//                Log.d("komga1","state.getLatestResult =$state")
                        state.getOnDeckBooks?.books?.let {
                            items(it.size) { i ->
                                val book = state.getOnDeckBooks.books[i]
//                        Log.d("komga1","series= ${series.name}")
                                BookThumbCard(
                                    url = "${url}api/v1/books/${book.id}/thumbnail",
                                    book = book ,
                                    modifier = Modifier
                                        .padding(16.dp)
                                        .fillMaxWidth()
                                )
                            }
                        }
                    }
                }//----On Deck end

                //----Recently Added Books start
                item{
                    Spacer(modifier = Modifier.height(height = 20.dp))
                    Row() {
                        Text(
                            text = "Thhis is the row",
                            color = Color.Black,
                            style = MaterialTheme.typography.h4,
//                        color = MaterialTheme.colors.onBackground,
                            modifier = Modifier.padding(vertical = 10.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                }
                item {
                    LazyRow(
                        modifier = Modifier.fillMaxSize()
                    ) {
//                Log.d("komga1","state.getLatestResult =$state")
                        state.getRecentlyAddedBooks?.books?.let {
                            items(it.size) { i ->
                                val book = state.getRecentlyAddedBooks.books[i]
                                Log.d("koomga12345", "Book = ${book.toString()}")
//                        Log.d("komga1","series= ${series.name}")
                                BookThumbCard(
                                    url = "${url}api/v1/books/${book.id}/thumbnail",
                                    book = book ,
                                    modifier = Modifier
                                        .padding(16.dp)
                                        .fillMaxWidth()
                                )
                            }
                        }
                    }
                }//----Recently Added Books end

                //----Recently Added Series start
                item{
                    Spacer(modifier = Modifier.height(height = 20.dp))
                    Row() {
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
                    LazyRow(
                        modifier = Modifier.fillMaxSize()
                    ) {
//                Log.d("komga1","state.getLatestResult =$state")
                        state.getNewSeries?.series?.let {
                            items(it.size) { i ->
                                val series = state.getNewSeries.series[i]
//                        Log.d("komga1","series= ${series.name}")
                                SeriesThumbCard(
                                    url = "${url}api/v1/series/${series.id}/thumbnail",
                                    series = series,
                                    modifier = Modifier
                                        .padding(16.dp)
                                        .fillMaxWidth()
                                )
                            }
                        }
                    }
                }//

                //----Recently Updated  Series start
                item{
                    Spacer(modifier = Modifier.height(height = 20.dp))
                    Row() {
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
                    LazyRow(
                        modifier = Modifier.fillMaxSize()
                    ) {
//                Log.d("komga1","state.getLatestResult =$state")
                        state.getUpdatedSeries?.series?.let {
                            items(it.size) { i ->
                                val series = state.getUpdatedSeries.series[i]
//                        Log.d("komga1","series= ${series.name}")
                                SeriesThumbCard(
                                    url = "${url}api/v1/series/${series.id}/thumbnail",
                                    series = series,
                                    modifier = Modifier
                                        .padding(16.dp)
                                        .fillMaxWidth()
                                )
                            }
                        }
                    }
                }//----Recently Updated  Series end

            }
        }

    }

}
