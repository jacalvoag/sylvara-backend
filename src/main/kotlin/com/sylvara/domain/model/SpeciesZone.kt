package com.sylvara.domain.model

import java.time.LocalDateTime

data class SpeciesZone(
    val speciesZoneId: Int? = null,
    val speciesId: Int,
    val studyZoneId: Int,
    val samplingUnit: String,
    val individualCount: Int,
    val heightStratum: String,
    val createdAt: LocalDateTime = LocalDateTime.now(),
    val updatedAt: LocalDateTime = LocalDateTime.now()
) {
    init {
        require(samplingUnit.isNotBlank()) { "La unidad de muestreo no puede estar vacía" }
        require(individualCount >= 0) { "El conteo no puede ser negativo" }
        require(heightStratum.isNotBlank()) { "El estrato de altura no puede estar vacío" }
    }
}