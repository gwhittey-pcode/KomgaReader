package org.maddiesoftware.komagareader.komga_server.presentaion.componet.seriesbyid

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.TabPosition
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


@Composable
fun SeriesByIdTabIndicator(tabPosition: List<TabPosition>, index: Int) {
    val transition = updateTransition(targetState = index, label = "")
    val leftIndicator by transition.animateDp(label = "", transitionSpec = {
        spring(stiffness = Spring.StiffnessVeryLow)
    }) {
        tabPosition[it].left
    }
    val rightIndicator by transition.animateDp(label = "", transitionSpec = {
        spring(stiffness = Spring.StiffnessVeryLow)
    }) {
        tabPosition[it].right
    }
    Box(
        Modifier
            .fillMaxSize()
            .wrapContentSize(align = Alignment.BottomStart)
            .offset(x = leftIndicator)
            .width(rightIndicator - leftIndicator)
            .padding(4.dp)
            .fillMaxSize()
            .border(
                BorderStroke(width = 4.dp, MaterialTheme.colors.primaryVariant),
                RoundedCornerShape(4.dp)
            )
    )
}