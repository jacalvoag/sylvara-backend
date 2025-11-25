package com.sylvara.domain.model

import java.time.LocalDateTime

data class Project(
    val projectId: Int? = null,
    val userId: Int,
    val projectName: String,
    val projectStatus: String = "Activo",
    val projectDescription: String? = null,
    val createdAt: LocalDateTime = LocalDateTime.now()
) {
    init {
        require(projectName.isNotBlank()) { "El nombre del proyecto no puede estar vacío" }
        require(projectStatus in listOf("Activo", "Terminado")) {
            "Estado inválido"
        }
    }
}