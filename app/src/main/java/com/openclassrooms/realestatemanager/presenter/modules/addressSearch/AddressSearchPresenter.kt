package com.openclassrooms.realestatemanager.presenter.modules.addressSearch

import com.openclassrooms.realestatemanager.domain.useCases.geocoder.GetAddressesFromSearchUseCase
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
}

interface AddressSearchPresenter: DisposablePresenter<AddressSearchView> {
    fun searchAddresses(query: String)
    fun didSelectUserLocation()
}

class AddressSearchPresenterImpl @Inject constructor(
    private val getAddressesFromSearch: GetAddressesFromSearchUseCase,
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
        ""
        // TODO a faire plus tard
    }
}