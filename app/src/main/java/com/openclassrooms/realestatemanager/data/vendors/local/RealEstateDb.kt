package com.openclassrooms.realestatemanager.data.vendors.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.openclassrooms.realestatemanager.data.vendors.local.objectRequest.AddressRequest
import com.openclassrooms.realestatemanager.data.vendors.local.objectRequest.IsEuroRequest
import com.openclassrooms.realestatemanager.data.vendors.local.objectRequest.PhotoRequest
import com.openclassrooms.realestatemanager.data.vendors.local.objectRequest.RealEstateRequest

@Database(entities = [(RealEstateRequest::class), (AddressRequest::class), (PhotoRequest::class), (IsEuroRequest::class)], version = 1)
abstract class RealEstateDb: RoomDatabase() {
    abstract fun RealEstateDao(): RealEstateDao
}