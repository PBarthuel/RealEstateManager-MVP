package com.openclassrooms.realestatemanager.domain.models

import com.openclassrooms.realestatemanager.domain.models.error.DomainError

/**
 * Enum used to define all DomainExceptions used in Domain. The conversion is made from the Data layer
 * @see com.openclassrooms.realestatemanager.data.models.domainMappingProtocols.DomainExceptionConvertible
 */
sealed class DomainException : Exception()
sealed class DomainNetworkException(open val domainError: DomainError) : DomainException() {
    data class InternalError(override val domainError: DomainError) : DomainNetworkException(domainError)
}
sealed class DomainFormException(override val message: String) : DomainException() {
    data class WrongTypeFormat(override val message: String) : DomainFormException(message)
    data class WrongPriceFormat(override val message: String) : DomainFormException(message)
    data class WrongSurfaceFormat(override val message: String) : DomainFormException(message)
    data class WrongDescriptionFormat(override val message: String) : DomainFormException(message)
    data class WrongAgentFormat(override val message: String) : DomainFormException(message)
    data class WrongTotalRoomNumberFormat(override val message: String) : DomainFormException(message)
    data class WrongBedRoomNumberFormat(override val message: String) : DomainFormException(message)
    data class WrongBathRoomNumberFormat(override val message: String) : DomainFormException(message)
}
sealed class DomainLocationException(override val message: String) : DomainException() {
    data class EmptyLocationError(override val message: String) : DomainLocationException(message)
}
