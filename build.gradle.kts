plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.ktor)
    alias(libs.plugins.kotlin.plugin.serialization)
    id("com.gradleup.shadow") version "9.2.2"
}

group = "com.sylvara"
version = "0.0.1"

val ktor_version = "3.3.2"

application {
    mainClass = "com.sylvara.ApplicationKt"
}

tasks{
    shadowJar {
        //Esto evita conflictos con nombres de archivos duplicados en librerias
        mergeServiceFiles()
        manifest {
            attributes(mapOf("Main-Class" to "com.example.Applicationkt"))
        }
        //Opcional: Nombre fijo para no tener versiones en el nombre del archivo
        archiveFileName.set("sylvara.jar")
    }
}

dependencies {
    implementation(libs.koin.ktor)
    implementation(libs.koin.logger.slf4j)
    implementation(libs.ktor.server.core)
    implementation(libs.ktor.serialization.kotlinx.json)
    implementation(libs.ktor.server.content.negotiation)
    implementation(libs.exposed.core)
    implementation(libs.exposed.jdbc)
    implementation(libs.h2)
    implementation(libs.ktor.server.host.common)
    implementation(libs.ktor.server.status.pages)
    implementation(libs.ktor.server.call.logging)
    implementation(libs.ktor.server.cors)
    implementation(libs.ktor.server.auth)
    implementation(libs.ktor.server.auth.jwt)
    implementation(libs.ktor.server.netty)
    implementation(libs.logback.classic)
    testImplementation(libs.ktor.server.test.host)
    testImplementation(libs.kotlin.test.junit)

    //Dependencias añadidas
    implementation("org.postgresql:postgresql:42.7.8")
    implementation("com.zaxxer:HikariCP:7.0.2")
}
