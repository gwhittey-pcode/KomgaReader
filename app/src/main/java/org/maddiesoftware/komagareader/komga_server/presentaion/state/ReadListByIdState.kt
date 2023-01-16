package org.maddiesoftware.komagareader.komga_server.presentaion.state

import org.maddiesoftware.komagareader.komga_server.domain.model.ReadList

data class ReadListByIdState(
    val readListInfo: ReadList? = null,
    val isLoading: Boolean = false,
    val error: String? = null
) {
}
