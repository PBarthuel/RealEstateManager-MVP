package com.openclassrooms.realestatemanager.presenter.models.uiPhotoItem

import com.openclassrooms.realestatemanager.domain.models.DomainPhoto

fun DomainPhoto.toUIItem(): UIPhotoItem =
    UIPhotoItem(photoReference, roomType)