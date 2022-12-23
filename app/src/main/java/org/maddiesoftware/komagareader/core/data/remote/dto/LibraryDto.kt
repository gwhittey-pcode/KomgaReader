package org.maddiesoftware.komagareader.core.data.remote.dto

data class LibraryDto(
    val analyzeDimensions: Boolean?,
    val convertToCbz: Boolean?,
    val emptyTrashAfterScan: Boolean?,
    val hashFiles: Boolean?,
    val hashPages: Boolean?,
    val id: String?,
    val importBarcodeIsbn: Boolean?,
    val importComicInfoBook: Boolean?,
    val importComicInfoCollection: Boolean?,
    val importComicInfoReadList: Boolean?,
    val importComicInfoSeries: Boolean?,
    val importEpubBook: Boolean?,
    val importEpubSeries: Boolean?,
    val importLocalArtwork: Boolean?,
    val importMylarSeries: Boolean?,
    val name: String?,
    val repairExtensions: Boolean?,
    val root: String?,
    val scanDeep: Boolean?,
    val scanForceModifiedTime: Boolean?,
    val seriesCover: String?,
    val unavailable: Boolean?
)
