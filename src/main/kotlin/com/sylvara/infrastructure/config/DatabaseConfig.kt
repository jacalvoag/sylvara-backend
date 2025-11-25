package com.sylvara.infrastructure.config

import com.sylvara.infrastructure.database.entities.*
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction

object DatabaseConfig {

    fun init(): Database {
        println("🔌 Conectando a PostgreSQL (Sylvara)...")

        // Variables de entorno con fallback para desarrollo local
        val dbHost = System.getenv("DB_HOST") ?: "localhost"
        val dbPort = System.getenv("DB_PORT") ?: "5432"
        val dbName = System.getenv("DB_NAME") ?: "sylvara_db"
        val dbUser = System.getenv("DB_USER") ?: "postgres"
        val dbPassword = System.getenv("DB_PASSWORD") ?: "ajstyles2006"

        val jdbcUrl = "jdbc:postgresql://$dbHost:$dbPort/$dbName"

        println("Host: $dbHost:$dbPort")
        println("Database: $dbName")

        val database = Database.connect(
            url = jdbcUrl,
            driver = "org.postgresql.Driver",
            user = dbUser,
            password = dbPassword
        )

        println("PostgreSQL conectado exitosamente")

        return database
    }
}