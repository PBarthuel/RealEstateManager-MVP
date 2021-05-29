package com.openclassrooms.realestatemanager.data.models.domainMappingProtocols

import com.openclassrooms.realestatemanager.domain.models.DomainException


/**
 * Interface used to implement toDomainExceptionType for Exceptions triggered from the Data
 * @see com.openclassrooms.realestatemanager.domain.models.DomainException
 */
interface DomainExceptionConvertible {
    fun toDomain(): DomainException
}