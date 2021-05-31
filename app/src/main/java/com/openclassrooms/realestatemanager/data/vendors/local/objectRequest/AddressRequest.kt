package com.openclassrooms.realestatemanager.data.vendors.local.objectRequest

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.openclassrooms.realestatemanager.data.models.domainMappingProtocols.DomainModelConvertible
import com.openclassrooms.realestatemanager.domain.models.DomainAddress
import kotlinx.serialization.Serializable

@Entity
@Serializable
data class AddressRequest (
    @PrimaryKey(autoGenerate = true) var addressId: Long,
    val realEstateOwnerId: Long,
    @ColumnInfo(name = "country")
    val country: String?,
    @ColumnInfo(name = "city")
    val city: String?,
    @ColumnInfo(name = "road")
    val road: String,
    @ColumnInfo(name = "postal_code")
    val postalCode: String,
    @ColumnInfo(name = "latitude")
    val latitude: Double,
    @ColumnInfo(name = "longitude")
    val longitude: Double
) : DomainModelConvertible<DomainAddress> {
    override fun toDomain(): DomainAddress =
        DomainAddress(
            addressId,
            country,
            city,
            road,
            postalCode,
            latitude,
            longitude
        )
}