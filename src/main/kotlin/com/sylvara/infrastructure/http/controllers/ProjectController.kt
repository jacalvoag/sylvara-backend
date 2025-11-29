package com.sylvara.infrastructure.http.controllers

import com.sylvara.application.usecases.*
import com.sylvara.infrastructure.http.request.CreateStudyZoneRequest
import com.sylvara.infrastructure.http.request.UpdateProjectRequest
import com.sylvara.infrastructure.http.response.CreateStudyZoneResponse
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*

class ProjectController(
    private val getProjectDetailsUseCase: GetProjectDetailsUseCase,
    private val createStudyZoneUseCase: CreateStudyZoneUseCase,
    private val updateProjectUseCase: UpdateProjectUseCase,
    private val deleteStudyZoneUseCase: DeleteStudyZoneUseCase
) {

    // GET: Obtener detalles del proyecto
    suspend fun getProjectDetails(call: ApplicationCall) {
        try {
            val projectId = call.parameters["projectId"]?.toIntOrNull()
                ?: return call.respond(
                    HttpStatusCode.BadRequest,
                    mapOf("success" to false, "error" to "ID de proyecto inválido")
                )

            // TODO: Obtener userId del JWT
            val userId = 1

            val projectDetails = getProjectDetailsUseCase.execute(projectId, userId)
                ?: return call.respond(
                    HttpStatusCode.NotFound,
                    mapOf("success" to false, "error" to "Proyecto no encontrado")
                )

            call.respond(
                HttpStatusCode.OK,
                mapOf("success" to true, "data" to projectDetails)
            )
        } catch (e: IllegalAccessException) {
            call.respond(
                HttpStatusCode.Forbidden,
                mapOf("success" to false, "error" to e.message)
            )
        } catch (e: Exception) {
            call.application.log.error("Error en getProjectDetails", e)
            call.respond(
                HttpStatusCode.InternalServerError,
                mapOf("success" to false, "error" to e.message)
            )
        }
    }

    // POST: Crear zona de estudio
    suspend fun createStudyZone(call: ApplicationCall) {
        try {
            val projectId = call.parameters["projectId"]?.toIntOrNull()
                ?: return call.respond(
                    HttpStatusCode.BadRequest,
                    mapOf("success" to false, "error" to "ID de proyecto inválido")
                )

            val request = call.receive<CreateStudyZoneRequest>()

            // TODO: Obtener userId del JWT
            val userId = 1

            val studyZone = createStudyZoneUseCase.execute(
                projectId = projectId,
                userId = userId,
                studyZoneName = request.studyZoneName,
                studyZoneDescription = request.studyZoneDescription,
                squareArea = request.squareArea
            )

            val response = CreateStudyZoneResponse(
                studyZoneId = studyZone.studyZoneId!!,
                studyZoneName = studyZone.studyZoneName,
                studyZoneDescription = studyZone.studyZoneDescription,
                squareArea = studyZone.squareArea.toString(),
                message = "Zona de estudio creada exitosamente"
            )

            call.respond(
                HttpStatusCode.Created,
                mapOf("success" to true, "data" to response)
            )
        } catch (e: IllegalArgumentException) {
            call.respond(
                HttpStatusCode.BadRequest,
                mapOf("success" to false, "error" to e.message)
            )
        } catch (e: IllegalAccessException) {
            call.respond(
                HttpStatusCode.Forbidden,
                mapOf("success" to false, "error" to e.message)
            )
        } catch (e: Exception) {
            call.application.log.error("Error en createStudyZone", e)
            call.respond(
                HttpStatusCode.InternalServerError,
                mapOf("success" to false, "error" to e.message)
            )
        }
    }

    // PUT: Actualizar proyecto
    suspend fun updateProject(call: ApplicationCall) {
        try {
            val projectId = call.parameters["projectId"]?.toIntOrNull()
                ?: return call.respond(
                    HttpStatusCode.BadRequest,
                    mapOf("success" to false, "error" to "ID de proyecto inválido")
                )

            val request = call.receive<UpdateProjectRequest>()

            // TODO: Obtener userId del JWT
            val userId = 1

            val updatedProject = updateProjectUseCase.execute(
                projectId = projectId,
                userId = userId,
                projectName = request.projectName,
                projectDescription = request.projectDescription
            )

            call.respond(
                HttpStatusCode.OK,
                mapOf(
                    "success" to true,
                    "message" to "Proyecto actualizado exitosamente",
                    "data" to mapOf(
                        "projectId" to updatedProject.projectId,
                        "projectName" to updatedProject.projectName,
                        "projectDescription" to updatedProject.projectDescription
                    )
                )
            )
        } catch (e: IllegalArgumentException) {
            call.respond(
                HttpStatusCode.BadRequest,
                mapOf("success" to false, "error" to e.message)
            )
        } catch (e: IllegalAccessException) {
            call.respond(
                HttpStatusCode.Forbidden,
                mapOf("success" to false, "error" to e.message)
            )
        } catch (e: Exception) {
            call.application.log.error("Error en updateProject", e)
            call.respond(
                HttpStatusCode.InternalServerError,
                mapOf("success" to false, "error" to e.message)
            )
        }
    }

    // DELETE: Eliminar zona de estudio
    suspend fun deleteStudyZone(call: ApplicationCall) {
        try {
            val studyZoneId = call.parameters["studyZoneId"]?.toIntOrNull()
                ?: return call.respond(
                    HttpStatusCode.BadRequest,
                    mapOf("success" to false, "error" to "ID de zona inválido")
                )

            // TODO: Obtener userId del JWT
            val userId = 1

            deleteStudyZoneUseCase.execute(studyZoneId, userId)

            call.respond(
                HttpStatusCode.OK,
                mapOf(
                    "success" to true,
                    "message" to "Zona de estudio eliminada exitosamente"
                )
            )
        } catch (e: IllegalArgumentException) {
            call.respond(
                HttpStatusCode.BadRequest,
                mapOf("success" to false, "error" to e.message)
            )
        } catch (e: IllegalAccessException) {
            call.respond(
                HttpStatusCode.Forbidden,
                mapOf("success" to false, "error" to e.message)
            )
        } catch (e: Exception) {
            call.application.log.error("Error en deleteStudyZone", e)
            call.respond(
                HttpStatusCode.InternalServerError,
                mapOf("success" to false, "error" to e.message)
            )
        }
    }
}