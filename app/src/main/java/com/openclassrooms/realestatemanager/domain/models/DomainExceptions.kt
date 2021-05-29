package com.openclassrooms.realestatemanager.domain.models
/**
 * Enum used to define all DomainExceptions used in Domain. The conversion is made from the Data layer
 * @see com.openclassrooms.realestatemanager.data.models.domainMappingProtocols.DomainExceptionConvertible
 */
sealed class DomainException : Exception()
sealed class DomainFormException(override val message: String) : DomainException() {
    data class WrongTypeFormat(override val message: String) : DomainFormException(message)
    data class WrongPriceFormat(override val message: String) : DomainFormException(message)
    data class WrongSurfaceFormat(override val message: String) : DomainFormException(message)
    data class WrongDescriptionFormat(override val message: String) : DomainFormException(message)
    data class WrongAgentFormat(override val message: String) : DomainFormException(message)
    data class WrongTotalRoomNumberFormat(override val message: String) : DomainFormException(message)
    data class WrongBedRoomNumberFormat(override val message: String) : DomainFormException(message)
    data class WrongBathRoomNumberFormat(override val message: String) : DomainFormException(message)
    data class WrongCountryFormat(override val message: String) : DomainFormException(message)
    data class WrongRoadFormat(override val message: String) : DomainFormException(message)
    data class WrongHouseNumberFormat(override val message: String) : DomainFormException(message)
    data class WrongCityFormat(override val message: String) : DomainFormException(message)
    data class WrongPostalCodeFormat(override val message: String) : DomainFormException(message)
}
