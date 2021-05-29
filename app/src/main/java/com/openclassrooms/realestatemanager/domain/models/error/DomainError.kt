package com.openclassrooms.realestatemanager.domain.models.error

typealias DomainDataError = Map<String, String>?

data class DomainError(
    val errorKey: String,
    val data: DomainDataError = null
)