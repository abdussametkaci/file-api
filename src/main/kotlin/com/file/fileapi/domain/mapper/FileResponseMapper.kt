package com.file.fileapi.domain.mapper

import com.file.fileapi.component.ContextAwareConverter
import com.file.fileapi.domain.dto.response.FileResponse
import com.file.fileapi.domain.model.File
import org.mapstruct.Mapper

@Mapper
interface FileResponseMapper : ContextAwareConverter<File, FileResponse>
