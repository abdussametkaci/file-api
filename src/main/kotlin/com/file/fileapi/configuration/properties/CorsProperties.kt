package com.file.fileapi.configuration.properties

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties("cors")
data class CorsProperties(
    val allowedOrigins: List<String>
)
