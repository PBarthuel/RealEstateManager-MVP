package com.openclassrooms.realestatemanager.presenter.modules.createRealEstate

import com.openclassrooms.realestatemanager.app.utils.Utils
import com.openclassrooms.realestatemanager.domain.useCases.createRealEstate.CreateRealEstateUseCase
import com.openclassrooms.realestatemanager.presenter.models.uiAddressItem.UIAddressItem
import com.openclassrooms.realestatemanager.presenter.models.uiPhotoItem.UIPhotoItem
import com.openclassrooms.realestatemanager.presenter.models.uiRealEstateMasterDetailItem.UIRealEstateMasterDetailItem
import com.openclassrooms.realestatemanager.presenter.protocols.DisposablePresenter
import com.openclassrooms.realestatemanager.presenter.protocols.utils.NetworkSchedulers
import javax.inject.Inject

interface CreateRealEstateView {
    fun onDismissView()
    fun onUpdateList(list: List<UIPhotoItem>)
}

interface CreateRealEstatePresenter: DisposablePresenter<CreateRealEstateView> {
    fun didSubmitRealEstate(type: String, price: String, surface: String, description: String, interestPoint: String, agent: String, totalRoomNumber: String, bedroomNumber: String, bathroomNumber: String, address: UIAddressItem)
    fun didAddPhoto(photoName: String, base64ImageData: String)
}

class CreateRealEstatePresenterImpl @Inject constructor(
    private val createRealEstate: CreateRealEstateUseCase,
    private val networkSchedulers: NetworkSchedulers
) : CreateRealEstatePresenter {
    
    override var view: CreateRealEstateView? = null
    
    var photos: MutableList<UIPhotoItem> = mutableListOf()
    
    override fun attach(view: CreateRealEstateView) {
        this.view = view
    }
    
    override fun didSubmitRealEstate(
        type: String,
        price: String,
        surface: String,
        description: String,
        interestPoint: String,
        agent: String,
        totalRoomNumber: String,
        bedroomNumber: String,
        bathroomNumber: String,
        address: UIAddressItem,
    ) {
        val item = UIRealEstateMasterDetailItem(
                id = 0,
                type = type,
                price = price,
                surface = surface,
                description = description,
                interestPoint = interestPoint,
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
        
        createRealEstate.invoke(item)
            .subscribeOn(networkSchedulers.io)
            .observeOn(networkSchedulers.main)
            .subscribe({
                view?.onDismissView()
            }, {
            // TODO here handle the error
            })
    }
    
    override fun didAddPhoto(photoName: String, base64ImageData: String) {
        photos.add(UIPhotoItem(base64ImageData, photoName))
        view?.onUpdateList(photos)
    }
}