package com.openclassrooms.realestatemanager.app.modules.addressSearch.adapter

import com.openclassrooms.realestatemanager.presenter.models.uiAddressItem.UIAddressItem

interface OnAddressClickListener {
    fun didSelectItem(item: UIAddressItem)
}
