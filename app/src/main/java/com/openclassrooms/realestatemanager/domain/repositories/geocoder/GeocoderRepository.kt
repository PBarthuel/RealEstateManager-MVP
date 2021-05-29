package com.openclassrooms.realestatemanager.domain.repositories.geocoder


import com.openclassrooms.realestatemanager.domain.models.DomainAddress
import io.reactivex.rxjava3.core.Observable

interface GeocoderRepository {
    fun getListOfAddresses(query: String): Observable<List<DomainAddress>>
}
