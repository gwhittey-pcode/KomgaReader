package org.maddiesoftware.komagareader.server_display.domain.model

import org.maddiesoftware.komagareader.server_display.presentaion.componet.MenuItem

data class Library(
    val id: String?,
    val name: String?,
    val unavailable: Boolean?,
){
    fun toMenuItem():MenuItem{
        return MenuItem(
            id = id.toString(),
            title = name.toString()

        )
    }
}
