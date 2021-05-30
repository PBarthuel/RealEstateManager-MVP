package com.openclassrooms.realestatemanager.data.models.exceptions

import com.openclassrooms.realestatemanager.data.models.domainMappingProtocols.DomainExceptionConvertible
import com.openclassrooms.realestatemanager.domain.models.DomainException
import com.openclassrooms.realestatemanager.domain.models.DomainLocationException
import com.openclassrooms.realestatemanager.domain.models.DomainNetworkException
import com.openclassrooms.realestatemanager.domain.models.DomainPermissionException
import com.openclassrooms.realestatemanager.domain.models.error.DomainError

sealed class DataException : Exception(), DomainExceptionConvertible
data class DataAPIDecodeException(override val message: String) : DataException() {
    override fun toDomain(): DomainException =
        DomainNetworkException.InternalError(DomainError((message)))
}
sealed class DataPermissionException(override val message: String) : DataException() {
    data class ReportError(override val message: String) : DataPermissionException(message)

    override fun toDomain(): DomainException = when (this) {
        is ReportError -> DomainPermissionException.ReportError(message)
    }
}
sealed class DataUserLocationException(override val message: String) : DataException() {
    data class EmptyLocationError(override val message: String) : DataUserLocationException(message)

    override fun toDomain(): DomainException = when (this) {
        is EmptyLocationError -> DomainLocationException.EmptyLocationError(message)
    }
}