package com.openclassrooms.realestatemanager.presenter.modules.editRealEstate

import com.openclassrooms.realestatemanager.domain.useCases.main.GetRealEstateMasterDetailUseCase
import com.openclassrooms.realestatemanager.presenter.models.toUIMasterDetailItem
import com.openclassrooms.realestatemanager.presenter.models.uiAddressItem.UIAddressItem
import com.openclassrooms.realestatemanager.presenter.models.uiPhotoItem.UIPhotoItem
import com.openclassrooms.realestatemanager.presenter.models.uiRealEstateMasterDetailItem.UIRealEstateMasterDetailItem
import com.openclassrooms.realestatemanager.presenter.protocols.DisposablePresenter
import com.openclassrooms.realestatemanager.presenter.protocols.plusAssign
import com.openclassrooms.realestatemanager.presenter.protocols.utils.NetworkSchedulers
import javax.inject.Inject

interface EditRealEstateView {
    fun onSetupUI(item: UIRealEstateMasterDetailItem)
    fun onUpdateList(list: List<UIPhotoItem>)
    fun onShowAddress(address: String)
}

interface EditRealEstatePresenter : DisposablePresenter<EditRealEstateView> {
    fun setup(id: Long)
    fun didAddPhoto(photoName: String, base64ImageData: String)
    fun didDeletePhoto(photo: UIPhotoItem)
    fun didChooseAddress(address: UIAddressItem)
}

class EditRealEstatePresenterImpl @Inject constructor(
    private val getRealEstateMasterDetail: GetRealEstateMasterDetailUseCase,
    private val networkSchedulers: NetworkSchedulers
) : EditRealEstatePresenter {

    override var view: EditRealEstateView? = null

    var id: Long? = 0
    var photos: ArrayList<UIPhotoItem> = arrayListOf()
    var address: UIAddressItem? = null

    override fun attach(view: EditRealEstateView) {
        this.view = view
    }

    override fun setup(id: Long) {
        this.id = id

        disposeBag += getRealEstateMasterDetail.invoke(id)
            .map { it.toUIMasterDetailItem() }
            .subscribeOn(networkSchedulers.io)
            .observeOn(networkSchedulers.main)
            .subscribe({ realEstateMasterDetailItem ->
                view?.onSetupUI(realEstateMasterDetailItem)
                this.address = realEstateMasterDetailItem.address
                realEstateMasterDetailItem.photos.forEach { photo ->
                    photos.add(photo)
                }
            }, { })
    }

    override fun didAddPhoto(photoName: String, base64ImageData: String) {
        photos.add(UIPhotoItem(base64ImageData, photoName))
        view?.onUpdateList(photos)
    }

    override fun didDeletePhoto(photo: UIPhotoItem) {
        photos.remove(photo)
        view?.onUpdateList(photos)
    }

    override fun didChooseAddress(address: UIAddressItem) {
        this.address = address
        view?.onShowAddress(address.getString())
    }
}