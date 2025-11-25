package com.sylvara.domain.ports.output

import com.sylvara.domain.model.Species

interface SpeciesRepository {
    suspend fun save(species: Species): Species
    suspend fun findById(id: Int): Species?
    suspend fun findByProjectId(projectId: Int): List<Species>
    suspend fun findByNameAndProject(name: String, projectId: Int): Species?
    suspend fun findAll(): List<Species>
    suspend fun update(species: Species): Species
    suspend fun delete(id: Int)
}