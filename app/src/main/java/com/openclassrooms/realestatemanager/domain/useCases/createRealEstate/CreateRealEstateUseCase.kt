package com.openclassrooms.realestatemanager.domain.useCases.createRealEstate

import com.openclassrooms.realestatemanager.domain.models.DomainAddress
import com.openclassrooms.realestatemanager.domain.models.DomainFormException
import com.openclassrooms.realestatemanager.domain.models.DomainPhoto
import com.openclassrooms.realestatemanager.domain.models.DomainRealEstateMasterDetail
import com.openclassrooms.realestatemanager.domain.repositories.local.RealEstateRepository
import com.openclassrooms.realestatemanager.domain.utils.isNameValid
import com.openclassrooms.realestatemanager.domain.utils.isOnlyNumber
import com.openclassrooms.realestatemanager.presenter.models.uiRealEstateMasterDetailItem.UIRealEstateMasterDetailItem
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class CreateRealEstateUseCase @Inject constructor(
    private val realEstateRepository: RealEstateRepository
) {

    fun invoke(item: UIRealEstateMasterDetailItem): Single<Unit> {
        with(item) {
            return when {
                !type.isNameValid() -> Single.error(DomainFormException.WrongTypeFormat("$type format is not valid"))
                !price.isOnlyNumber() -> Single.error(DomainFormException.WrongPriceFormat("$price format is not valid"))
                !surface.isOnlyNumber() -> Single.error(DomainFormException.WrongSurfaceFormat("$surface format is not valid"))
                !description.isNameValid() -> Single.error(DomainFormException.WrongDescriptionFormat("$description format is not valid"))
                !agent.isNameValid() -> Single.error(DomainFormException.WrongAgentFormat("$agent format is not valid"))
                !totalRoomNumber.isOnlyNumber() -> Single.error(DomainFormException.WrongTotalRoomNumberFormat("$totalRoomNumber format is not valid"))
                !bedroomNumber.isOnlyNumber() -> Single.error(DomainFormException.WrongBedRoomNumberFormat("$bedroomNumber format is not valid"))
                !bathroomNumber.isOnlyNumber() -> Single.error(DomainFormException.WrongBathRoomNumberFormat("$bathroomNumber format is not valid"))
                !address.country.isNameValid() -> Single.error(DomainFormException.WrongCountryFormat("${address.country} format is not valid"))
                !address.road.isNameValid() -> Single.error(DomainFormException.WrongRoadFormat("${address.road} format is not valid"))
                !address.houseNumber.isOnlyNumber() -> Single.error(DomainFormException.WrongHouseNumberFormat("${address.houseNumber} format is not valid"))
                !address.city.isNameValid() -> Single.error(DomainFormException.WrongCityFormat("${address.city} format is not valid"))
                !address.postalCode.isOnlyNumber() -> Single.error(DomainFormException.WrongPostalCodeFormat("${address.postalCode} format is not valid"))
                else -> {

                    realEstateRepository.createRealEstate(
                        DomainRealEstateMasterDetail(
                            id = id,
                            type = type,
                            price = price,
                            surface = surface,
                            school = school,
                            commerce = commerce,
                            parc = parc,
                            trainStation = trainStation,
                            description = description,
                            isSold = isSold,
                            entryDate = entryDate,
                            exitDate = exitDate,
                            agent = agent,
                            totalRoomNumber = totalRoomNumber,
                            bedroomNumber = bedroomNumber,
                            bathroomNumber = bathroomNumber,
                            address = DomainAddress(
                                country = address.country,
                                city = address.city,
                                houseNumber = address.houseNumber,
                                road = address.road,
                                postalCode = address.postalCode
                            ),
                            photos = photos.map { photo ->
                                DomainPhoto(
                                    photoReference = photo.photoReference,
                                    roomType = photo.roomName
                                )
                            }
                        )
                    )
                }
            }
        }
    }
}