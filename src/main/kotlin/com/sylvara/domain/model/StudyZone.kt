package com.sylvara.domain.model

import java.math.BigDecimal
import java.time.LocalDateTime

data class StudyZone(
    val studyZoneId: Int? = null,
    val projectId: Int,
    val studyZoneName: String,
    val studyZoneDescription: String? = null,
    val squareArea: BigDecimal,
    val createdAt: LocalDateTime = LocalDateTime.now()
) {
    init {
        require(studyZoneName.isNotBlank()) { "El nombre de la zona no puede estar vacío" }
        require(squareArea > BigDecimal.ZERO) { "El área debe ser mayor a 0" }
    }
}