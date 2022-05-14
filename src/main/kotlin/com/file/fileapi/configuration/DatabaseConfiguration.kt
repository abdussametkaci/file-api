package com.file.fileapi.configuration

import com.file.fileapi.configuration.properties.DatabaseProperties
import io.r2dbc.pool.ConnectionPool
import io.r2dbc.pool.ConnectionPoolConfiguration
import io.r2dbc.postgresql.PostgresqlConnectionConfiguration
import io.r2dbc.postgresql.PostgresqlConnectionFactory
import io.r2dbc.spi.ConnectionFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.r2dbc.config.AbstractR2dbcConfiguration
import org.springframework.data.r2dbc.config.EnableR2dbcAuditing
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories

@Configuration
@EnableR2dbcRepositories
@EnableR2dbcAuditing
class DatabaseConfiguration(private val databaseProperties: DatabaseProperties) : AbstractR2dbcConfiguration() {

    @Bean
    override fun connectionFactory(): ConnectionFactory {
        with(databaseProperties) {
            val connectionFactory = PostgresqlConnectionFactory(
                PostgresqlConnectionConfiguration.builder()
                    .host(host)
                    .port(port.toInt())
                    .database(name)
                    .username(username)
                    .password(password)
                    .build()
            )

            val configuration = ConnectionPoolConfiguration.builder(connectionFactory)
                .maxIdleTime(connectionPool.maxIdleTime)
                .initialSize(connectionPool.initialSize)
                .maxSize(connectionPool.maxSize)
                .maxCreateConnectionTime(connectionPool.maxCreateConnectionTime)
                .build()

            return ConnectionPool(configuration)
        }
    }
}
