package com.openclassrooms.realestatemanager.data.models.entities

import androidx.room.Embedded
import androidx.room.Relation
import com.openclassrooms.realestatemanager.data.models.domainMappingProtocols.DomainModelConvertible
import com.openclassrooms.realestatemanager.data.vendors.local.objectRequest.AddressRequest
import com.openclassrooms.realestatemanager.data.vendors.local.objectRequest.PhotoRequest
import com.openclassrooms.realestatemanager.data.vendors.local.objectRequest.RealEstateRequest
import com.openclassrooms.realestatemanager.domain.models.DomainRealEstateCondense
import kotlinx.serialization.Serializable

@Serializable
data class RealEstateCondenseResponse(
    @Embedded val realEstate: RealEstateRequest,
    @Relation(
        parentColumn = "realEstateId",
        entityColumn = "realEstateOwnerId"
    )
    val address: AddressRequest,
    @Relation(
        parentColumn = "realEstateId",
        entityColumn = "realEstateOwnerId"
    )
    val photo: List<PhotoRequest>
) : DomainModelConvertible<DomainRealEstateCondense> {
    override fun toDomain(): DomainRealEstateCondense {
        with(realEstate) {
            val headerPhoto = when(photo.isNullOrEmpty()) {
                true -> ""
                false -> photo[0].photoReference
            }
            return DomainRealEstateCondense(
                realEstateId,
                type,
                address.city,
                price,
                isSold,
                headerPhoto
            )
        }
    }
}