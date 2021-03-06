package com.openclassrooms.realestatemanager.presenter.modules.createRealEstate

import android.util.Log
import com.openclassrooms.realestatemanager.app.utils.Utils
import com.openclassrooms.realestatemanager.data.repositories.local.ConnectivityRepositoryImpl
import com.openclassrooms.realestatemanager.domain.useCases.createRealEstate.CreateRealEstateUseCase
import com.openclassrooms.realestatemanager.presenter.models.uiAddressItem.UIAddressItem
import com.openclassrooms.realestatemanager.presenter.models.uiPhotoItem.UIPhotoItem
import com.openclassrooms.realestatemanager.presenter.models.uiRealEstateMasterDetailItem.UIRealEstateMasterDetailItem
import com.openclassrooms.realestatemanager.presenter.protocols.DisposablePresenter
import com.openclassrooms.realestatemanager.presenter.protocols.errorProtocol.FormErrorProtocol
import com.openclassrooms.realestatemanager.presenter.protocols.plusAssign
import com.openclassrooms.realestatemanager.presenter.protocols.utils.NetworkSchedulers
import javax.inject.Inject

interface CreateRealEstateView : FormErrorProtocol {
    fun onDismissView()
    fun onUpdateList(list: List<UIPhotoItem>)
    fun onShowAddress(address: String)
    fun onShowAddressEditText()
    fun onHideAddressEditText()
}

interface CreateRealEstatePresenter: DisposablePresenter<CreateRealEstateView> {
    fun setup()
    fun didSubmitRealEstate(type: String, price: String, surface: String, description: String, school: Boolean, commerce: Boolean, parc: Boolean, trainStation: Boolean, agent: String, totalRoomNumber: String, bedroomNumber: String, bathroomNumber: String)
    fun didAddPhoto(photoName: String, base64ImageData: String)
    fun didDeletePhoto(photo: UIPhotoItem)
    fun didChooseAddress(address: UIAddressItem)
}

class CreateRealEstatePresenterImpl @Inject constructor(
    private val createRealEstate: CreateRealEstateUseCase,
    private val connectivityRepositoryImpl: ConnectivityRepositoryImpl,
    private val networkSchedulers: NetworkSchedulers
) : CreateRealEstatePresenter {
    
    override var view: CreateRealEstateView? = null

    var address: UIAddressItem = UIAddressItem(
        0,
        "and/or edit to put an address",
        "to enable",
        "you need",
        "your network connection",
        0.0,
        0.0
    )
    var photos: ArrayList<UIPhotoItem> = arrayListOf()
    
    override fun attach(view: CreateRealEstateView) {
        this.view = view
    }

    override fun setup() {
        disposeBag += connectivityRepositoryImpl
            .isConnectedPublishSubject
            .subscribeOn(networkSchedulers.io)
            .subscribe({
                if(it) {
                    view?.onShowAddressEditText()
                } else {
                    view?.onHideAddressEditText()
                }
            }, { it.printStackTrace() })
    }

    override fun didSubmitRealEstate(
        type: String,
        price: String,
        surface: String,
        description: String,
        school: Boolean,
        commerce: Boolean,
        parc: Boolean,
        trainStation: Boolean,
        agent: String,
        totalRoomNumber: String,
        bedroomNumber: String,
        bathroomNumber: String,
    ) {
        val item = UIRealEstateMasterDetailItem(
            id = 0,
            type = type,
            price = price,
            surface = surface,
            description = description,
            school = school,
            commerce = commerce,
            parc = parc,
            trainStation = trainStation,
            agent = agent,
            isSold = false,
            entryDate = Utils.todayDate,
            exitDate = "05/05/2085",
            totalRoomNumber = totalRoomNumber,
            bedroomNumber = bedroomNumber,
            bathroomNumber = bathroomNumber,
            address = address,
            photos = photos
        )

        disposeBag += createRealEstate.invoke(item)
            .subscribeOn(networkSchedulers.io)
            .observeOn(networkSchedulers.main)
            .subscribe({
                view?.onDismissView()
            }, { error ->
                view?.onReceiveError(error)
            })
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
        this.address = address
        view?.onShowAddress(address.getString())
    }
}