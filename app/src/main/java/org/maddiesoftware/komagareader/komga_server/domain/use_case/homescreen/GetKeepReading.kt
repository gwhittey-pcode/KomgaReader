package org.maddiesoftware.komagareader.komga_server.domain.use_case.homescreen

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import org.maddiesoftware.komagareader.komga_server.domain.model.Book
import org.maddiesoftware.komagareader.komga_server.domain.repository.ApiRepository

class GetKeepReading (private val apiRepository: ApiRepository) {
    operator fun invoke(pageSize: Int, libraryId: String?): Flow<PagingData<Book>> {
        return apiRepository.getKeepReading(pageSize = pageSize,libraryId =libraryId)
    }

}
