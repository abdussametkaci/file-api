package com.file.fileapi.service

import com.file.fileapi.domain.model.File
import com.file.fileapi.exception.NotFoundException
import com.file.fileapi.repository.FileRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.springframework.core.io.FileSystemResource
import org.springframework.core.io.Resource
import org.springframework.core.io.buffer.DataBufferUtils
import org.springframework.http.codec.multipart.FilePart
import org.springframework.stereotype.Service
import reactor.core.scheduler.Schedulers
import java.nio.channels.AsynchronousFileChannel
import java.nio.file.Files
import java.nio.file.Paths
import java.nio.file.StandardOpenOption
import java.util.UUID

@Service
class FileService(private val fileRepository: FileRepository) {

    suspend fun upload(filePart: FilePart): File {
        val extension = filePart.extension
        val file = fileRepository.save(File(extension = extension))
        val filename = file.id.toString() + "." + file.extension

        withContext(Dispatchers.IO) {
            Files.createDirectories(Paths.get("./files"))
            val filePath = Paths.get("./files/$filename").toAbsolutePath().normalize()
            val path = Files.createFile(filePath)
            val channel = AsynchronousFileChannel.open(path, StandardOpenOption.WRITE)
            DataBufferUtils.write(filePart.content(), channel, 0)
                .publishOn(Schedulers.boundedElastic())
                .doOnComplete {
                    channel.close()
                }
                .subscribe()
        }

        return file
    }

    suspend fun download(id: UUID): Resource {
        val file = fileRepository.findById(id) ?: throw NotFoundException("error.file.not-found", args = arrayOf(id))
        val filename = file.id.toString() + "." + file.extension
        val path = Paths.get("./files/$filename").toAbsolutePath().normalize()
        return FileSystemResource(path.toString())
    }

    private val FilePart.extension: String
        get() = filename().substringAfterLast('.', "")
}
