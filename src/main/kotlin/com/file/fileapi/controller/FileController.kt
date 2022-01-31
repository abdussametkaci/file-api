package com.file.fileapi.controller

import com.file.fileapi.component.ContextAwareConversionService
import com.file.fileapi.component.convert
import com.file.fileapi.domain.dto.response.FileResponse
import com.file.fileapi.service.FileService
import org.springframework.core.io.Resource
import org.springframework.http.MediaType.APPLICATION_OCTET_STREAM_VALUE
import org.springframework.http.codec.multipart.FilePart
import org.springframework.http.server.reactive.ServerHttpRequest
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestPart
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
@RequestMapping("/files")
class FileController(
    private val fileService: FileService,
    private val conversionService: ContextAwareConversionService
) {

    @PostMapping("/upload")
    suspend fun upload(@RequestPart("file") filePart: FilePart, serverHttpRequest: ServerHttpRequest): FileResponse {
        return conversionService.convert<FileResponse>(fileService.upload(filePart)).apply {
            uri = getUri(serverHttpRequest, this.id.toString())
        }
    }

    @GetMapping("/{id}", produces = [APPLICATION_OCTET_STREAM_VALUE])
    suspend fun download(@PathVariable id: UUID): Resource {
        return fileService.download(id)
    }

    private suspend fun getUri(serverHttpRequest: ServerHttpRequest, filename: String): String {
        val uri = serverHttpRequest.uri
        return """${uri.scheme}://${uri.host}${uri.port}/files/$filename"""
    }
}
