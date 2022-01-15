package com.openclassrooms.realestatemanager.data.vendors.local

import android.database.Cursor
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.openclassrooms.realestatemanager.data.models.entities.RealEstateCondenseResponse
import com.openclassrooms.realestatemanager.data.models.entities.RealEstateMasterDetailResponse
import com.openclassrooms.realestatemanager.data.vendors.local.objectRequest.AddressRequest
import com.openclassrooms.realestatemanager.data.vendors.local.objectRequest.IsEuroRequest
import com.openclassrooms.realestatemanager.data.vendors.local.objectRequest.PhotoRequest
import com.openclassrooms.realestatemanager.data.vendors.local.objectRequest.RealEstateRequest
import io.reactivex.rxjava3.core.Single

@Dao
interface RealEstateDao {

    @Transaction
    @Query("SELECT * FROM RealEstateRequest")
    fun getAllRealEstateMasterDetailWithCursor(): Cursor
    
    @Transaction
    @Query("SELECT * FROM RealEstateRequest WHERE realEstateId = :id")
    fun getRealEstateMasterDetail(id: Long): Single<RealEstateMasterDetailResponse>
    
    @Transaction
    @Query("SELECT * FROM RealEstateRequest")
    fun getAllRealEstateMasterDetail(): Single<List<RealEstateMasterDetailResponse>>
    
    @Transaction
    @Query("SELECT * FROM RealEstateRequest")
    fun getRealEstateCondense(): Single<List<RealEstateCondenseResponse>>
    
    @Transaction
    @Query("SELECT is_euro FROM IsEuroRequest")
    fun getIsEuro(): Single<Boolean>
    
    @Insert
    fun createRealEstate(realEstateRequest: RealEstateRequest) : Long
    
    @Insert
    fun createAddress(addressRequest: AddressRequest)
    
    @Insert
    fun createPhoto(photosRequest: List<PhotoRequest>)
    
    @Insert
    fun createIsEuro(isEuro: IsEuroRequest)
    
    @Update
    fun updateRealEstate(realEstateRequest: RealEstateRequest)
    
    @Update
    fun updateAddress(addressRequest: AddressRequest)
    
    @Update
    fun updateIsEuro(isEuro: IsEuroRequest)
    
    @Delete
    fun deletePhoto(photoRequest: PhotoRequest)
}