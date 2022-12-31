package org.maddiesoftware.komagareader.server_display.presentaion.componet

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.maddiesoftware.komagareader.server_display.domain.model.Series

@Composable
fun SeriesItem(
    series: Series,
    modifier: Modifier = Modifier,
    url: String
) {

    MyAsyncImage(url = url)
    Spacer(modifier = Modifier.width(4.dp))
}
