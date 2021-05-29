package com.openclassrooms.realestatemanager.data.models.entities.addressSearch

import com.openclassrooms.realestatemanager.data.models.domainMappingProtocols.DomainModelConvertible
import com.openclassrooms.realestatemanager.domain.models.DomainAddress
import kotlinx.serialization.Serializable

@Serializable
data class AddressResponse(
    val country: String?,
    val city: String?,
    val road: String,
    val postalCode: String,
    val latitude: Double,
    val longitude: Double
) : DomainModelConvertible<DomainAddress> {
    override fun toDomain(): DomainAddress =
        DomainAddress(
            country = country,
            city = city,
            road = road,
            postalCode = postalCode,
            latitude = latitude,
            longitude = longitude
        )
}