package com.openclassrooms.realestatemanager.presenter.models.uiAddressItem

import com.openclassrooms.realestatemanager.domain.models.DomainAddress

fun DomainAddress.toUIItem() =
    UIAddressItem(id, country, city, road, postalCode, latitude, longitude)