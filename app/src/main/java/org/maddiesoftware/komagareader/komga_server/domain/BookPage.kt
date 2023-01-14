package org.maddiesoftware.komagareader.komga_server.domain

class BookPage(
    val index: Int = 0,
    val doSplit: Boolean = false,
    val pageName: String = "1",
    val number: Int?,
    val splitPart: String = "1"
)
