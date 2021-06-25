package com.openclassrooms.realestatemanager.presenter.models

import com.openclassrooms.realestatemanager.app.utils.Utils
import com.openclassrooms.realestatemanager.domain.models.DomainRealEstateCondense
import com.openclassrooms.realestatemanager.domain.models.DomainRealEstateMasterDetail
import com.openclassrooms.realestatemanager.presenter.models.uiAddressItem.toUIItem
import com.openclassrooms.realestatemanager.presenter.models.uiPhotoItem.toUIItem
import com.openclassrooms.realestatemanager.presenter.models.uiRealEstateCondenseItem.UIRealEstateCondenseItem
import com.openclassrooms.realestatemanager.presenter.models.uiRealEstateMasterDetailItem.UIRealEstateMasterDetailItem

fun DomainRealEstateCondense.toUICondenseItemDollar(): UIRealEstateCondenseItem =
    UIRealEstateCondenseItem(
        id,
        type,
        city,
        "$price$",
        isSold,
        photo
    )

fun DomainRealEstateCondense.toUICondenseItemEuro(): UIRealEstateCondenseItem =
    UIRealEstateCondenseItem(
        id,
        type,
        city,
        Utils.convertDollarToEuro(price.toInt()).toString() + "€",
        isSold,
        photo
    )

fun DomainRealEstateMasterDetail.toUIMasterDetailItem(): UIRealEstateMasterDetailItem =
    UIRealEstateMasterDetailItem(
        id = id,
        type = type,
        price = price,
        surface = surface,
        description = description,
        isSold = isSold,
        school = school,
        commerce = commerce,
        parc = parc,
        trainStation = trainStation,
        entryDate = entryDate,
        exitDate = exitDate,
        agent = agent,
        totalRoomNumber = totalRoomNumber,
        bedroomNumber = bedroomNumber,
        bathroomNumber = bathroomNumber,
        address = address.toUIItem(),
        photos = photos.map { it.toUIItem() }
    )