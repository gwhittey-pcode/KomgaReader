package org.maddiesoftware.komagareader.server_display.presentaion.screen.allseries

import org.maddiesoftware.komagareader.server_display.domain.model.PageSeries

data class AllSeriesState(
    val getAllSeries: PageSeries? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)
