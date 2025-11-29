package com.sylvara.infrastructure.http.response

import kotlinx.serialization.Serializable

@Serializable
data class HomeResponse(
    val activeProjects: List<ProjectSummary>,
    val totalProjects: Int,
    val projectsThisMonth: Int
)

@Serializable
data class ProjectSummary(
    val projectName: String,
    val projectStatus: String,
    val totalStudyZones: Int
)