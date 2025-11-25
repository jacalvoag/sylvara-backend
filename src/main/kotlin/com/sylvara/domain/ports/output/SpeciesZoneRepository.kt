package com.sylvara.domain.ports.output

import com.sylvara.domain.model.SpeciesZone

interface SpeciesZoneRepository {
    suspend fun save(speciesZone: SpeciesZone): SpeciesZone
    suspend fun findById(id: Int): SpeciesZone?
    suspend fun findByStudyZoneId(studyZoneId: Int): List<SpeciesZone>
    suspend fun findBySpeciesId(speciesId: Int): List<SpeciesZone>
    suspend fun findAll(): List<SpeciesZone>
    suspend fun update(speciesZone: SpeciesZone): SpeciesZone
    suspend fun delete(id: Int)
}
