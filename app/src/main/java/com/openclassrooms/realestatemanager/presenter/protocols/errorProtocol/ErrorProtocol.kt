package com.openclassrooms.realestatemanager.presenter.protocols.errorProtocol

/**
 * Interface to implement callbacks when DomainException are triggered
 */
interface ErrorProtocol {
    fun onReceiveError(exception: Throwable) {}
}