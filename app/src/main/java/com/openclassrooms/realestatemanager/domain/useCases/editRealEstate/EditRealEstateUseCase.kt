package com.openclassrooms.realestatemanager.domain.useCases.editRealEstate

import com.openclassrooms.realestatemanager.domain.models.DomainAddress
import com.openclassrooms.realestatemanager.domain.models.DomainPhoto
import com.openclassrooms.realestatemanager.domain.models.DomainRealEstateMasterDetail
import com.openclassrooms.realestatemanager.domain.repositories.local.RealEstateRepository
import com.openclassrooms.realestatemanager.presenter.models.uiPhotoItem.UIPhotoItem
import com.openclassrooms.realestatemanager.presenter.models.uiRealEstateMasterDetailItem.UIRealEstateMasterDetailItem
import io.reactivex.rxjava3.core.Completable
import javax.inject.Inject

class EditRealEstateUseCase @Inject constructor(
    private val realEstateRepository: RealEstateRepository
) {

    fun invoke(item: UIRealEstateMasterDetailItem, photosToDelete: List<UIPhotoItem>): Completable {
        with(item) {
            return realEstateRepository.editRealEstate(
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
                        id = address.id,
                        country = address.country,
                        city = address.city,
                        road = address.road,
                        postalCode = address.postalCode,
                        latitude = address.latitude,
                        longitude = address.longitude
                    ),
                    photos = photos.map { photo ->
                        DomainPhoto(
                            0,
                            photoReference = photo.photoReference,
                            roomType = photo.roomType
                        )
                    }
                ),
                photosToDelete.map {
                    DomainPhoto(
                        it.id,
                        it.photoReference,
                        it.roomType
                    )
                }
            )
        }
    }
}
