package com.openclassrooms.realestatemanager.presenter.protocols.errorProtocol

import com.openclassrooms.realestatemanager.domain.models.DomainFormException
import com.openclassrooms.realestatemanager.domain.utils.toDomainExceptionType

/**
 * Interface to implement callbacks when DomainFormException are triggered
 */
interface FormErrorProtocol : ErrorProtocol {
    fun onReceiveWrongTypeFormatError() {}
    fun onReceiveWrongPriceFormatError() {}
    fun onReceiveWrongSurfaceFormatError() {}
    fun onReceiveWrongDescriptionFormatError() {}
    fun onReceiveWrongAgentFormatError() {}
    fun onReceiveWrongTotalRoomNumberFormatError() {}
    fun onReceiveWrongBedroomNumberFormatError() {}
    fun onReceiveWrongBathroomNumberFormatError() {}
    fun onReceiveWrongCountryFormatError() {}
    fun onReceiveWrongRoadFormatError() {}
    fun onReceiveWrongHouseNumberFormatError() {}
    fun onReceiveWrongCityFormatError() {}
    fun onReceiveWrongPostalCodeFormatError() {}

    override fun onReceiveError(exception: Throwable) {
        when (exception.toDomainExceptionType() as? DomainFormException) {
            is DomainFormException.WrongAgentFormat -> onReceiveWrongAgentFormatError()
            is DomainFormException.WrongBathRoomNumberFormat -> onReceiveWrongBathroomNumberFormatError()
            is DomainFormException.WrongBedRoomNumberFormat -> onReceiveWrongBedroomNumberFormatError()
            is DomainFormException.WrongDescriptionFormat -> onReceiveWrongDescriptionFormatError()
            is DomainFormException.WrongPriceFormat -> onReceiveWrongPriceFormatError()
            is DomainFormException.WrongSurfaceFormat -> onReceiveWrongSurfaceFormatError()
            is DomainFormException.WrongTotalRoomNumberFormat -> onReceiveWrongTotalRoomNumberFormatError()
            is DomainFormException.WrongTypeFormat -> onReceiveWrongTypeFormatError()
        }
    }
}