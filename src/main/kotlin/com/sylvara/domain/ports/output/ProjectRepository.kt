package com.sylvara.domain.ports.output

import com.sylvara.domain.model.Project

interface ProjectRepository {
    suspend fun save(project: Project): Project
    suspend fun findById(id: Int): Project?
    suspend fun findByUserId(userId: Int): List<Project>
    suspend fun findAll(): List<Project>
    suspend fun update(project: Project): Project
    suspend fun delete(id: Int)
}