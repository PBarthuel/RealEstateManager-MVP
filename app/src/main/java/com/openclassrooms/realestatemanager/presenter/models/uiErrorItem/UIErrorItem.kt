package com.openclassrooms.realestatemanager.presenter.models.uiErrorItem

typealias UIDataErrorItem = Map<String, String>?

data class UIErrorItem(
    val errorKey: String,
    val data: UIDataErrorItem
)
