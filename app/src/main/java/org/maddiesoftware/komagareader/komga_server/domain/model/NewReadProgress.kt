package org.maddiesoftware.komagareader.komga_server.domain.model

import com.google.gson.annotations.SerializedName

data class NewReadProgress(
    @SerializedName("page") var page:Int = 0,
    @SerializedName("completed") var completed:Boolean = false
)
