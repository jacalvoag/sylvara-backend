package com.sylvara.infrastructure.di

import com.sylvara.application.usecases.GetHomeDataUseCase
import com.sylvara.infrastructure.database.repositories.ProjectRepository
import com.sylvara.infrastructure.database.repositories.StudyZoneRepository
import com.sylvara.infrastructure.http.controllers.HomeController
import org.koin.dsl.module

val appModule = module {
    // Repositories
    single<ProjectRepository> { ProjectRepository() }
    single<StudyZoneRepository> { StudyZoneRepository() }

    // Use Cases
    single { GetHomeDataUseCase(get(), get()) }

    // Controllers
    single { HomeController(get()) }
}