package org.maddiesoftware.komagareader.server_display.presentaion.screen.bookreader

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import org.maddiesoftware.komagareader.server_display.presentaion.activity.MainViewModule
import org.maddiesoftware.komagareader.server_display.presentaion.screen.bookinfo.BookInfoViewModel

@OptIn(ExperimentalPagerApi::class)
@Destination
@Composable
fun BookReaderScreen(
    bookId: String? = null,
    bookPageCount: Int?,
    viewModel: BookInfoViewModel = hiltViewModel(),
    mainViewModule: MainViewModule = hiltViewModel(),
    navigator: DestinationsNavigator,
){
    val imageList = Constants.imageList
    var imageNum:Int = 0
    if (bookPageCount != null) {
        imageNum = bookPageCount
    }
    val pagerState = rememberPagerState(imageNum)
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colors.background
    ) {
        Column(modifier = Modifier.fillMaxSize()) {

            HorizontalPager( state = pagerState) { page ->

                PagerItemScreen(imageUrl = imageList[page],page)

            }

            LaunchedEffect(key1 = pagerState) {
                pagerState.animateScrollToPage(5)
            }


        }
    }
}

object Constants {

    val imageList = listOf(
        "https://apod.nasa.gov/apod/image/1912/M27_Mazlin_960.jpg",
        "https://apod.nasa.gov/apod/image/1912/ElectricMilkyWay_Pedretti_1080.jpg",
        "https://apod.nasa.gov/apod/image/1912/NGC6744_FinalLiuYuhang1024.jpg",
        "https://apod.nasa.gov/apod/image/1912/TaurusAbolfath1024.jpg",
        "https://apod.nasa.gov/apod/image/1912/LinesOfTimeKomlev1100.jpg",
        "https://apod.nasa.gov/apod/image/1912/geminids2013_beletsky_960.jpg",
        "https://apod.nasa.gov/apod/image/1912/StarlinkTrails_Filter_1080.jpg",
        "https://apod.nasa.gov/apod/image/1912/N63A_HubbleChandraSchmidt_960.jpg",
        "https://apod.nasa.gov/apod/image/1912/m78ldn1622barnardsloopJulio1100.jpg",
        "https://apod.nasa.gov/apod/image/1912/gem16otjcc1100.jpg",
        "https://apod.nasa.gov/apod/image/1912/borisovStsci1315.jpg",
        "https://apod.nasa.gov/apod/image/1912/mammatus_olson_960.jpg",
        "https://apod.nasa.gov/apod/image/1912/M77Bfield_NASA_960.jpg",
        "https://apod.nasa.gov/apod/image/1912/Horsehead_Hanson_960.jpg",
        "https://apod.nasa.gov/apod/image/1912/J0030_NICER_1024.jpg",
        "https://apod.nasa.gov/apod/image/1912/AS17-149-22859-2v2SmlWmk1024.jpg",
        "https://apod.nasa.gov/apod/image/1912/N2616c_600h.jpg",
        "https://apod.nasa.gov/apod/image/1912/WinterStars_Slovinsky_1080.jpg",
        "https://apod.nasa.gov/apod/image/1912/AnnularEclipse_Pinski_960.jpg",
        "https://apod.nasa.gov/apod/image/1912/Orava_Duskova_WinterHexagon_0.jpg",
        "https://apod.nasa.gov/apod/image/1912/PartialSolar_Ghohroodi_960.jpg",
        "https://apod.nasa.gov/apod/image/1912/DistortedSunrise_Chasiotis_1080.jpg",
        "https://apod.nasa.gov/apod/image/1912/saturnplane_cassini_1004.jpg",
        "https://apod.nasa.gov/apod/image/1912/M20_volskiy1024.jpg",
        "https://apod.nasa.gov/apod/image/1912/M33-HaLRGB-RayLiao1024.jpg",
    )
}


@Composable
fun PagerItemScreen(imageUrl: String, count: Int) {

    Box(modifier = Modifier.fillMaxSize()) {
        SubcomposeAsyncImage(
            model = imageUrl,
            contentDescription = "image",
            modifier = Modifier.fillMaxSize()
        ) {
            val state = painter.state
            if (state is AsyncImagePainter.State.Loading || state is AsyncImagePainter.State.Error) {
                CircularProgressIndicator()
            } else {
                SubcomposeAsyncImageContent()
            }
        }
        Text(
            text = "Page $count",
            fontSize = MaterialTheme.typography.h2.fontSize,
            color = Color.Red,
            modifier = Modifier.align(alignment = Alignment.BottomStart)
        )
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun PagerScreen() {

    val scope = rememberCoroutineScope()







}