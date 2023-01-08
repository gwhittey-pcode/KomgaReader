package org.maddiesoftware.komagareader.core.util

import org.maddiesoftware.komagareader.server_display.domain.model.Library

object ServerInfoSingleton {
    var serverName: String = ""
    var userName = ""
    var password = ""
    var url = ""
    var libraryList: List<Library>? =null
}