package com.file.fileapi.domain.model

import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import java.time.Instant
import java.util.UUID

@Table
data class File(
    @Id val id: UUID? = null,
    val extension: String,
    @CreatedDate var createdAt: Instant? = null
) {
    fun getName(): String {
        return id.toString() + "." + extension
    }
}
