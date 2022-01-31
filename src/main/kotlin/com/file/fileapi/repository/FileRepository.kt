package com.file.fileapi.repository

import com.file.fileapi.domain.model.File
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import java.util.UUID

interface FileRepository : CoroutineCrudRepository<File, UUID>
