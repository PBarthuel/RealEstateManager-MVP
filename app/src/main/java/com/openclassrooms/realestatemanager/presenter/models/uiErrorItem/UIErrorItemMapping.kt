package com.openclassrooms.realestatemanager.presenter.models.uiErrorItem

import com.openclassrooms.realestatemanager.domain.models.error.DomainError

fun DomainError.toUIItem(): UIErrorItem =
    UIErrorItem(errorKey, data)
