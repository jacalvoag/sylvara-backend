package com.sylvara.domain.model

data class FunctionalType(
    val functionalTypeId: Int? = null,
    val functionalTypeName: String
) {
    init {
        require(functionalTypeName.isNotBlank()) { "El tipo funcional no puede estar vacío" }
    }
}