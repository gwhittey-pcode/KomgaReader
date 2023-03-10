package org.maddiesoftware.komagareader.komga_server.presentaion.screen

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Splitscreen
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import org.maddiesoftware.komagareader.core.presentation.theme.Poppins
import org.maddiesoftware.komagareader.core.presentation.viewmodels.MainViewModel
import org.maddiesoftware.komagareader.komga_server.presentaion.componet.settings.GeneralSettingItem
import org.maddiesoftware.komagareader.komga_server.presentaion.viewmodels.SettingsViewModel

@OptIn(ExperimentalMaterialApi::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Destination
@Composable
fun SettingsScreen(
    navigator: DestinationsNavigator,
    viewModel: SettingsViewModel = hiltViewModel(),
    mainViewModel: MainViewModel
) {
    mainViewModel.showTopBar.value = true
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()
    val state = viewModel.state

    Column {
        HeaderText()
        //BookReader Settings Colum
        Column(
            modifier = Modifier
                .padding(horizontal = 10.dp)
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
            Log.d(
                "Settings",
                "viewModel.state.useDblPageSplit = ${viewModel.state.useDblPageSplit}"
            )
            GeneralSettingItem(
                icon = Icons.Default.Splitscreen,
                mainText = "Split Double Pages",
                subText = "If Page Width > Page Height then split page in two",
                onClick = {},
                settingsControl = {
                    Switch(
                        checked = viewModel.state.useDblPageSplit,
                        colors = SwitchDefaults.colors(MaterialTheme.colors.primary),
                        onCheckedChange = { viewModel.writeUseDblPageSplit(it) }
                    )
                },
            )
        }//End BookReader Settings Colum
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

@ExperimentalMaterialApi
@Composable
fun BookReaderSettingsUI() {

}