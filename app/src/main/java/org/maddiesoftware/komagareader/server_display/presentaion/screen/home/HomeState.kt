package org.maddiesoftware.komagareader.server_display.presentaion.screen.home

import org.maddiesoftware.komagareader.server_display.domain.model.PageBook
import org.maddiesoftware.komagareader.server_display.domain.model.PageSeries

data class HomeState(
    val getUpdatedSeries: PageSeries? =null,
    val getKeepReading: PageBook? = null,
    val getOnDeckBooks: PageBook? = null,
    val getNewSeries: PageSeries? = null,
    val getRecentlyAddedBooks: PageBook? = null,
    val isLoading: Boolean = false,
    val error: String? = null

)
