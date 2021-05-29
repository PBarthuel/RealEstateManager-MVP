package com.openclassrooms.realestatemanager.data.repositories.geocoder

import com.openclassrooms.realestatemanager.data.utils.throwDomainExceptionOnError
import com.openclassrooms.realestatemanager.data.vendors.geocoder.GeocoderServiceProvider
import com.openclassrooms.realestatemanager.domain.models.DomainAddress
import com.openclassrooms.realestatemanager.domain.repositories.geocoder.GeocoderRepository
import io.reactivex.rxjava3.core.Observable
import javax.inject.Inject

class GeocoderRepositoryImpl @Inject constructor(
    private val geocoderServiceProvider: GeocoderServiceProvider
) : GeocoderRepository {

    override fun getListOfAddresses(query: String): Observable<List<DomainAddress>> =
        geocoderServiceProvider.getListOfAddresses(query)
            .map { it.map { addressResponse -> addressResponse.toDomain() } }
            .throwDomainExceptionOnError()
}
