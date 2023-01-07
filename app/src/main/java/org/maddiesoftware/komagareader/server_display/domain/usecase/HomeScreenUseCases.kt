package org.maddiesoftware.komagareader.server_display.domain.usecase

data class HomeScreenUseCases(
    val getOnDeckBooks:GetOnDeckBooks,
    val getKeepReading:GetKeepReading,
    val getRecentlyAddedBooks: GetRecentlyAddedBooks,
    val getNewSeries:GetNewSeries,
    val getUpdatedSeries:GetUpdatedSeries,
)
