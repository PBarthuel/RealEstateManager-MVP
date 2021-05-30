package com.openclassrooms.realestatemanager.presenter.modules.main.views.realEstateList

import android.util.Log
import com.openclassrooms.realestatemanager.domain.useCases.main.GetRealEstateCondenseUseCase
import com.openclassrooms.realestatemanager.presenter.models.toUICondenseItem
import com.openclassrooms.realestatemanager.presenter.models.uiRealEstateCondenseItem.UIRealEstateCondenseItem
import com.openclassrooms.realestatemanager.presenter.protocols.DisposablePresenter
import com.openclassrooms.realestatemanager.presenter.protocols.plusAssign
import com.openclassrooms.realestatemanager.presenter.protocols.utils.NetworkSchedulers
import javax.inject.Inject

interface RealEstateListView {
    fun onSetupRealEstates(list: List<UIRealEstateCondenseItem>)
}

interface RealEstateListPresenter : DisposablePresenter<RealEstateListView> {
    fun setup()
}

class RealEstateListPresenterImpl @Inject constructor(
    private val getRealEstateCondense: GetRealEstateCondenseUseCase,
    private val networkSchedulers: NetworkSchedulers
) : RealEstateListPresenter {

    override var view: RealEstateListView? = null

    override fun attach(view: RealEstateListView) {
        this.view = view
    }

    override fun setup() {
        disposeBag += getRealEstateCondense.invoke()
            .map { domainRealEstatesCondenses ->
                domainRealEstatesCondenses.map { domainRealEstateCondense ->
                    domainRealEstateCondense.toUICondenseItem()
                }
            }
            .subscribeOn(networkSchedulers.io)
            .observeOn(networkSchedulers.main)
            .subscribe({
                view?.onSetupRealEstates(it)
            }, { })
    }
}