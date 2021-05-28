package com.openclassrooms.realestatemanager.data.repositories.local

import com.openclassrooms.realestatemanager.data.vendors.local.RealEstateDao
import com.openclassrooms.realestatemanager.data.vendors.local.objectRequest.AddressRequest
import com.openclassrooms.realestatemanager.data.vendors.local.objectRequest.PhotoRequest
import com.openclassrooms.realestatemanager.data.vendors.local.objectRequest.RealEstateRequest
import com.openclassrooms.realestatemanager.domain.models.DomainRealEstateMasterDetail
import com.openclassrooms.realestatemanager.domain.repositories.local.RealEstateRepository
import javax.inject.Inject

class RealEstateRepositoryImpl @Inject constructor(
    private val realEstateDao: RealEstateDao
) : RealEstateRepository {
    
    override fun createRealEstate(domainRealEstateMasterDetail: DomainRealEstateMasterDetail) {
        with(domainRealEstateMasterDetail) {
            val id = realEstateDao.createRealEstate(
                RealEstateRequest(
                    realEstateId = id,
                    type = type,
                    price = price,
                    surface = surface,
                    description = description,
                    interestPoint = interestPoint,
                    isSold = isSold,
                    entryDate = entryDate,
                    exitDate = exitDate,
                    agent = agent,
                    totalRoomNumber = totalRoomNumber,
                    bedroomNumber = bedroomNumber,
                    bathroomNumber = bathroomNumber
                )
            )
            with(address) {
                realEstateDao.createAddress(
                    AddressRequest(
                        addressId = 0,
                        realEstateOwnerId = id,
                        country = country,
                        city = city,
                        houseNumber = houseNumber,
                        road = road,
                        postalCode = postalCode
                    )
                )
            }
            realEstateDao.createPhoto(
                 photos.map { photo ->
                     PhotoRequest(
                        photoId = 0,
                        realEstateOwnerId = id,
                        photoReference = photo.photoReference,
                        roomType = photo.roomType
                     )
                 }
            )
        }
    }
}