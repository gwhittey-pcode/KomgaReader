package org.maddiesoftware.komagareader.komga_server.domain.use_case.collections

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import org.maddiesoftware.komagareader.komga_server.domain.model.CollectionX
import org.maddiesoftware.komagareader.komga_server.domain.repository.ApiRepository

class GetAllCollections (private val apiRepository: ApiRepository) {
    operator fun invoke(pageSize: Int, libraryId: String?): Flow<PagingData<CollectionX>> {
        return apiRepository.getAllCollections(pageSize = pageSize, libraryId =libraryId)
    }

}
