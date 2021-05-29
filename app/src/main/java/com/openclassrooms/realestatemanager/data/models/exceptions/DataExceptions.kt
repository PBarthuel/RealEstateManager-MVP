package com.openclassrooms.realestatemanager.data.models.exceptions

import com.openclassrooms.realestatemanager.data.models.domainMappingProtocols.DomainExceptionConvertible
import com.openclassrooms.realestatemanager.domain.models.DomainException
import com.openclassrooms.realestatemanager.domain.models.DomainNetworkException
import com.openclassrooms.realestatemanager.domain.models.error.DomainError

sealed class DataException : Exception(), DomainExceptionConvertible
data class DataAPIDecodeException(override val message: String) : DataException() {
    override fun toDomain(): DomainException =
        DomainNetworkException.InternalError(DomainError((message)))
}