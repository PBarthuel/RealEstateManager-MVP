package com.openclassrooms.realestatemanager.presenter.modules.main

import com.openclassrooms.realestatemanager.data.repositories.local.ConnectivityRepositoryImpl
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
    private val networkSchedulers: NetworkSchedulers,
    private val connectivityRepositoryImpl: ConnectivityRepositoryImpl
) : MainPresenter {

    override var view: MainView? = null

    override fun attach(view: MainView) {
        this.view = view
    }

    override fun setup() {
        disposeBag += connectivityRepositoryImpl
            .isConnectedPublishSubject
            .subscribeOn(networkSchedulers.io)
            .subscribe({
                if(!it) {
                    //TODO comme ça pour verifier internet
                        //TODO si tu as oublié tu a handle internet pour la pull request 
                    view?.displayNoInternet()
                } else {
                    view?.displayInternet()
                }
            }, {})
        if(!isGeolocationEnabled.invoke()) {
            disposeBag += requestGeolocationPermission.invoke()
                .subscribeOn(networkSchedulers.io)
                .observeOn(networkSchedulers.main)
                .subscribe({},
                    { error -> view?.onReceiveError(error) }
                )
        }
    }
}