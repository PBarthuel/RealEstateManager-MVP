package com.openclassrooms.realestatemanager.presenter.modules.map

import com.openclassrooms.realestatemanager.domain.useCases.geocoder.GetUserAddressUseCase
import com.openclassrooms.realestatemanager.domain.useCases.isEuro.GetIsEuroUseCase
import com.openclassrooms.realestatemanager.domain.useCases.map.GetAllRealEstateMasterDetailUseCase
import com.openclassrooms.realestatemanager.domain.useCases.permissions.IsGeolocationEnabledUseCase
import com.openclassrooms.realestatemanager.presenter.models.toUIMasterDetailItem
import com.openclassrooms.realestatemanager.presenter.models.uiAddressItem.UIAddressItem
import com.openclassrooms.realestatemanager.presenter.models.uiAddressItem.toUIItem
import com.openclassrooms.realestatemanager.presenter.models.uiRealEstateMasterDetailItem.UIRealEstateMasterDetailItem
import com.openclassrooms.realestatemanager.presenter.protocols.DisposablePresenter
import com.openclassrooms.realestatemanager.presenter.protocols.plusAssign
import com.openclassrooms.realestatemanager.presenter.protocols.utils.NetworkSchedulers
import javax.inject.Inject

interface MapView {
    fun onEnableUserPosition()
    fun onSetupMapMarker(list: List<UIRealEstateMasterDetailItem>, isEuro: Boolean)
    fun onShowUserPosition(item: UIAddressItem)
}

interface MapPresenter: DisposablePresenter<MapView> {
    fun setupMapMarker()
    fun setup()
}

class MapPresenterImpl @Inject constructor(
    private val getAllRealEstateMasterDetail: GetAllRealEstateMasterDetailUseCase,
    private val isGeolocationEnabled: IsGeolocationEnabledUseCase,
    private val getUserAddress: GetUserAddressUseCase,
    private val getIsEuro: GetIsEuroUseCase,
    private val networkSchedulers: NetworkSchedulers
) : MapPresenter {
    
    override var view: MapView? = null
    
    override fun attach(view: MapView) {
        this.view = view
    }
    
    override fun setup() {
        if (isGeolocationEnabled.invoke()) {
            showUserAddressOnMap()
            view?.onEnableUserPosition()
        }
    }
    
    override fun setupMapMarker() {
        disposeBag += getIsEuro.invoke()
            .subscribeOn(networkSchedulers.io)
            .observeOn(networkSchedulers.main)
            .subscribe({ isEuro ->
                disposeBag += getAllRealEstateMasterDetail.invoke()
                    .map { domainRealEstatesMastersDetails ->
                        domainRealEstatesMastersDetails.map {
                            it.toUIMasterDetailItem()
                        }
                    }
                    .subscribeOn(networkSchedulers.io)
                    .observeOn(networkSchedulers.main)
                    .subscribe({
                        view?.onSetupMapMarker(it, isEuro)
                    }, {})
            }, {})
    }
    
    private fun showUserAddressOnMap() {
        getUserAddress.invoke()
            .map { domainAddress -> domainAddress.toUIItem() }
            .subscribeOn(networkSchedulers.io)
            .observeOn(networkSchedulers.main)
            .subscribe({
                view?.onShowUserPosition(it)
            }, { })
    }
}