package com.file.fileapi.repository

import com.file.fileapi.domain.model.File
import org.springframework.data.r2dbc.repository.Query
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import java.util.UUID

interface FileRepository : CoroutineCrudRepository<File, UUID> {

    @Query("""DELETE FROM file WHERE id = :id RETURNING *""")
    suspend fun deleteAndReturnById(id: UUID): File?
}
