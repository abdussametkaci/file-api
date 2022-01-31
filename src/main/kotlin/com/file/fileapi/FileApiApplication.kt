package com.file.fileapi

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication

@SpringBootApplication
@ConfigurationPropertiesScan
class FileApiApplication

fun main(args: Array<String>) {
    runApplication<FileApiApplication>(*args)
}
