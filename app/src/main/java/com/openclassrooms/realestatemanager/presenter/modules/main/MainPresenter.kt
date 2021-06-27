package com.openclassrooms.realestatemanager.presenter.modules.main

import com.openclassrooms.realestatemanager.data.repositories.local.ConnectivityRepositoryImpl
import com.openclassrooms.realestatemanager.domain.useCases.isEuro.GetIsEuroUseCase
import com.openclassrooms.realestatemanager.domain.useCases.isEuro.CreateIsEuroUseCase
import com.openclassrooms.realestatemanager.domain.useCases.permissions.IsGeolocationEnabledUseCase
import com.openclassrooms.realestatemanager.domain.useCases.permissions.RequestGeolocationPermissionUseCase
import com.openclassrooms.realestatemanager.presenter.protocols.DisposablePresenter
import com.openclassrooms.realestatemanager.presenter.protocols.errorProtocol.PermissionErrorProtocol
import com.openclassrooms.realestatemanager.presenter.protocols.plusAssign
import com.openclassrooms.realestatemanager.presenter.protocols.utils.NetworkSchedulers
import javax.inject.Inject

interface MainView : PermissionErrorProtocol {
    fun displayNoInternet()
    fun displayInternet()
}

interface MainPresenter : DisposablePresenter<MainView> {
    fun setup()
}

class MainPresenterImpl @Inject constructor(
    private val isGeolocationEnabled: IsGeolocationEnabledUseCase,
    private val requestGeolocationPermission: RequestGeolocationPermissionUseCase,
    private val createIsEuro: CreateIsEuroUseCase,
    private val getIsEuro: GetIsEuroUseCase,
    private val networkSchedulers: NetworkSchedulers
) : MainPresenter {

    override var view: MainView? = null

    override fun attach(view: MainView) {
        this.view = view
    }

    override fun setup() {
        if(!isGeolocationEnabled.invoke()) {
            disposeBag += requestGeolocationPermission.invoke()
                .subscribeOn(networkSchedulers.io)
                .observeOn(networkSchedulers.main)
                .subscribe({},
                    { error -> view?.onReceiveError(error) }
                )
        }

        disposeBag += getIsEuro.invoke()
            .subscribeOn(networkSchedulers.io)
            .observeOn(networkSchedulers.main)
            .subscribe({ }, {
                disposeBag += createIsEuro.invoke()
                    .subscribeOn(networkSchedulers.io)
                    .observeOn(networkSchedulers.main)
                    .subscribe({}, {})
            })
    }
}