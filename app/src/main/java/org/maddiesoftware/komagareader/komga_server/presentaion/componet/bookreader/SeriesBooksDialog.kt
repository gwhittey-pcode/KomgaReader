package org.maddiesoftware.komagareader.komga_server.presentaion.componet.bookreader

import android.view.Gravity
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layout
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.compose.ui.window.DialogWindowProvider
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import com.ramcosta.composedestinations.spec.DestinationStyle
import org.maddiesoftware.komagareader.R
import org.maddiesoftware.komagareader.core.data.local.ServerInfoSingleton
import org.maddiesoftware.komagareader.komga_server.presentaion.componet.BookThumbCard
import org.maddiesoftware.komagareader.komga_server.presentaion.componet.general.PaginationStateHandler
import org.maddiesoftware.komagareader.komga_server.presentaion.componet.general.WarningMessage
import org.maddiesoftware.komagareader.komga_server.presentaion.viewmodels.SeriesByIdViewModel

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SeriesBooksDialog(
    setShowDialog: (Boolean) -> Unit,
    screenWidth:Dp,
    onItemClick: (id: String) -> Unit,
    ) {
    val viewModel: SeriesByIdViewModel = hiltViewModel()
    val serverInfo = ServerInfoSingleton
    val bookState = viewModel.bookState
        .collectAsLazyPagingItems()
    Dialog(
        onDismissRequest = { setShowDialog(false) },
        properties = DestinationStyle.Dialog.properties.let {
            DialogProperties(
                dismissOnBackPress = it.dismissOnBackPress,
                dismissOnClickOutside = it.dismissOnClickOutside,
                securePolicy = it.securePolicy,
                usePlatformDefaultWidth = false
            )
        },
    ) {
        val dialogWindowProvider = LocalView.current.parent as DialogWindowProvider
        dialogWindowProvider.window.setGravity(Gravity.TOP)
        Surface(
            shape = RoundedCornerShape(16.dp),
            color = Color.White
        ) {
            LazyRow(modifier = Modifier.width(screenWidth).height(320.dp))
            {
                items(bookState.itemCount) { i ->
                    val book = bookState[i]
                    BookThumbCard(
                        url = "${serverInfo.url}api/v1/books/${book?.id}/thumbnail",
                        book = book,
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxWidth(),
                        onItemClick = {onItemClick(book?.id.toString())}
                    )
                }
                item {
                    PaginationStateHandler(
                        paginationState = bookState,
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
                                        modifier = Modifier
                                            .padding(start = 3.dp)
                                            .clickable(role = Role.Button) { bookState.retry() },
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

enum class CustomDialogPosition {
    BOTTOM, TOP
}

fun Modifier.customDialogModifier(pos: CustomDialogPosition) = layout { measurable, constraints ->

    val placeable = measurable.measure(constraints);
    layout(constraints.maxWidth, constraints.maxHeight){
        when(pos) {
            CustomDialogPosition.BOTTOM -> {
                placeable.place(0, constraints.maxHeight - placeable.height, 10f)
            }
            CustomDialogPosition.TOP -> {
                placeable.place(0,0,10f)
            }
        }
    }
}