package com.openclassrooms.realestatemanager.data.vendors.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.openclassrooms.realestatemanager.data.models.entities.RealEstateCondenseResponse
import com.openclassrooms.realestatemanager.data.models.entities.RealEstateMasterDetailResponse
import com.openclassrooms.realestatemanager.data.vendors.local.objectRequest.AddressRequest
import com.openclassrooms.realestatemanager.data.vendors.local.objectRequest.PhotoRequest
import com.openclassrooms.realestatemanager.data.vendors.local.objectRequest.RealEstateRequest
import io.reactivex.rxjava3.core.Single

@Dao
interface RealEstateDao {
    
    @Transaction
    @Query("SELECT * FROM RealEstateRequest WHERE realEstateId = :id")
    suspend fun getRealEstate(id: Long): RealEstateMasterDetailResponse
    
    @Transaction
    @Query("SELECT * FROM RealEstateRequest")
    fun getRealEstateCondense(): List<RealEstateCondenseResponse>
    
    @Insert
    fun createRealEstate(realEstateRequest: RealEstateRequest) : Long
    
    @Insert
    fun createAddress(addressRequest: AddressRequest)
    
    @Insert
    fun createPhoto(photosRequest: List<PhotoRequest>)
    
    @Update
    suspend fun updateRealEstate(realEstateRequest: RealEstateRequest)
    
    @Update
    fun updateAddress(addressRequest: AddressRequest)
    
    @Update
    fun updatePhoto(photoRequest: PhotoRequest)
    
    @Delete
    fun deleteRealEstate(realEstateRequest: RealEstateRequest)
}