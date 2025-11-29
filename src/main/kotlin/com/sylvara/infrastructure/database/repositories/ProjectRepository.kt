package com.sylvara.infrastructure.database.repositories

import com.sylvara.domain.model.Project
import com.sylvara.domain.ports.output.ProjectRepository
import com.sylvara.com.sylvara.infrastructure.database.entities.Projects
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq

class ProjectRepository : ProjectRepository {

    override suspend fun save(project: Project): Project = transaction {
        val id = Projects.insertAndGetId {
            it[userId] = project.userId
            it[projectName] = project.projectName
            it[projectStatus] = project.projectStatus
            it[projectDescription] = project.projectDescription
            it[createdAt] = project.createdAt
        }
        project.copy(projectId = id.value)
    }

    override suspend fun findById(id: Int): Project? = transaction {
        Projects.selectAll()
            .where { Projects.id eq id }
            .map { toProject(it) }
            .singleOrNull()
    }

    override suspend fun findByUserId(userId: Int): List<Project> = transaction {
        Projects.selectAll()
            .where { Projects.userId eq userId }
            .map { toProject(it) }
    }

    override suspend fun findAll(): List<Project> = transaction {
        Projects.selectAll().map { toProject(it) }
    }

    override suspend fun update(project: Project): Project = transaction {
        Projects.update({ Projects.id eq project.projectId!! }) {
            it[projectName] = project.projectName
            it[projectStatus] = project.projectStatus
            it[projectDescription] = project.projectDescription
        }
        project
    }

    override suspend fun delete(id: Int) = transaction {
        Projects.deleteWhere { Projects.id eq id }
        Unit
    }

    private fun toProject(row: ResultRow) = Project(
        projectId = row[Projects.id].value,
        userId = row[Projects.userId],
        projectName = row[Projects.projectName],
        projectStatus = row[Projects.projectStatus],
        projectDescription = row[Projects.projectDescription],
        createdAt = row[Projects.createdAt]
    )
}