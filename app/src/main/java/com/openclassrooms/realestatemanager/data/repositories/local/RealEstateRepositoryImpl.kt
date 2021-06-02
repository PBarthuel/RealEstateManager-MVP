package com.openclassrooms.realestatemanager.data.repositories.local

import com.openclassrooms.realestatemanager.data.utils.throwDomainExceptionOnError
import com.openclassrooms.realestatemanager.data.vendors.local.RealEstateDao
import com.openclassrooms.realestatemanager.data.vendors.local.objectRequest.AddressRequest
import com.openclassrooms.realestatemanager.data.vendors.local.objectRequest.PhotoRequest
import com.openclassrooms.realestatemanager.data.vendors.local.objectRequest.RealEstateRequest
import com.openclassrooms.realestatemanager.domain.models.DomainPhoto
import com.openclassrooms.realestatemanager.domain.models.DomainRealEstateCondense
import com.openclassrooms.realestatemanager.domain.models.DomainRealEstateMasterDetail
import com.openclassrooms.realestatemanager.domain.repositories.local.RealEstateRepository
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
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

    override fun getRealEstateCondense(): Single<List<DomainRealEstateCondense>> =
        realEstateDao.getRealEstateCondense().map { realEstatesCondensesResponses ->
            realEstatesCondensesResponses.map { it.toDomain() }
        }.throwDomainExceptionOnError()

    override fun getRealEstateMasterDetail(id: Long): Single<DomainRealEstateMasterDetail> =
        realEstateDao.getRealEstateMasterDetail(id)
            .map{ it.toDomain() }
            .throwDomainExceptionOnError()
    
    override fun getAllRealEstateMasterDetail(): Single<List<DomainRealEstateMasterDetail>> =
            realEstateDao.getAllRealEstateMasterDetail().map { realEstatesMastersDetailsResponses ->
                realEstatesMastersDetailsResponses.map { it.toDomain() }
            }.throwDomainExceptionOnError()
    
    override fun editRealEstate(domainRealEstateMasterDetail: DomainRealEstateMasterDetail, photosToDelete: List<DomainPhoto>): Completable =
        Completable.create { completable ->
            try {
                with(domainRealEstateMasterDetail) {
                    realEstateDao.updateRealEstate(
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
                    realEstateDao.updateAddress(
                        AddressRequest(
                            addressId = address.id,
                            realEstateOwnerId = id,
                            country = address.country,
                            city = address.city,
                            road = address.road,
                            postalCode = address.postalCode,
                            latitude = address.latitude,
                            longitude = address.longitude
                        )
                    )
                    photosToDelete.forEach {
                        realEstateDao.deletePhoto(
                            PhotoRequest(
                                photoId = it.id,
                                realEstateOwnerId = id,
                                photoReference = it.photoReference,
                                roomType = it.roomType
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
                    completable.onComplete()
                }
            } catch (e: Exception) {
                completable.onError(e)
            }
        }
}