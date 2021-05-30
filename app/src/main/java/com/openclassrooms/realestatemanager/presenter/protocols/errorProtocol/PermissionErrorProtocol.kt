package com.openclassrooms.realestatemanager.presenter.protocols.errorProtocol

import com.openclassrooms.realestatemanager.domain.models.DomainPermissionException
import com.openclassrooms.realestatemanager.domain.utils.toDomainExceptionType

interface PermissionErrorProtocol : ErrorProtocol {

    fun onReceivePermissionReportError() { /* lack of spec */ }
    fun onReceiveBackgroundLocationMissingOnAndroidRError() { }

    override fun onReceiveError(exception: Throwable) {
        when ((exception.toDomainExceptionType() as? DomainPermissionException)) {
            is DomainPermissionException.ReportError -> onReceivePermissionReportError()
            is DomainPermissionException.BackgroundLocationError -> onReceiveBackgroundLocationMissingOnAndroidRError()
        }
    }
}