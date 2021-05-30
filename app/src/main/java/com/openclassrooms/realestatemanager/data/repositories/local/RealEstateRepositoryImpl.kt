package com.openclassrooms.realestatemanager.data.repositories.local

import com.openclassrooms.realestatemanager.data.vendors.local.RealEstateDao
import com.openclassrooms.realestatemanager.data.vendors.local.objectRequest.AddressRequest
import com.openclassrooms.realestatemanager.data.vendors.local.objectRequest.PhotoRequest
import com.openclassrooms.realestatemanager.data.vendors.local.objectRequest.RealEstateRequest
import com.openclassrooms.realestatemanager.domain.models.DomainRealEstateMasterDetail
import com.openclassrooms.realestatemanager.domain.repositories.local.RealEstateRepository
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.core.SingleOnSubscribe
import java.lang.Exception
import javax.inject.Inject

class RealEstateRepositoryImpl @Inject constructor(
    private val realEstateDao: RealEstateDao
) : RealEstateRepository {

    override fun createRealEstate(domainRealEstateMasterDetail: DomainRealEstateMasterDetail): Single<Unit> =
        Single.create { single ->
            with(domainRealEstateMasterDetail) {
                try {
                    val id = realEstateDao.createRealEstate(
                        RealEstateRequest(
                            realEstateId = id,
                            type = type,
                            price = price,
                            surface = surface,
                            description = description,
                            school = school,
                            commerce = commerce,
                            parc = parc,
                            trainStation = trainStation,
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
                                road = road,
                                postalCode = postalCode,
                                latitude = latitude,
                                longitude = longitude
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
                    single.onSuccess(Unit)
                } catch (e: Exception) {
                    single.onError(e)
                }
            }
        }
}