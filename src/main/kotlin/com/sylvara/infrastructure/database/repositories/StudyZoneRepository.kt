package com.sylvara.infrastructure.database.repositories

import com.sylvara.domain.model.StudyZone
import com.sylvara.domain.ports.output.StudyZoneRepository
import com.sylvara.com.sylvara.infrastructure.database.entities.StudyZones
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction

class StudyZoneRepository : StudyZoneRepository {

    override suspend fun save(studyZone: StudyZone): StudyZone = transaction {
        val id = StudyZones.insertAndGetId {
            it[projectId] = studyZone.projectId
            it[studyZoneName] = studyZone.studyZoneName
            it[studyZoneDescription] = studyZone.studyZoneDescription
            it[squareArea] = studyZone.squareArea
            it[createdAt] = studyZone.createdAt
        }
        studyZone.copy(studyZoneId = id.value)
    }

    override suspend fun findById(id: Int): StudyZone? = transaction {
        StudyZones.selectAll()
            .where { StudyZones.id eq id }
            .map { toStudyZone(it) }
            .singleOrNull()
    }

    override suspend fun findByProjectId(projectId: Int): List<StudyZone> = transaction {
        StudyZones.selectAll()
            .where { StudyZones.projectId eq projectId }
            .map { toStudyZone(it) }
    }

    override suspend fun findAll(): List<StudyZone> = transaction {
        StudyZones.selectAll().map { toStudyZone(it) }
    }

    override suspend fun update(studyZone: StudyZone): StudyZone = transaction {
        StudyZones.update({ StudyZones.id eq studyZone.studyZoneId!! }) {
            it[studyZoneName] = studyZone.studyZoneName
            it[studyZoneDescription] = studyZone.studyZoneDescription
            it[squareArea] = studyZone.squareArea
        }
        studyZone
    }

    override suspend fun delete(id: Int) = transaction {
        StudyZones.deleteWhere { StudyZones.id eq id }
        Unit
    }

    private fun toStudyZone(row: ResultRow) = StudyZone(
        studyZoneId = row[StudyZones.id].value,
        projectId = row[StudyZones.projectId],
        studyZoneName = row[StudyZones.studyZoneName],
        studyZoneDescription = row[StudyZones.studyZoneDescription],
        squareArea = row[StudyZones.squareArea],
        createdAt = row[StudyZones.createdAt]
    )
}