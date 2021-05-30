package com.openclassrooms.realestatemanager.domain.useCases.geocoder

import com.openclassrooms.realestatemanager.domain.models.DomainAddress
import com.openclassrooms.realestatemanager.domain.repositories.geocoder.GeocoderRepository
import io.reactivex.rxjava3.core.Observable
import javax.inject.Inject

class GetUserAddressUseCase @Inject constructor(
    private val geocoderRepository: GeocoderRepository
) {
    fun invoke(): Observable<DomainAddress> =
        geocoderRepository.getUserAddress()
}