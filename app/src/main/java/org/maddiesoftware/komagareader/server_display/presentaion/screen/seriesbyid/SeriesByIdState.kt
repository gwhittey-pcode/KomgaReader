package org.maddiesoftware.komagareader.server_display.presentaion.screen.seriesbyid

import org.maddiesoftware.komagareader.server_display.domain.model.Series

data class SeriesByIdState(
    val seriesInfo:Series? = null,
    val isLoading: Boolean = false,
    val error: String? = null
) {
}