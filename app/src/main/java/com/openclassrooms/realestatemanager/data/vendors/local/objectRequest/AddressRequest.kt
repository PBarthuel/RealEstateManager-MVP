package com.openclassrooms.realestatemanager.data.vendors.local.objectRequest

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Entity
@Serializable
data class AddressRequest (
    @PrimaryKey(autoGenerate = true) var addressId: Long,
    val realEstateOwnerId: Long,
    @ColumnInfo(name = "country")
    val country: String,
    @ColumnInfo(name = "city")
    val city: String,
    @ColumnInfo(name = "house_number")
    val houseNumber: String,
    @ColumnInfo(name = "road")
    val road: String,
    @ColumnInfo(name = "postal_code")
    val postalCode: String
)