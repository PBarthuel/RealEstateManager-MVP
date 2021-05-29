package com.openclassrooms.realestatemanager.presenter.models.uiAddressItem

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UIAddressItem(
    val country: String?,
    val city: String?,
    val road: String,
    val postalCode: String,
    val latitude: Double,
    val longitude: Double
) : Parcelable {

    fun getString(): String {
        var address = road
        city?.let {
            address = "$address $city"
        }
        return address
    }
}