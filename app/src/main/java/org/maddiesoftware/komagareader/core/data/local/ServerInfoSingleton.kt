package org.maddiesoftware.komagareader.core.data.local

import org.maddiesoftware.komagareader.komga_server.domain.model.Library

object ServerInfoSingleton {
    var serverName: String = ""
    var userName = ""
    var password = ""
    var url = ""
    var libraryList: List<Library>? =null
}