package com.openclassrooms.realestatemanager.presenter.modules.editRealEstate

import com.openclassrooms.realestatemanager.app.utils.Utils
import com.openclassrooms.realestatemanager.domain.useCases.editRealEstate.EditRealEstateUseCase
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
    fun onDismissView()
    fun onSetupUI(item: UIRealEstateMasterDetailItem)
    fun onUpdateList(list: List<UIPhotoItem>)
    fun onShowAddress(address: String)
}

interface EditRealEstatePresenter : DisposablePresenter<EditRealEstateView> {
    fun setup(id: Long)
    fun didAddPhoto(photoName: String, base64ImageData: String)
    fun didDeletePhoto(photo: UIPhotoItem)
    fun didChooseAddress(address: UIAddressItem)
    fun didSelectEdit(type: String, price: String, surface: String, description: String, isSold: Boolean, commerce: Boolean, school: Boolean, parc: Boolean, trainStation: Boolean, agent: String, totalRoomNumber: String, bedRoomNumber: String, bathRoomNumber: String)
}

class EditRealEstatePresenterImpl @Inject constructor(
    private val getRealEstateMasterDetail: GetRealEstateMasterDetailUseCase,
    private val editRealEstate: EditRealEstateUseCase,
    private val networkSchedulers: NetworkSchedulers
) : EditRealEstatePresenter {

    override var view: EditRealEstateView? = null

    var id: Long = 0
    var addressId: Long = 0
    var photosToDelete: ArrayList<UIPhotoItem> = arrayListOf()
    var photos: ArrayList<UIPhotoItem> = arrayListOf()
    var address: UIAddressItem? = null
    var entryDate: String? = null

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
                addressId = realEstateMasterDetailItem.address.id
                this.entryDate = realEstateMasterDetailItem.entryDate
                realEstateMasterDetailItem.photos.forEach { photo ->
                    photosToDelete.add(photo)
                    photos.add(photo)
                }
            }, { })
    }

    override fun didAddPhoto(photoName: String, base64ImageData: String) {
        photos.add(UIPhotoItem(0, base64ImageData, photoName))
        view?.onUpdateList(photos)
    }

    override fun didDeletePhoto(photo: UIPhotoItem) {
        photos.remove(photo)
        view?.onUpdateList(photos)
    }

    override fun didChooseAddress(address: UIAddressItem) {
        this.address = UIAddressItem(
            addressId,
            address.country,
            address.city,
            address.road,
            address.postalCode,
            address.latitude,
            address.longitude
        )
        view?.onShowAddress(address.getString())
    }

    override fun didSelectEdit(
        type: String,
        price: String,
        surface: String,
        description: String,
        isSold: Boolean,
        commerce: Boolean,
        school: Boolean,
        parc: Boolean,
        trainStation: Boolean,
        agent: String,
        totalRoomNumber: String,
        bedRoomNumber: String,
        bathRoomNumber: String
    ) {
        val address = address ?: return
        val entryDate = entryDate ?: return

        val exitDate = if(isSold) {
            Utils.todayDate
        } else {
            "05/05/2085"
        }

        val item = UIRealEstateMasterDetailItem(
            id = id,
            type = type,
            price = price,
            surface = surface,
            description = description,
            school = school,
            commerce = commerce,
            parc = parc,
            trainStation = trainStation,
            isSold = isSold,
            entryDate = entryDate,
            exitDate = exitDate,
            agent = agent,
            totalRoomNumber = totalRoomNumber,
            bedroomNumber = bedRoomNumber,
            bathroomNumber = bathRoomNumber,
            address = address,
            photos = photos
        )

        disposeBag += editRealEstate.invoke(item, photosToDelete)
            .subscribeOn(networkSchedulers.io)
            .observeOn(networkSchedulers.main)
            .subscribe({
               view?.onDismissView()
            }, { })
    }
}