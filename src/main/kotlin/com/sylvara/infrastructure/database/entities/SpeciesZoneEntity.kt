package com.sylvara.com.sylvara.infrastructure.database.entities


import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.javatime.datetime

object SpeciesZones : IntIdTable("species_zones") {
    val speciesId = integer("species_id")
    val studyZoneId = integer("study_zone_id")
    val samplingUnit = varchar("sampling_unit", 100)
    val individualCount = integer("individual_count")
    val heightStratum = varchar("height_stratum", 100)
    val createdAt = datetime("created_at")
    val updatedAt = datetime("updated_at")
}