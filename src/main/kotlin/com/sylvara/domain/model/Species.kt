package com.sylvara.domain.model

import java.time.LocalDateTime

data class Species(
    val speciesId: Int? = null,
    val projectId: Int,
    val speciesName: String,
    val speciesPhoto: String? = null,
    val functionalTypeId: Int,
    val createdAt: LocalDateTime = LocalDateTime.now()
) {
    init {
        require(speciesName.isNotBlank()) { "El nombre de la especie no puede estar vacío" }
    }
}