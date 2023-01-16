package org.maddiesoftware.komagareader.komga_server.domain.use_case.readlists

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import org.maddiesoftware.komagareader.komga_server.domain.model.ReadList
import org.maddiesoftware.komagareader.komga_server.domain.repository.ApiRepository

class GetAllReadList(private val apiRepository: ApiRepository) {
    operator fun invoke(pageSize: Int, libraryId: String?): Flow<PagingData<ReadList>> {
        return apiRepository.getAllReadList(pageSize = pageSize, libraryId =libraryId)
    }
}
