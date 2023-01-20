package org.maddiesoftware.komagareader.komga_server.presentaion.state

import org.maddiesoftware.komagareader.komga_server.domain.model.CollectionX

data class CollectionByIdState(
    val collectionInfo: CollectionX? = null,
    val isLoading: Boolean = false,
    val error: String? = null
) {
}
