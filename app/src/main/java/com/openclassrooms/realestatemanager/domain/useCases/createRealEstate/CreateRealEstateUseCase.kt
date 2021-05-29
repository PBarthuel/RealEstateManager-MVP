package com.openclassrooms.realestatemanager.domain.useCases.createRealEstate

import com.openclassrooms.realestatemanager.domain.models.DomainAddress
import com.openclassrooms.realestatemanager.domain.models.DomainFormException
import com.openclassrooms.realestatemanager.domain.models.DomainPhoto
import com.openclassrooms.realestatemanager.domain.models.DomainRealEstateMasterDetail
import com.openclassrooms.realestatemanager.domain.repositories.local.RealEstateRepository
import com.openclassrooms.realestatemanager.domain.utils.isNameValid
import com.openclassrooms.realestatemanager.domain.utils.isOnlyNumber
import com.openclassrooms.realestatemanager.presenter.models.uiRealEstateMasterDetailItem.UIRealEstateMasterDetailItem
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.core.SingleOnSubscribe
import java.lang.Exception
import javax.inject.Inject

class CreateRealEstateUseCase @Inject constructor(
    private val realEstateRepository: RealEstateRepository
) {

    fun invoke(item: UIRealEstateMasterDetailItem): Single<Unit> {
        with(item) {
            return when {
                !type.isNameValid() -> Single.error(DomainFormException.WrongTypeFormat("$type format is not valid"))
                !price.isOnlyNumber() -> Single.error(DomainFormException.WrongPriceFormat("$price format is not valid"))
                else -> {

                    realEstateRepository.createRealEstate(
                        DomainRealEstateMasterDetail(
                            id = id,
                            type = type,
                            price = price,
                            surface = surface,
                            interestPoint = interestPoint,
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