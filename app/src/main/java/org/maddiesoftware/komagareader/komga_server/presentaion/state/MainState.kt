package org.maddiesoftware.komagareader.komga_server.presentaion.state

import org.maddiesoftware.komagareader.komga_server.domain.model.Library

data class MainState(
    val libraryList: List<Library>? =null,
    val isLoading: Boolean = false,
    val error: String? = null
)
