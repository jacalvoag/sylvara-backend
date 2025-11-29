package com.sylvara.infrastructure.http.response

import kotlinx.serialization.Serializable
import java.math.BigDecimal

@Serializable
data class ProjectDetailsResponse(
    val projectName: String,
    val projectDescription: String?,
    val projectStatus: String,
    val studyZones: List<StudyZoneSummary>
)

@Serializable
data class StudyZoneSummary(
    val studyZoneId: Int,
    val studyZoneName: String,
    val squareArea: String // BigDecimal convertido a String para JSON
)

@Serializable
data class CreateStudyZoneResponse(
    val studyZoneId: Int,
    val studyZoneName: String,
    val studyZoneDescription: String?,
    val squareArea: String,
    val message: String
)