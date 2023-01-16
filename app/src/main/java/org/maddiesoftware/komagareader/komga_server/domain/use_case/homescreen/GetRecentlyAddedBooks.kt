package org.maddiesoftware.komagareader.komga_server.domain.use_case.homescreen

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import org.maddiesoftware.komagareader.komga_server.domain.model.Book
import org.maddiesoftware.komagareader.komga_server.domain.repository.ApiRepository

class GetRecentlyAddedBooks(private val apiRepository: ApiRepository) {
    operator fun invoke(pageSize: Int, libraryId: String?): Flow<PagingData<Book>> {
        return apiRepository.getRecentlyAddedBooks(pageSize = pageSize,libraryId =libraryId)
    }

}
