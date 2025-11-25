package com.sylvara.domain.ports.output

import com.sylvara.domain.model.StudyZone

interface StudyZoneRepository {
    suspend fun save(studyZone: StudyZone): StudyZone
    suspend fun findById(id: Int): StudyZone?
    suspend fun findByProjectId(projectId: Int): List<StudyZone>
    suspend fun findAll(): List<StudyZone>
    suspend fun update(studyZone: StudyZone): StudyZone
    suspend fun delete(id: Int)
}