package com.openclassrooms.realestatemanager.app.ui.photoList.adapter

import com.openclassrooms.realestatemanager.presenter.models.uiPhotoItem.UIPhotoItem

interface OnPhotoClickListener {
    fun onRealEstatePhotoClicked(item: UIPhotoItem)
}