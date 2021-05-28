package com.openclassrooms.realestatemanager.data.models.entities

import androidx.room.Embedded
import androidx.room.Relation
import com.openclassrooms.realestatemanager.data.vendors.local.objectRequest.AddressRequest
import com.openclassrooms.realestatemanager.data.vendors.local.objectRequest.PhotoRequest
import com.openclassrooms.realestatemanager.data.vendors.local.objectRequest.RealEstateRequest
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
        entityColumn = "photoId"
    )
    val photo: List<PhotoRequest>
)