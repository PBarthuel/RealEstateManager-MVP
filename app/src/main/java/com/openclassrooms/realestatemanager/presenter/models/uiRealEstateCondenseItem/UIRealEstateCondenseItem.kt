package com.openclassrooms.realestatemanager.presenter.models.uiRealEstateCondenseItem

data class UIRealEstateCondenseItem(
    val id: Long,
    val type: String,
    val city: String?,
    val price: String,
    val isSold: Boolean,
    val photo: String
)