package com.sylvara.infrastructure.http.controllers


import com.sylvara.application.usecases.GetHomeDataUseCase
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*

class HomeController(
    private val getHomeDataUseCase: GetHomeDataUseCase
) {
    suspend fun getHomeData(call: ApplicationCall) {
        try {
            // TODO: Obtener userId del JWT cuando implementes autenticación
            val userId = 1 // Temporal para pruebas

            val homeData = getHomeDataUseCase.execute(userId)

            call.respond(
                status = HttpStatusCode.OK,
                message = mapOf(
                    "success" to true,
                    "data" to homeData
                )
            )
        } catch (e: Exception) {
            call.application.log.error("Error en getHomeData", e)
            call.respond(
                status = HttpStatusCode.InternalServerError,
                message = mapOf(
                    "success" to false,
                    "error" to e.message
                )
            )
        }
    }
}