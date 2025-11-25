package com.sylvara.domain.model

import java.time.LocalDate
import java.time.LocalDateTime

data class User(
    val userId: Int? = null,
    val userName: String,
    val userLastname: String,
    val userBirthday: LocalDate,
    val userEmail: String,
    val userPassword: String,
    val createdAt: LocalDateTime = LocalDateTime.now()
) {
    init {
        require(userName.isNotBlank()) { "El nombre no puede estar vacío" }
        require(userLastname.isNotBlank()) { "El apellido no puede estar vacío" }
        require(userEmail.contains("@")) { "Email inválido" }
        require(userPassword.length >= 8) { "La contraseña debe tener al menos 8 caracteres" }
    }
}