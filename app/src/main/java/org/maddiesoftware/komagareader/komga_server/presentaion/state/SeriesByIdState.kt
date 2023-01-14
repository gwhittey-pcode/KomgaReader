package org.maddiesoftware.komagareader.komga_server.presentaion.state

import org.maddiesoftware.komagareader.komga_server.domain.model.Series

data class SeriesByIdState(
    val seriesInfo: Series? = null,
    val isLoading: Boolean = false,
    val error: String? = null
) {
}