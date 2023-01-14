package org.maddiesoftware.komagareader.komga_server.presentaion.state

import org.maddiesoftware.komagareader.komga_server.domain.model.PageBook
import org.maddiesoftware.komagareader.komga_server.domain.model.PageSeries

data class HomeState(
    val getUpdatedSeries: PageSeries? =null,
    val getKeepReading: PageBook? = null,
    val getOnDeckBooks: PageBook? = null,
    val getNewSeries: PageSeries? = null,
    val getRecentlyAddedBooks: PageBook? = null,
    val isLoading: Boolean = false,
    val error: String? = null

)
