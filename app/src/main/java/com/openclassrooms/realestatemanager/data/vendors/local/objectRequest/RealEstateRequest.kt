package com.openclassrooms.realestatemanager.data.vendors.local.objectRequest

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.openclassrooms.realestatemanager.domain.models.DomainRealEstateMasterDetail
import kotlinx.serialization.Serializable

@Entity
@Serializable
data class RealEstateRequest(
    @PrimaryKey(autoGenerate = true)
    var realEstateId: Long,
    @ColumnInfo(name = "type")
    val type: String,
    @ColumnInfo(name = "price")
    val price: String,
    @ColumnInfo(name = "surface")
    val surface: String,
    @ColumnInfo(name = "description")
    val description: String,
    @ColumnInfo(name = "interest_point")
    val interestPoint: String,
    @ColumnInfo(name = "is_sold")
    val isSold: Boolean,
    @ColumnInfo(name = "entry_date")
    val entryDate: String,
    @ColumnInfo(name = "exit_date")
    val exitDate: String,
    @ColumnInfo(name = "agent")
    val agent: String,
    @ColumnInfo(name = "total_room_number")
    val totalRoomNumber: String,
    @ColumnInfo(name = "bedroom_number")
    val bedroomNumber: String,
    @ColumnInfo(name = "bathroom_number")
    val bathroomNumber: String
)