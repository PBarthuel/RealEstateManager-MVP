package com.openclassrooms.realestatemanager.presenter.modules.main.views.realEstateList

import com.openclassrooms.realestatemanager.domain.useCases.isEuro.GetIsEuroUseCase
import com.openclassrooms.realestatemanager.domain.useCases.main.GetRealEstateCondenseUseCase
import com.openclassrooms.realestatemanager.presenter.models.toUICondenseItemDollar
import com.openclassrooms.realestatemanager.presenter.models.toUICondenseItemEuro
import com.openclassrooms.realestatemanager.presenter.models.uiRealEstateCondenseItem.UIRealEstateCondenseItem
import com.openclassrooms.realestatemanager.presenter.protocols.DisposablePresenter
import com.openclassrooms.realestatemanager.presenter.protocols.plusAssign
import com.openclassrooms.realestatemanager.presenter.protocols.utils.NetworkSchedulers
import javax.inject.Inject

interface RealEstateListView {
    fun onSetupRealEstates(list: List<UIRealEstateCondenseItem>)
    fun onShowEditActivity(id: Long)
    fun displayToast()
}

interface RealEstateListPresenter : DisposablePresenter<RealEstateListView> {
    fun setup()
    fun setupId(id: Long)
    fun didSelectEdit()
}

class RealEstateListPresenterImpl @Inject constructor(
    private val getRealEstateCondense: GetRealEstateCondenseUseCase,
    private val getIsEuro: GetIsEuroUseCase,
    private val networkSchedulers: NetworkSchedulers
) : RealEstateListPresenter {

    override var view: RealEstateListView? = null

    var id: Long? = null

    override fun attach(view: RealEstateListView) {
        this.view = view
    }

    override fun setup() {
        disposeBag += getIsEuro.invoke()
            .subscribeOn(networkSchedulers.io)
            .observeOn(networkSchedulers.main)
            .subscribe({
                if(it == true) {
                    disposeBag += getRealEstateCondense.invoke()
                        .map { domainRealEstatesCondenses ->
                            domainRealEstatesCondenses.map { domainRealEstateCondense ->
                                domainRealEstateCondense.toUICondenseItemEuro()
                            }
                        }
                        .subscribeOn(networkSchedulers.io)
                        .observeOn(networkSchedulers.main)
                        .subscribe({
                            view?.onSetupRealEstates(it)
                        }, { })
                } else {
                    disposeBag += getRealEstateCondense.invoke()
                        .map { domainRealEstatesCondenses ->
                            domainRealEstatesCondenses.map { domainRealEstateCondense ->
                                domainRealEstateCondense.toUICondenseItemDollar()
                            }
                        }
                        .subscribeOn(networkSchedulers.io)
                        .observeOn(networkSchedulers.main)
                        .subscribe({
                            view?.onSetupRealEstates(it)
                        }, { })
                }
            }, { })
    }

    override fun setupId(id: Long) {
        this.id = id
    }

    override fun didSelectEdit() {
        if(id == null) {
            view?.displayToast()
        } else {
            val id = id ?: return
            view?.onShowEditActivity(id)
        }
    }
}