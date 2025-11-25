package com.sylvara.bootstrap

import com.sylvara.com.sylvara.bootstrap.configureFrameworks
import com.sylvara.com.sylvara.bootstrap.configureRouting
import com.sylvara.com.sylvara.bootstrap.configureSecurity
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*

fun main() {
    embeddedServer(
        factory = Netty,
        port = 8080,
        host = "0.0.0.0",
        module = Application::module
    ).start(wait = true)
}

fun Application.module() {
    configureDatabases()
    configurePlugins()
    configureSecurity()
    configureFrameworks()
    configureRouting()
}