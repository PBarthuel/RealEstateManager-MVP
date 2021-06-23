package com.openclassrooms.realestatemanager.presenter.modules.settings

import com.openclassrooms.realestatemanager.domain.useCases.isEuro.GetIsEuroUseCase
import com.openclassrooms.realestatemanager.domain.useCases.isEuro.UpdateIsEuroUseCase
import com.openclassrooms.realestatemanager.presenter.protocols.DisposablePresenter
import com.openclassrooms.realestatemanager.presenter.protocols.plusAssign
import com.openclassrooms.realestatemanager.presenter.protocols.utils.NetworkSchedulers
import javax.inject.Inject

interface SettingsView {
    fun onShowSwitchState(isEuro: Boolean)
    fun dismissView()
}

interface SettingsPresenter : DisposablePresenter<SettingsView> {
    fun setup()
    fun didTapOnSwitch(isEuro: Boolean)
}

class SettingsPresenterImpl @Inject constructor(
    private val getIsEuro: GetIsEuroUseCase,
    private val updateIsEuro: UpdateIsEuroUseCase,
    private val networkSchedulers: NetworkSchedulers
) : SettingsPresenter {
    
    override var view: SettingsView? = null
    
    override fun attach(view: SettingsView) {
        this.view = view
    }
    
    override fun setup() {
        disposeBag += getIsEuro.invoke()
            .subscribeOn(networkSchedulers.io)
            .observeOn(networkSchedulers.main)
            .subscribe({
                view?.onShowSwitchState(it)
            }, { }
            )
    }
    
    override fun didTapOnSwitch(isEuro: Boolean) {
        disposeBag += updateIsEuro.invoke(isEuro)
            .subscribeOn(networkSchedulers.io)
            .observeOn(networkSchedulers.main)
            .subscribe({
                view?.dismissView()
            }, { })
    }
}