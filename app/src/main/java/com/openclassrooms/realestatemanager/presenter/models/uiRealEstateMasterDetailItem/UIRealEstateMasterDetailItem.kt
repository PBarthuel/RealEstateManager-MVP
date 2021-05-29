package com.openclassrooms.realestatemanager.presenter.models.uiRealEstateMasterDetailItem

import com.openclassrooms.realestatemanager.presenter.models.uiAddressItem.UIAddressItem
import com.openclassrooms.realestatemanager.presenter.models.uiPhotoItem.UIPhotoItem

data class UIRealEstateMasterDetailItem(
    val id: Long,
    val type: String,
    val price: String,
    val surface: String,
    val description: String,
    val school: Boolean,
    val commerce: Boolean,
    val parc: Boolean,
    val trainStation: Boolean,
    val isSold: Boolean,
    val entryDate: String,
    val exitDate: String,
    val agent: String,
    val totalRoomNumber: String,
    val bedroomNumber: String,
    val bathroomNumber: String,
    val address: UIAddressItem,
    val photos: List<UIPhotoItem>
)