package com.file.fileapi.controller

import com.file.fileapi.domain.model.File
import com.file.fileapi.service.FileService
import org.springframework.core.io.Resource
import org.springframework.http.MediaType.APPLICATION_OCTET_STREAM_VALUE
import org.springframework.http.codec.multipart.FilePart
import org.springframework.http.server.reactive.ServerHttpRequest
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestPart
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
@RequestMapping("/files")
class FileController(private val fileService: FileService) {

    @PostMapping
    suspend fun upload(@RequestPart("file") filePart: FilePart): File {
        return fileService.upload(filePart)
    }

    @GetMapping("/{id}", produces = [APPLICATION_OCTET_STREAM_VALUE])
    suspend fun download(@PathVariable id: UUID): Resource {
        return fileService.download(id)
    }

    @DeleteMapping("/{id}")
    suspend fun delete(@PathVariable id: UUID) {
        fileService.delete(id)
    }
}
