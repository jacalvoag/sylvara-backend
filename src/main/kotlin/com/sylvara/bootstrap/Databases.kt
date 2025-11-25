package com.sylvara.bootstrap

import com.sylvara.infrastructure.config.DatabaseConfig
import io.ktor.server.application.*

fun Application.configureDatabases() {
    DatabaseConfig.init()
}