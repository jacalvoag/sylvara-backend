package com.sylvara.com.sylvara.infrastructure.database.entities

import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.javatime.datetime

object Projects : IntIdTable("projects") {
    val userId = integer("user_id")
    val projectName = varchar("project_name", 255)
    val projectStatus = varchar("project_status", 50).default("Activo")
    val projectDescription = text("project_description").nullable()
    val createdAt = datetime("created_at")
}
