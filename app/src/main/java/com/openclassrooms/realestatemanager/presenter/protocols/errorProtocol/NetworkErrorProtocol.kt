package com.openclassrooms.realestatemanager.presenter.protocols.errorProtocol

import android.view.ViewGroup
import com.openclassrooms.realestatemanager.domain.models.DomainNetworkException
import com.openclassrooms.realestatemanager.domain.utils.toDomainExceptionType
import com.openclassrooms.realestatemanager.presenter.models.uiErrorItem.UIErrorItem
import com.openclassrooms.realestatemanager.presenter.models.uiErrorItem.toUIItem

/**
 * Interface to implement callbacks when DomainNetworkException are triggered
 */
interface NetworkErrorProtocol : ErrorProtocol {
    val rootView: ViewGroup?
    
    fun onReceiveInternalError(errorItem: UIErrorItem? = null) {}

    override fun onReceiveError(exception: Throwable) {
        when (val domainException = exception.toDomainExceptionType() as? DomainNetworkException) {
            is DomainNetworkException.InternalError -> onReceiveInternalError(domainException.domainError.toUIItem())
        }
    }
}
