package com.openclassrooms.realestatemanager.presenter.protocols.errorProtocol

import android.view.ViewGroup
import android.widget.Toast
import com.openclassrooms.realestatemanager.domain.models.DomainLocationException
import com.openclassrooms.realestatemanager.domain.utils.toDomainExceptionType

interface LocationErrorProtocol : ErrorProtocol {
    val rootView: ViewGroup?

    fun onReceiveEmptyUserLocationError() {
        val context = rootView?.context ?: return
        Toast.makeText(context, "error", Toast.LENGTH_LONG).show()
    }

    override fun onReceiveError(exception: Throwable) {
        when ((exception.toDomainExceptionType() as? DomainLocationException)) {
            is DomainLocationException.EmptyLocationError -> onReceiveEmptyUserLocationError()
        }
    }
}
