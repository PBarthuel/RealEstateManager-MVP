package com.openclassrooms.realestatemanager.data.models.entities

import androidx.room.Embedded
import androidx.room.Relation
import com.openclassrooms.realestatemanager.data.models.domainMappingProtocols.DomainModelConvertible
import com.openclassrooms.realestatemanager.data.vendors.local.objectRequest.AddressRequest
import com.openclassrooms.realestatemanager.data.vendors.local.objectRequest.PhotoRequest
import com.openclassrooms.realestatemanager.data.vendors.local.objectRequest.RealEstateRequest
import com.openclassrooms.realestatemanager.domain.models.DomainRealEstateMasterDetail
import kotlinx.serialization.Serializable

@Serializable
data class RealEstateMasterDetailResponse (
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
) : DomainModelConvertible<DomainRealEstateMasterDetail> {
    override fun toDomain(): DomainRealEstateMasterDetail =
        with(realEstate) {
            DomainRealEstateMasterDetail(
                    realEstateId,
                    type,
                    price,
                    surface,
                    description,
                    interestPoint,
                    isSold,
                    entryDate,
                    exitDate,
                    agent,
                    totalRoomNumber,
                    bedroomNumber,
                    bathroomNumber,
                    address.toDomain(),
                    photo.map { it.toDomain() }
            )
        }
}