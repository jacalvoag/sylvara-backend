package com.sylvara.com.sylvara.infrastructure.database.entities


import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.javatime.date
import org.jetbrains.exposed.sql.javatime.datetime

object Users : IntIdTable("users") {
    val userName = varchar("user_name", 100)
    val userLastname = varchar("user_lastname", 100)
    val userBirthday = date("user_birthday")
    val userEmail = varchar("user_email", 255).uniqueIndex()
    val userPassword = varchar("user_password", 255)
    val createdAt = datetime("created_at")
}