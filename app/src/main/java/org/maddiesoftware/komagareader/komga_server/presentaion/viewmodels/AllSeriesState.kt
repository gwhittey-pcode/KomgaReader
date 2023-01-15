package org.maddiesoftware.komagareader.komga_server.presentaion.viewmodels

import org.maddiesoftware.komagareader.komga_server.domain.model.PageSeries

data class AllSeriesState(
    val getAllSeries: PageSeries? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)
