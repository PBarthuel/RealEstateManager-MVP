package com.openclassrooms.realestatemanager.presenter.modules.main.views

import com.openclassrooms.realestatemanager.domain.useCases.main.GetRealEstateMasterDetailUseCase
import com.openclassrooms.realestatemanager.presenter.models.toUIMasterDetailItem
import com.openclassrooms.realestatemanager.presenter.models.uiRealEstateMasterDetailItem.UIRealEstateMasterDetailItem
import com.openclassrooms.realestatemanager.presenter.protocols.DisposablePresenter
import com.openclassrooms.realestatemanager.presenter.protocols.plusAssign
import com.openclassrooms.realestatemanager.presenter.protocols.utils.NetworkSchedulers
import javax.inject.Inject

interface RealEstateMasterDetailView {
    fun onSetupMasterDetail(item: UIRealEstateMasterDetailItem)
    fun onShowEditActivity(id: Long)
    fun displayToast()
}

interface RealEstateMasterDetailPresenter : DisposablePresenter<RealEstateMasterDetailView> {
    fun setup(id: Long)
    fun didSelectEdit()
}

class RealEstateMasterDetailPresenterImpl @Inject constructor(
    private val getRealEstateMasterDetail: GetRealEstateMasterDetailUseCase,
    private val networkSchedulers: NetworkSchedulers
) : RealEstateMasterDetailPresenter {

    override var view: RealEstateMasterDetailView? = null

    var id: Long? = null

    override fun attach(view: RealEstateMasterDetailView) {
        this.view = view
    }

    override fun setup(id: Long) {
        this.id = id

        disposeBag += getRealEstateMasterDetail.invoke(id)
            .map { it.toUIMasterDetailItem() }
            .subscribeOn(networkSchedulers.io)
            .observeOn(networkSchedulers.main)
            .subscribe({
                view?.onSetupMasterDetail(it)
            }, { })
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