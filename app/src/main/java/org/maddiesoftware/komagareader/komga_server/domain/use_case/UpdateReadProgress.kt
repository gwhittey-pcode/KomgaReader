package org.maddiesoftware.komagareader.komga_server.domain.use_case

import org.maddiesoftware.komagareader.core.util.Resource
import org.maddiesoftware.komagareader.komga_server.domain.repository.ApiRepository
import retrofit2.Response

class UpdateReadProgress(private val apiRepository: ApiRepository) {
    suspend operator fun invoke(page: Int, bookId: String, completed:Boolean): Resource<Response<Unit>> {
        return apiRepository.updateReadProgress(
            bookId =bookId,
            page=page,
            completed=completed
        )
    }
}

