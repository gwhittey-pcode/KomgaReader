package org.maddiesoftware.komagareader.komga_server.presentaion.componet.bookreader

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.width
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.unit.dp


@Composable
fun BookReaderControlButtons(
    modifier: Modifier,
    onClickItem: () -> Unit
) {
    Button(//Right Side  Button to next page
        enabled = true,
        border = BorderStroke(width = 1.dp, brush = SolidColor(Color.Blue)),
        shape = MaterialTheme.shapes.medium,
        modifier = modifier
            .width(100.dp)
            .alpha(1f),
        onClick = onClickItem
    ) {}//NextButton
//    Button(//Left Side Button for Next Page
//        enabled = true,
//        border = BorderStroke(width = 1.dp, brush = SolidColor(Color.Blue)),
//        shape = MaterialTheme.shapes.medium,
//        modifier = Modifier
//            .width(100.dp)
//            .alpha(0f),
//        onClick = onClickItem
//    ) {}//end prevButton
//
//    Button(//Lower Middle Button to Open Page Nav Dialog
//        enabled = true,
//        border = BorderStroke(width = 1.dp, brush = SolidColor(Color.Blue)),
//        shape = MaterialTheme.shapes.medium,
//        modifier = Modifier
//            .height(100.dp)
//            .alpha(0f),
//        onClick = onClickItem
//    ) {}//End dialogOpenPageNavButton
//
//    Button(//Lower Middle Button to Open Page Nav Dialog
//        enabled = true,
//        border = BorderStroke(width = 1.dp, brush = SolidColor(Color.Blue)),
//        shape = MaterialTheme.shapes.medium,
//        modifier = Modifier
//            .height(100.dp)
//            .alpha(0f),
//
//        onClick = onClickItem
//    ) {}//end dialogOpenSeriesBookNavButton
//
//
//    Button(
////Lower Middle Button to Open Page Nav Dialog
//        enabled = true,
//        border = BorderStroke(width = 1.dp, brush = SolidColor(Color.Blue)),
//        shape = MaterialTheme.shapes.medium,
//        modifier = Modifier
//            .height(200.dp)
//            .alpha(0f),
//        content = {},
//        onClick = onClickItem,
//    )
}