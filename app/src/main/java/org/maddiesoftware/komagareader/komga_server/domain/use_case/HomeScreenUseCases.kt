package org.maddiesoftware.komagareader.komga_server.domain.use_case

data class HomeScreenUseCases(
    val getOnDeckBooks:GetOnDeckBooks,
    val getKeepReading:GetKeepReading,
    val getRecentlyAddedBooks: GetRecentlyAddedBooks,
    val getNewSeries:GetNewSeries,
    val getUpdatedSeries:GetUpdatedSeries,
)
