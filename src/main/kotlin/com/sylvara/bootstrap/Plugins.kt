package com.sylvara.bootstrap

import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.plugins.calllogging.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.plugins.cors.routing.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.request.httpMethod
import io.ktor.server.request.path
import io.ktor.server.response.*
import kotlinx.serialization.json.Json
import org.slf4j.event.Level

fun Application.configurePlugins() {
    install(ContentNegotiation) {
        json(Json {
            prettyPrint = true
            isLenient = true
            ignoreUnknownKeys = true
            explicitNulls = false
        })
    }

    install(CORS) {
        // Métodos HTTP permitidos
        allowMethod(HttpMethod.Options)
        allowMethod(HttpMethod.Get)
        allowMethod(HttpMethod.Post)
        allowMethod(HttpMethod.Put)
        allowMethod(HttpMethod.Delete)
        allowMethod(HttpMethod.Patch)

        // Headers permitidos
        allowHeader(HttpHeaders.Authorization)
        allowHeader(HttpHeaders.ContentType)
        allowHeader(HttpHeaders.AccessControlAllowOrigin)

        // Headers que se exponen al cliente
        exposeHeader(HttpHeaders.Authorization)

        // Permite cookies/credenciales
        allowCredentials = true

        // Permite cualquier host (SOLO PARA DESARROLLO)
        anyHost()

        // Tiempo de caché de la configuración CORS (en segundos)
        maxAgeInSeconds = 3600
    }

    install(CallLogging) {
        level = Level.INFO

        // Filtrar qué peticiones se loggean
        filter { call ->
            call.request.path().startsWith("/api")
        }

        // Formato del log
        format { call ->
            val status = call.response.status()
            val httpMethod = call.request.httpMethod.value
            val userAgent = call.request.headers["User-Agent"]
            val path = call.request.path()

            "Status: $status, HTTP method: $httpMethod, Path: $path, User agent: $userAgent"
        }
    }

    install(StatusPages) {
        exception<Throwable> { call, cause ->
            call.application.log.error("Unhandled exception", cause)

            // Respuesta al cliente
            call.respond(
                status = HttpStatusCode.InternalServerError,
                message = mapOf(
                    "success" to false,
                    "error" to "Error interno del servidor",
                    "message" to (cause.message ?: "Error desconocido"),
                    // Solo en desarrollo, NO en producción
                    "stackTrace" to cause.stackTraceToString().take(500)
                )
            )
        }

        // Manejo específico de IllegalArgumentException
        exception<IllegalArgumentException> { call, cause ->
            call.respond(
                status = HttpStatusCode.BadRequest,
                message = mapOf(
                    "success" to false,
                    "error" to "Solicitud inválida",
                    "message" to (cause.message ?: "Parámetros incorrectos")
                )
            )
        }

        // Manejo específico de NullPointerException
        exception<NullPointerException> { call, cause ->
            call.respond(
                status = HttpStatusCode.NotFound,
                message = mapOf(
                    "success" to false,
                    "error" to "Recurso no encontrado",
                    "message" to "El recurso solicitado no existe"
                )
            )
        }

        // Manejo de errores 404 (ruta no encontrada)
        status(HttpStatusCode.NotFound) { call, status ->
            call.respond(
                status = status,
                message = mapOf(
                    "success" to false,
                    "error" to "Ruta no encontrada",
                    "message" to "La ruta ${call.request.path()} no existe"
                )
            )
        }

        // Manejo de errores 401 (no autorizado)
        status(HttpStatusCode.Unauthorized) { call, status ->
            call.respond(
                status = status,
                message = mapOf(
                    "success" to false,
                    "error" to "No autorizado",
                    "message" to "Debes iniciar sesión para acceder a este recurso"
                )
            )
        }
    }
}