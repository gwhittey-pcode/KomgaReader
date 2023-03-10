package org.maddiesoftware.komagareader.komga_server.data.remote.dto

import org.maddiesoftware.komagareader.komga_server.domain.model.Library

data class LibraryDto(
//    val analyzeDimensions: Boolean?,
//    val convertToCbz: Boolean?,
//    val emptyTrashAfterScan: Boolean?,
//    val hashFiles: Boolean?,
//    val hashPages: Boolean?,
    val id: String?,
    val name: String?,
    val unavailable: Boolean?,
//    val importBarcodeIsbn: Boolean?,
//    val importComicInfoBook: Boolean?,
//    val importComicInfoCollection: Boolean?,
//    val importComicInfoReadList: Boolean?,
//    val importComicInfoSeries: Boolean?,
//    val importEpubBook: Boolean?,
//    val importEpubSeries: Boolean?,
//    val importLocalArtwork: Boolean?,
//    val importMylarSeries: Boolean?,

//    val repairExtensions: Boolean?,
//    val root: String?,
//    val scanDeep: Boolean?,
//    val scanForceModifiedTime: Boolean?,
//    val seriesCover: String?,

){
    fun toLibrary(): Library {
        return Library(
            id = id,
            name = name,
            unavailable = unavailable
        )
    }
}
