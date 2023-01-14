package org.maddiesoftware.komagareader.komga_server.presentaion.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowRight
import androidx.compose.material.icons.filled.More
import androidx.compose.material.icons.filled.Splitscreen
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.launch
import org.maddiesoftware.komagareader.core.data.local.ServerInfoSingleton.libraryList
import org.maddiesoftware.komagareader.core.presentation.theme.LightPrimaryColor
import org.maddiesoftware.komagareader.core.presentation.theme.Poppins
import org.maddiesoftware.komagareader.core.presentation.theme.SecondaryColor
import org.maddiesoftware.komagareader.destinations.AllSeriesScreenDestination
import org.maddiesoftware.komagareader.destinations.HomeScreenDestination
import org.maddiesoftware.komagareader.destinations.SettingsScreenDestination
import org.maddiesoftware.komagareader.komga_server.presentaion.componet.general.NavBar
import org.maddiesoftware.komagareader.komga_server.presentaion.componet.general.NavDrawer

@OptIn(ExperimentalMaterialApi::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Destination
@Composable
fun SettingsScreen(navigator: DestinationsNavigator,){
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
                    when(id){
                        "home" -> {navigator.navigate(HomeScreenDestination())}
                        "settings" -> {navigator.navigate(SettingsScreenDestination())}
                        else -> {navigator.navigate(AllSeriesScreenDestination(libraryId = id))}
                    }
                }
            )

        }
    ){
        Column() {
            HeaderText()
//            ProfileCardUI()
            BookReaderSettingsUI()
            SupportOptionsUI()
        }
    }

}

@Composable
fun HeaderText() {

    Text(
        text = "Settings",
        fontFamily = Poppins,
        color = MaterialTheme.colors.onSurface,
        textAlign = TextAlign.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 30.dp, bottom = 10.dp),
        fontWeight = FontWeight.ExtraBold,
        style = MaterialTheme.typography.h4,
        fontSize = 20.sp
    )
}

@Composable
fun ProfileCardUI() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp)
            .padding(10.dp),
        backgroundColor = Color.White,
        elevation = 0.dp,
        shape = MaterialTheme.shapes.large
    ) {
        Row(
            modifier = Modifier.padding(20.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column() {
                Text(
                    text = "Book Reader Settings",
                    fontFamily = Poppins,
                    color = MaterialTheme.colors.onSurface,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                )
                Text(
                    text = "UI.Stack.YT@gmail.com",
                    fontFamily = Poppins,
                    color = MaterialTheme.colors.onSurface,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                )

                Button(
                    modifier = Modifier.padding(top = 10.dp),
                    onClick = {},
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor =MaterialTheme.colors.primary,
                    ),
                    contentPadding = PaddingValues(horizontal = 30.dp),
                    elevation = ButtonDefaults.elevation(
                        defaultElevation = 0.dp,
                        pressedElevation = 2.dp
                    ),
                    shape = MaterialTheme.shapes.medium
                ) {
                    Text(
                        text = "View",
                        fontFamily = Poppins,
                        color = MaterialTheme.colors.secondary,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
//            Image(
//                painter = painterResource(id = R.drawable.ic_profile_card_image),
//                contentDescription = "",
//                modifier = Modifier.height(120.dp)
//            )
        }
    }
}

@ExperimentalMaterialApi
@Composable
fun BookReaderSettingsUI() {
    Column(
        modifier = Modifier
            .padding(horizontal = 14.dp)
            .padding(top = 10.dp)
    ) {
        Text(
            text = "Book Reader Settings",
            fontFamily = Poppins,
            color = MaterialTheme.colors.onSurface,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(vertical = 8.dp)
        )
        GeneralSettingItem(
            icon = Icons.Default.Splitscreen,
            mainText = "Split Double Pages",
            subText = "Customize notifications"
        ) {}
        GeneralSettingItem(
            icon = Icons.Default.More,
            mainText = "More customization",
            subText = "Customize it more to fit your usage"
        ) {}
//        GeneralSettingItem()
    }
}

@ExperimentalMaterialApi
@Composable
fun GeneralSettingItem(icon: ImageVector, mainText: String, subText: String, onClick: () -> Unit) {
    Card(
        onClick = { onClick() },
        backgroundColor = Color.White,
        modifier = Modifier
            .padding(bottom = 8.dp)
            .fillMaxWidth(),
        elevation = 0.dp,
    ) {
        Row(
            modifier = Modifier.padding(vertical = 10.dp, horizontal = 14.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(34.dp)
                        .clip(shape = MaterialTheme.shapes.medium)
                        .background(MaterialTheme.colors.secondary)
                ) {
                    Icon(
                        imageVector = icon,
                        contentDescription = "",
                        tint = Color.Unspecified,
                        modifier = Modifier.padding(8.dp)
                    )
                }

                Spacer(modifier = Modifier.width(14.dp))
                Column(
                    modifier = Modifier.offset(y = (2).dp)
                ) {
                    Text(
                        text = mainText,
                        fontFamily = Poppins,
                        color = SecondaryColor,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                    )

                    Text(
                        text = subText,
                        fontFamily = Poppins,
                        color = Color.Gray,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.offset(y = (-4).dp)
                    )
                }
            }
            Icon(
                imageVector = Icons.Default.ArrowRight,
                contentDescription = "",
                modifier = Modifier.size(16.dp)
            )

        }
    }
}

@ExperimentalMaterialApi
@Composable
fun SupportOptionsUI() {
    Column(
        modifier = Modifier
            .padding(horizontal = 14.dp)
            .padding(top = 10.dp)
    ) {
        Text(
            text = "Support",
            fontFamily = Poppins,
            color = SecondaryColor,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(vertical = 8.dp)
        )
        SupportItem(
            icon = Icons.Default.More,
            mainText = "Feedback",
            onClick = {}
        )
        SupportItem(
            icon = Icons.Default.More,
            mainText = "Privacy Policy",
            onClick = {}
        )
        SupportItem(
            icon = Icons.Default.More,
            mainText = "About",
            onClick = {}
        )
    }
}

@ExperimentalMaterialApi
@Composable
fun SupportItem(icon: ImageVector, mainText: String, onClick: () -> Unit) {
    Card(
        onClick = { onClick() },
        backgroundColor = Color.White,
        modifier = Modifier
            .padding(bottom = 8.dp)
            .fillMaxWidth(),
        elevation = 0.dp,
    ) {
        Row(
            modifier = Modifier.padding(vertical = 10.dp, horizontal = 14.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(34.dp)
                        .clip(shape = MaterialTheme.shapes.medium)
                        .background(LightPrimaryColor)
                ) {
                    Icon(
                        imageVector = icon,
                        contentDescription = "",
                        tint = Color.Unspecified,
                        modifier = Modifier.padding(8.dp)
                    )
                }

                Spacer(modifier = Modifier.width(14.dp))

                Text(
                    text = mainText,
                    fontFamily = Poppins,
                    color = SecondaryColor,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                )
            }
            Icon(
                imageVector = Icons.Default.ArrowRight,
                contentDescription = "",
                modifier = Modifier.size(16.dp)
            )

        }
    }
}