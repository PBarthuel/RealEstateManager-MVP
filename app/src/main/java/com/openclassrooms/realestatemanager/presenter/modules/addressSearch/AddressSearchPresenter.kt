package com.openclassrooms.realestatemanager.presenter.modules.addressSearch

import com.openclassrooms.realestatemanager.domain.useCases.geocoder.GetAddressesFromSearchUseCase
import com.openclassrooms.realestatemanager.domain.useCases.geocoder.GetUserAddressUseCase
import com.openclassrooms.realestatemanager.domain.useCases.permissions.IsGeolocationEnabledUseCase
import com.openclassrooms.realestatemanager.domain.useCases.permissions.RequestGeolocationPermissionUseCase
import com.openclassrooms.realestatemanager.presenter.models.uiAddressItem.UIAddressItem
import com.openclassrooms.realestatemanager.presenter.models.uiAddressItem.toUIItem
import com.openclassrooms.realestatemanager.presenter.protocols.DisposablePresenter
import com.openclassrooms.realestatemanager.presenter.protocols.errorProtocol.LocationErrorProtocol
import com.openclassrooms.realestatemanager.presenter.protocols.plusAssign
import com.openclassrooms.realestatemanager.presenter.protocols.utils.NetworkSchedulers
import javax.inject.Inject

interface AddressSearchView : LocationErrorProtocol {
    fun onShowEmptyAddresses()
    fun onShowAddressesList(addresses: List<UIAddressItem>)
    fun onShowMissingPermission()
    fun onReceiveUserAddress(address: UIAddressItem)
}

interface AddressSearchPresenter: DisposablePresenter<AddressSearchView> {
    fun searchAddresses(query: String)
    fun didSelectUserLocation()
}

class AddressSearchPresenterImpl @Inject constructor(
    private val getAddressesFromSearch: GetAddressesFromSearchUseCase,
    private val isGeolocationEnabled: IsGeolocationEnabledUseCase,
    // TODO rajouter la demande de permission en arrivant sur la map et pourquoi pas le faire aussi avec l'address
    private val requestGeolocationPermission: RequestGeolocationPermissionUseCase,
    private val getUserAddress: GetUserAddressUseCase,
    private val networkSchedulers: NetworkSchedulers
) : AddressSearchPresenter {

    override var view: AddressSearchView? = null

    override fun attach(view: AddressSearchView) {
        this.view = view
    }

    override fun searchAddresses(query: String) {
        disposeBag += getAddressesFromSearch.invoke(query)
            .map { domainAddresses -> domainAddresses.map { it.toUIItem() } }
            .subscribeOn(networkSchedulers.io)
            .observeOn(networkSchedulers.main)
            .subscribe({ uiAddresses ->
                if (uiAddresses.isEmpty()) {
                    view?.onShowEmptyAddresses()
                    return@subscribe
                }
                view?.onShowAddressesList(uiAddresses)
            }, { view?.onReceiveError(it) })
    }

    override fun didSelectUserLocation() {
        when (isGeolocationEnabled.invoke()) {
            true -> getUserAddress.invoke()
                .map { domainAddress -> domainAddress.toUIItem() }
                .subscribeOn(networkSchedulers.io)
                .observeOn(networkSchedulers.main)
                .subscribe({
                    view?.onReceiveUserAddress(it)
                }, { view?.onReceiveError(it) })
            false -> view?.onShowMissingPermission()
        }
    }
}