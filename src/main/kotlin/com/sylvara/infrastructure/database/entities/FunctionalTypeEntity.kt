package com.sylvara.com.sylvara.infrastructure.database.entities


import org.jetbrains.exposed.dao.id.IntIdTable

object FunctionalTypes : IntIdTable("functional_types") {
    val functionalTypeName = varchar("functional_type_name", 100).uniqueIndex()
}