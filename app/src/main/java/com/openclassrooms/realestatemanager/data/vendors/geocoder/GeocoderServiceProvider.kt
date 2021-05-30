package com.openclassrooms.realestatemanager.data.vendors.geocoder

import android.annotation.SuppressLint
import android.location.Criteria
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import com.google.android.gms.location.FusedLocationProviderClient
import com.openclassrooms.realestatemanager.data.models.entities.addressSearch.AddressResponse
import com.openclassrooms.realestatemanager.data.models.entities.addressSearch.toAddressResponse
import com.openclassrooms.realestatemanager.data.models.exceptions.DataUserLocationException
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.ObservableEmitter
import javax.inject.Inject

class GeocoderServiceProvider @Inject constructor(
    private val geocoder: Geocoder,
    private val criteria: Criteria,
    private val locationManager: LocationManager,
    private val fusedLocationProviderClient: FusedLocationProviderClient
) {
    fun getListOfAddresses(query: String): Observable<List<AddressResponse>> =
        Observable.create { observable ->
            geocoder.getFromLocationName(query, 10)
                .also { addresses -> observable.onNext(addresses.map { address -> address.toAddressResponse() }) }
        }

    @SuppressLint("MissingPermission")
    fun getUserAddress(): Observable<AddressResponse> =
        Observable.create { observable ->
            var location: Location? = null
            locationManager.getBestProvider(criteria, true)?.let { provider ->
                location = locationManager.getLastKnownLocation(provider)
            }
            if (location == null) {
                fusedLocationProviderClient
                    .lastLocation
                    .addOnSuccessListener { loc ->
                        if (loc == null) {
                            observable.onError(DataUserLocationException.EmptyLocationError("Missing user location"))
                            return@addOnSuccessListener
                        }
                        fetchAddressFromLocation(loc.latitude, loc.longitude, observable)
                    }
                    .addOnFailureListener { observable.onError(DataUserLocationException.EmptyLocationError("Missing user location")) }
                return@create
            }
            location?.let { loc -> fetchAddressFromLocation(loc.latitude, loc.longitude, observable) }
        }

    private fun fetchAddressFromLocation(latitude: Double, longitude: Double, observable: ObservableEmitter<AddressResponse>) {
        geocoder.getFromLocation(latitude, longitude, 1)
            .first()
            .also { address -> observable.onNext(address.toAddressResponse()) }
    }
}
