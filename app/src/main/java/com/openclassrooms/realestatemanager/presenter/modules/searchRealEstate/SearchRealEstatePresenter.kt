package com.openclassrooms.realestatemanager.presenter.modules.searchRealEstate

import com.openclassrooms.realestatemanager.app.utils.viewExtension.toDate
import com.openclassrooms.realestatemanager.domain.useCases.map.GetAllRealEstateMasterDetailUseCase
import com.openclassrooms.realestatemanager.domain.utils.isOnlyNumber
import com.openclassrooms.realestatemanager.presenter.models.toUIMasterDetailItem
import com.openclassrooms.realestatemanager.presenter.models.uiRealEstateCondenseItem.UIRealEstateCondenseItem
import com.openclassrooms.realestatemanager.presenter.models.uiRealEstateMasterDetailItem.UIRealEstateMasterDetailItem
import com.openclassrooms.realestatemanager.presenter.protocols.DisposablePresenter
import com.openclassrooms.realestatemanager.presenter.protocols.plusAssign
import com.openclassrooms.realestatemanager.presenter.protocols.utils.NetworkSchedulers
import java.util.Date
import javax.inject.Inject

interface SearchRealEstateView {
    fun onSetEntryDate(entryDate: Date)
    fun onSetExitDate(exitDate: Date)
    fun onDisplaySearch(list: List<UIRealEstateCondenseItem>)
    fun onResetAllSwitch()
    fun onResetDate()
}

interface SearchRealEstatePresenter: DisposablePresenter<SearchRealEstateView> {
    fun setup()
    fun reloadList()
    fun reloadSwitchValue()
    fun didSelectEntryDate(entryDate: Date)
    fun didSelectExitDate(exitDate: Date)
    fun didSelectMinSurface(minSurface: String)
    fun didSelectMaxSurface(maxSurface: String)
    fun didSelectMinPrice(minPrice: String)
    fun didSelectMaxPrice(maxPrice: String)
    fun didSelectMinPhotoNumber(minPhotoNumber: String)
    fun didSelectSchool(isSchool: Boolean)
    fun didSelectCommerce(isCommerce: Boolean)
    fun didSelectParc(isParc: Boolean)
    fun didSelectTrainStation(isTrainStation: Boolean)
    fun didSelectIsSold(isSold: Boolean)
    fun didValidate(city: String)
}

class SearchRealEstatePresenterImpl @Inject constructor(
    private val getAllRealEstateMasterDetail: GetAllRealEstateMasterDetailUseCase,
    private val networkSchedulers: NetworkSchedulers
) : SearchRealEstatePresenter {
    
    override var view: SearchRealEstateView? = null
    
    private val listRealEstateMasterDetail: ArrayList<UIRealEstateMasterDetailItem> = arrayListOf()
    var listToShow: ArrayList<UIRealEstateMasterDetailItem> = arrayListOf()
    var minSurface: Long? = null
    var maxSurface: Long? = null
    var minPrice: Long? = null
    var maxPrice: Long? = null
    var minPhotoNumber: Long? = null
    var entryDate: Date? = null
    var exitDate: Date? = null
    var isSchool: Boolean? = null
    var isCommerce: Boolean? = null
    var isParc: Boolean? = null
    var isTrainStation: Boolean? = null
    var isSold: Boolean? = null
    
    override fun attach(view: SearchRealEstateView) {
        this.view = view
    }
    
    override fun setup() {
        disposeBag += getAllRealEstateMasterDetail.invoke()
            .map { domainRealEstatesMastersDetail ->
                domainRealEstatesMastersDetail.map { domainRealEstateMasterDetail ->
                    domainRealEstateMasterDetail.toUIMasterDetailItem()
                }
            }
            .subscribeOn(networkSchedulers.io)
            .observeOn(networkSchedulers.main)
            .subscribe({
            it.forEach { uiRealEstateMasterDetailItem ->
                    listRealEstateMasterDetail.add(uiRealEstateMasterDetailItem)
                    listToShow.add(uiRealEstateMasterDetailItem)
                }
            }, { })
    }
    
    override fun didSelectEntryDate(entryDate: Date) {
        this.entryDate = entryDate
        view?.onSetEntryDate(entryDate)
    }
    
    override fun didSelectExitDate(exitDate: Date) {
        this.exitDate = exitDate
        view?.onSetExitDate(exitDate)
    }
    
    override fun didSelectMinSurface(minSurface: String) {
        if(minSurface != "" && minSurface.isOnlyNumber()) {
            this.minSurface = minSurface.toLong()
        }
    }
    
    override fun didSelectMaxSurface(maxSurface: String) {
        if(maxSurface != "" && maxSurface.isOnlyNumber()) {
            this.maxSurface = maxSurface.toLong()
        } else {
            this.maxSurface = null
        }
    }
    
    override fun didSelectMinPrice(minPrice: String) {
        if(minPrice != "" && minPrice.isOnlyNumber()) {
            this.minPrice = minPrice.toLong()
        }
    }
    
    override fun didSelectMaxPrice(maxPrice: String) {
        if(maxPrice != "" && maxPrice.isOnlyNumber()) {
            this.maxPrice = maxPrice.toLong()
        } else {
            this.maxPrice = null
        }
    }
    
    override fun didSelectMinPhotoNumber(minPhotoNumber: String) {
        if(minPhotoNumber != "" && minPhotoNumber.isOnlyNumber()) {
            this.minPhotoNumber = minPhotoNumber.toLong()
        } else {
            this.minPhotoNumber = null
        }
    }
    
    override fun didSelectSchool(isSchool: Boolean) {
        this.isSchool = isSchool
    }
    
    override fun didSelectCommerce(isCommerce: Boolean) {
        this.isCommerce = isCommerce
    }
    
    override fun didSelectParc(isParc: Boolean) {
        this.isParc = isParc
    }
    
    override fun didSelectTrainStation(isTrainStation: Boolean) {
        this.isTrainStation = isTrainStation
    }
    
    override fun didSelectIsSold(isSold: Boolean) {
        this.isSold = isSold
    }
    
    override fun didValidate(city: String) {
        val listToRemove: ArrayList<UIRealEstateMasterDetailItem> = arrayListOf()
        listToShow.forEach { item ->
            val shouldAdd = when {
                minSurface != null && minSurface!! > item.surface.toLong() -> true
                maxSurface != null && maxSurface!! < item.surface.toLong() -> true
                minPrice != null && minPrice!! > item.price.toLong() -> true
                maxPrice != null && maxPrice!! < item.price.toLong() -> true
                city.isNotEmpty() && city != item.address.city -> true
                minPhotoNumber != null && minPhotoNumber!! > item.photos.size -> true
                isSchool != null && isSchool != item.school -> true
                isCommerce != null && isCommerce != item.commerce -> true
                isParc != null && isParc != item.parc -> true
                isTrainStation != null && isTrainStation != item.trainStation -> true
                isSold != null && isSold != item.isSold -> true
                entryDate != null && entryDate!!.after(item.entryDate.toDate()) -> true
                exitDate != null && exitDate!!.before(item.exitDate.toDate()) -> true
                else -> false
            }
            if (shouldAdd) {
                listToRemove.add(item)
            }
        }
        listToRemove.forEach {
            listToShow.remove(it)
        }
        view?.onDisplaySearch(listToShow.map {
            when (it.photos.isNullOrEmpty()) {
                true -> UIRealEstateCondenseItem(it.id, it.type, it.address.city, it.price, it.isSold, "")
                false -> UIRealEstateCondenseItem(it.id, it.type, it.address.city, it.price, it.isSold, it.photos[0].photoReference)
            }
        })
    }
    
    override fun reloadList() {
        listToShow.clear()
        listRealEstateMasterDetail.forEach {
            listToShow.add(it)
        }
        view?.onResetAllSwitch()
        view?.onResetDate()
        entryDate = null
        exitDate = null
    }
    
    override fun reloadSwitchValue() {
        isSchool = null
        isCommerce = null
        isParc = null
        isTrainStation = null
        isSold = null
    }
}