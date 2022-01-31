package com.file.fileapi.domain.dto.response

import java.util.UUID

data class FileResponse(
    val id: UUID,
    val extension: String,
    var uri: String?
)
