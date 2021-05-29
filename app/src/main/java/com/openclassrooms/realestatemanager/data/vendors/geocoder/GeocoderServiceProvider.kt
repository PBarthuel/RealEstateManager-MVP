package com.openclassrooms.realestatemanager.data.vendors.geocoder

import android.location.Geocoder
import com.openclassrooms.realestatemanager.data.models.entities.addressSearch.AddressResponse
import com.openclassrooms.realestatemanager.data.models.entities.addressSearch.toAddressResponse
import io.reactivex.rxjava3.core.Observable
import javax.inject.Inject

class GeocoderServiceProvider @Inject constructor(
    private val geocoder: Geocoder,
) {
    fun getListOfAddresses(query: String): Observable<List<AddressResponse>> =
        Observable.create { observable ->
            geocoder.getFromLocationName(query, 10)
                .also { addresses -> observable.onNext(addresses.map { address -> address.toAddressResponse() }) }
        }
}
