package com.file.fileapi.service

import com.file.fileapi.configuration.properties.FileProperties
import com.file.fileapi.domain.model.File
import com.file.fileapi.exception.NotFoundException
import com.file.fileapi.repository.FileRepository
import kotlinx.coroutines.reactor.mono
import org.springframework.core.io.FileSystemResource
import org.springframework.core.io.Resource
import org.springframework.http.codec.multipart.FilePart
import org.springframework.stereotype.Service
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.util.UUID

@Service
class FileService(
    private val fileRepository: FileRepository,
    private val fileProperties: FileProperties
) {

    suspend fun upload(filePart: FilePart): File {
        val basePath = Paths.get(fileProperties.upload)
        val extension = filePart.extension
        val file = fileRepository.save(File(extension = extension))

        mono {
            filePart
        }
            .flatMap { fp ->
                fp.transferTo(basePath.resolve(file.getName()))
            }
            .subscribe()

        return file
    }

    suspend fun download(id: UUID): Resource {
        val file = fileRepository.findById(id) ?: throw NotFoundException("error.file.not-found", args = arrayOf(id))
        return FileSystemResource(getPath(file.getName()).toString())
    }

    suspend fun delete(id: UUID) {
        fileRepository.deleteAndReturnById(id)
            ?.let {
                Files.deleteIfExists(getPath(it.getName()))
            } ?: throw NotFoundException("error.file.not-found", args = arrayOf(id))
    }

    private suspend fun getPath(filename: String): Path {
        return Paths.get("${fileProperties.upload}/$filename").toAbsolutePath().normalize()
    }

    private val FilePart.extension: String
        get() = filename().substringAfterLast('.', "")
}
