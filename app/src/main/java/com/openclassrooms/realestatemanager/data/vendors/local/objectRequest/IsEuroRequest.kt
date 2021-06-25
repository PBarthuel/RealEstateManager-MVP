package com.openclassrooms.realestatemanager.data.vendors.local.objectRequest

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Entity
@Serializable
data class IsEuroRequest(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "is_euro")
    val isEuro: Boolean
)