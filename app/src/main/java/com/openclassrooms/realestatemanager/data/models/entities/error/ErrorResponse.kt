package com.openclassrooms.realestatemanager.data.models.entities.error

import com.openclassrooms.realestatemanager.data.models.domainMappingProtocols.DomainModelConvertible
import com.openclassrooms.realestatemanager.domain.models.error.DomainError
import kotlinx.serialization.Serializable

@Serializable
data class ErrorResponse(
    val error: String,
    val data: Map<String, String>? = null
) : DomainModelConvertible<DomainError> {

    override fun toDomain(): DomainError =
        DomainError(error, data)
}