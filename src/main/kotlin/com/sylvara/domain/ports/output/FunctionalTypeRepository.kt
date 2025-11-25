package com.sylvara.domain.ports.output

import com.sylvara.domain.model.FunctionalType

interface FunctionalTypeRepository {
    suspend fun findById(id: Int): FunctionalType?
    suspend fun findAll(): List<FunctionalType>
}