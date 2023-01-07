package org.maddiesoftware.komagareader.server_display.domain.usecase

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import org.maddiesoftware.komagareader.server_display.domain.model.Book
import org.maddiesoftware.komagareader.server_display.domain.repository.ApiRepository

class GetKeepReading (private val apiRepository: ApiRepository) {
    operator fun invoke(pageSize: Int, libraryId: String?): Flow<PagingData<Book>> {
        return apiRepository.getKeepReading(pageSize = pageSize,libraryId =libraryId)
    }

}
