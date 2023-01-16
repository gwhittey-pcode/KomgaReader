package org.maddiesoftware.komagareader.komga_server.domain.use_case.readlists

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import org.maddiesoftware.komagareader.komga_server.domain.model.Book
import org.maddiesoftware.komagareader.komga_server.domain.repository.ApiRepository

class GetBooksFromReadList (private val apiRepository: ApiRepository) {
    operator fun invoke(pageSize: Int, readListId: String?): Flow<PagingData<Book>> {
        return apiRepository.getBooksFromReadList(pageSize = pageSize, readListId = readListId.toString())
    }
}