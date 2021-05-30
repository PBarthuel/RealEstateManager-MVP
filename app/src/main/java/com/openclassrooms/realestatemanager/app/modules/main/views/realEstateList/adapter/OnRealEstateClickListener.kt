package com.openclassrooms.realestatemanager.app.modules.main.views.realEstateList.adapter

import com.openclassrooms.realestatemanager.presenter.models.uiRealEstateCondenseItem.UIRealEstateCondenseItem

interface OnRealEstateClickListener {
    fun onRealEstateClicked(item: UIRealEstateCondenseItem)
}
