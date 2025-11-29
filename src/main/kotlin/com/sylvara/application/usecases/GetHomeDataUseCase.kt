package com.sylvara.application.usecases

import com.sylvara.domain.ports.output.ProjectRepository
import com.sylvara.domain.ports.output.StudyZoneRepository
import com.sylvara.infrastructure.http.response.HomeResponse
import com.sylvara.infrastructure.http.response.ProjectSummary
import java.time.LocalDateTime

class GetHomeDataUseCase(
    private val projectRepository: ProjectRepository,
    private val studyZoneRepository: StudyZoneRepository
) {
    suspend fun execute(userId: Int): HomeResponse {
        // 1. Obtener proyectos activos
        val activeProjects = projectRepository.findByUserId(userId)
            .filter { it.projectStatus == "Activo" }

        // 2. Mapear a ProjectSummary con conteo de zonas
        val projectSummaries = activeProjects.map { project ->
            val studyZones = studyZoneRepository.findByProjectId(project.projectId!!)
            ProjectSummary(
                projectName = project.projectName,
                projectStatus = project.projectStatus,
                totalStudyZones = studyZones.size
            )
        }

        // 3. Total de proyectos registrados
        val totalProjects = projectRepository.findByUserId(userId).size

        // 4. Proyectos de este mes
        val currentMonth = LocalDateTime.now().month
        val currentYear = LocalDateTime.now().year
        val projectsThisMonth = projectRepository.findByUserId(userId)
            .count {
                it.createdAt.month == currentMonth &&
                        it.createdAt.year == currentYear
            }

        return HomeResponse(
            activeProjects = projectSummaries,
            totalProjects = totalProjects,
            projectsThisMonth = projectsThisMonth
        )
    }
}