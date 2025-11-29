package com.sylvara.application.usecases


import com.sylvara.domain.model.Project
import com.sylvara.domain.model.StudyZone
import com.sylvara.domain.ports.output.ProjectRepository
import com.sylvara.domain.ports.output.StudyZoneRepository
import com.sylvara.infrastructure.http.response.ProjectDetailsResponse
import com.sylvara.infrastructure.http.response.StudyZoneSummary
import java.math.BigDecimal
import java.time.LocalDateTime

// Get
class GetProjectDetailsUseCase(
    private val projectRepository: ProjectRepository,
    private val studyZoneRepository: StudyZoneRepository
) {
    suspend fun execute(projectId: Int, userId: Int): ProjectDetailsResponse? {
        // 1. Verificar que el proyecto existe y pertenece al usuario
        val project = projectRepository.findById(projectId) ?: return null
        if (project.userId != userId) {
            throw IllegalAccessException("No tienes acceso a este proyecto")
        }
        // 2. Obtener las zonas de estudio del proyecto
        val studyZones = studyZoneRepository.findByProjectId(projectId)
        // 3. Mapear a datos
        val studyZoneSummaries = studyZones.map { zone ->
            StudyZoneSummary(
                studyZoneId = zone.studyZoneId!!,
                studyZoneName = zone.studyZoneName,
                squareArea = zone.squareArea.toString()
            )
        }
        return ProjectDetailsResponse(
            projectName = project.projectName,
            projectDescription = project.projectDescription,
            projectStatus = project.projectStatus,
            studyZones = studyZoneSummaries
        )
    }
}

// Post
class CreateStudyZoneUseCase(
    private val projectRepository: ProjectRepository,
    private val studyZoneRepository: StudyZoneRepository
) {
    suspend fun execute(
        projectId: Int,
        userId: Int,
        studyZoneName: String,
        studyZoneDescription: String?,
        squareArea: String
    ): StudyZone {
        // 1. Verificar que el proyecto existe y pertenece al usuario
        val project = projectRepository.findById(projectId)
            ?: throw IllegalArgumentException("Proyecto no encontrado")
        if (project.userId != userId) {
            throw IllegalAccessException("No tienes acceso a este proyecto")
        }
        // 2. Validar y convertir el área
        val areaDecimal = try {
            BigDecimal(squareArea)
        } catch (e: NumberFormatException) {
            throw IllegalArgumentException("El área debe ser un número válido")
        }
        if (areaDecimal <= BigDecimal.ZERO) {
            throw IllegalArgumentException("El área debe ser mayor a 0")
        }
        // 3. Crear la zona de estudio
        val newStudyZone = StudyZone(
            projectId = projectId,
            studyZoneName = studyZoneName,
            studyZoneDescription = studyZoneDescription,
            squareArea = areaDecimal,
            createdAt = LocalDateTime.now()
        )
        return studyZoneRepository.save(newStudyZone)
    }
}

// Put
class UpdateProjectUseCase(
    private val projectRepository: ProjectRepository
) {
    suspend fun execute(
        projectId: Int,
        userId: Int,
        projectName: String,
        projectDescription: String?
    ): Project {
        // 1. Verificar que el proyecto existe y pertenece al usuario
        val project = projectRepository.findById(projectId)
            ?: throw IllegalArgumentException("Proyecto no encontrado")
        if (project.userId != userId) {
            throw IllegalAccessException("No tienes acceso a este proyecto")
        }
        // 2. Actualizar el proyecto
        val updatedProject = project.copy(
            projectName = projectName,
            projectDescription = projectDescription
        )
        return projectRepository.update(updatedProject)
    }
}

// Delete
class DeleteStudyZoneUseCase(
    private val projectRepository: ProjectRepository,
    private val studyZoneRepository: StudyZoneRepository
) {
    suspend fun execute(studyZoneId: Int, userId: Int) {
        // 1. Verificar que la zona existe
        val studyZone = studyZoneRepository.findById(studyZoneId)
            ?: throw IllegalArgumentException("Zona de estudio no encontrada")
        // 2. Verificar que el proyecto pertenece al usuario
        val project = projectRepository.findById(studyZone.projectId)
            ?: throw IllegalArgumentException("Proyecto no encontrado")
        if (project.userId != userId) {
            throw IllegalAccessException("No tienes acceso a esta zona de estudio")
        }
        // 3. Eliminar la zona
        studyZoneRepository.delete(studyZoneId)
    }
}