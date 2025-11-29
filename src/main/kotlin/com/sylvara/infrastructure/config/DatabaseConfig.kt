package com.sylvara.infrastructure.config

import com.sylvara.com.sylvara.infrastructure.database.entities.FunctionalTypes
import com.sylvara.com.sylvara.infrastructure.database.entities.Projects
import com.sylvara.com.sylvara.infrastructure.database.entities.Species
import com.sylvara.com.sylvara.infrastructure.database.entities.SpeciesZones
import com.sylvara.com.sylvara.infrastructure.database.entities.StudyZones
import com.sylvara.com.sylvara.infrastructure.database.entities.Users
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction

object DatabaseConfig {

    fun init(): Database {
        println("🔌 Conectando a PostgreSQL (Sylvara)...")

        val dbHost = System.getenv("DB_HOST") ?: "localhost"
        val dbPort = System.getenv("DB_PORT") ?: "5432"
        val dbName = System.getenv("DB_NAME") ?: "sylvara_db"
        val dbUser = System.getenv("DB_USER") ?: "postgres"
        val dbPassword = System.getenv("DB_PASSWORD") ?: "ajstyles2006"

        val jdbcUrl = "jdbc:postgresql://$dbHost:$dbPort/$dbName"

        val database = Database.connect(
            url = jdbcUrl,
            driver = "org.postgresql.Driver",
            user = dbUser,
            password = dbPassword
        )

        // Crear tablas si no existen
        transaction {
            SchemaUtils.create(
                Users,
                Projects,
                StudyZones,
                FunctionalTypes,
                Species,
                SpeciesZones
            )
            println("✅ Tablas verificadas/creadas exitosamente")
        }

        println("✅ PostgreSQL conectado exitosamente")

        return database
    }
}