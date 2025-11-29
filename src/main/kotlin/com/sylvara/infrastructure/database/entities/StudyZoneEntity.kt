package com.sylvara.com.sylvara.infrastructure.database.entities

import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.javatime.datetime

object StudyZones : IntIdTable("study_zones") {
    val projectId = integer("project_id")
    val studyZoneName = varchar("study_zone_name", 255)
    val studyZoneDescription = text("study_zone_description").nullable()
    val squareArea = decimal("square_area", 10, 2)
    val createdAt = datetime("created_at")
}
