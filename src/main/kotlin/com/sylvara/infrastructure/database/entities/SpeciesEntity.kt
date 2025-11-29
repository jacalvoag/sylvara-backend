package com.sylvara.com.sylvara.infrastructure.database.entities


import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.javatime.datetime

object Species : IntIdTable("species") {
    val projectId = integer("project_id")
    val speciesName = varchar("species_name", 255)
    val speciesPhoto = varchar("species_photo", 500).nullable()
    val functionalTypeId = integer("functional_type_id")
    val createdAt = datetime("created_at")
}