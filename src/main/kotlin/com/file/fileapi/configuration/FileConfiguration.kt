package com.file.fileapi.configuration

import com.file.fileapi.configuration.properties.FileProperties
import org.springframework.context.annotation.Configuration
import java.nio.file.Files
import java.nio.file.Paths
import javax.annotation.PostConstruct

@Configuration
class FileConfiguration(private val fileProperties: FileProperties) {

    @PostConstruct
    fun createFileDirectory() {
        Files.createDirectories(Paths.get(fileProperties.upload))
    }
}
