package com.sylvara.infrastructure.http.request

import kotlinx.serialization.Serializable

@Serializable
data class CreateStudyZoneRequest(
    val studyZoneName: String,
    val studyZoneDescription: String?,
    val squareArea: String
)

@Serializable
data class UpdateProjectRequest(
    val projectName: String,
    val projectDescription: String?
)