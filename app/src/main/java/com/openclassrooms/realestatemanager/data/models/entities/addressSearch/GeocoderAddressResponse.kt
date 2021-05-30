package com.openclassrooms.realestatemanager.data.models.entities.addressSearch

import android.location.Address

fun Address.toAddressResponse(): AddressResponse {
    var street = thoroughfare
    subThoroughfare?.let { street = "$it $street" }
    if (street == null) {
        street = featureName
    }
    return AddressResponse(
        country = countryName,
        city = locality,
        road = street,
        postalCode = postalCode,
        latitude = latitude,
        longitude = longitude
    )
}
