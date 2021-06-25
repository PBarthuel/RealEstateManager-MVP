package com.openclassrooms.realestatemanager.utils.fixtures.entities

import com.openclassrooms.realestatemanager.app.utils.Utils
import com.openclassrooms.realestatemanager.presenter.models.uiAddressItem.UIAddressItem
import com.openclassrooms.realestatemanager.presenter.models.uiPhotoItem.UIPhotoItem
import com.openclassrooms.realestatemanager.presenter.models.uiRealEstateCondenseItem.UIRealEstateCondenseItem
import com.openclassrooms.realestatemanager.presenter.models.uiRealEstateMasterDetailItem.UIRealEstateMasterDetailItem

class PresenterFixtures {
    
    class UIRealEstateCondenseItemUtils {
        companion object {
            fun createDollar(): UIRealEstateCondenseItem =
                UIRealEstateCondenseItem(
                    id = FixturesConstants.RealEstate.id,
                    type = FixturesConstants.RealEstate.type,
                    city = FixturesConstants.Address.city,
                    price = "${FixturesConstants.RealEstate.price}$",
                    isSold = FixturesConstants.RealEstate.isSold,
                    photo = FixturesConstants.Photo.photoReference
                )
            fun createEuro(): UIRealEstateCondenseItem =
                UIRealEstateCondenseItem(
                    id = FixturesConstants.RealEstate.id,
                    type = FixturesConstants.RealEstate.type,
                    city = FixturesConstants.Address.city,
                    price = Utils.convertDollarToEuro(FixturesConstants.RealEstate.price.toInt()).toString() + "€",
                    isSold = FixturesConstants.RealEstate.isSold,
                    photo = FixturesConstants.Photo.photoReference
                )
        }
    }
    
    class UIRealEstateMasterDetailItemUtils {
        companion object {
            fun create(): UIRealEstateMasterDetailItem =
                UIRealEstateMasterDetailItem(
                    id = FixturesConstants.RealEstate.id,
                    type = FixturesConstants.RealEstate.type,
                    price = FixturesConstants.RealEstate.price,
                    surface = FixturesConstants.RealEstate.surface,
                    description = FixturesConstants.RealEstate.description,
                    school = FixturesConstants.RealEstate.school,
                    commerce = FixturesConstants.RealEstate.commerce,
                    parc = FixturesConstants.RealEstate.parc,
                    trainStation = FixturesConstants.RealEstate.trainStation,
                    isSold = FixturesConstants.RealEstate.isSold,
                    entryDate = FixturesConstants.RealEstate.entryDate,
                    exitDate = FixturesConstants.RealEstate.exitDate,
                    agent = FixturesConstants.RealEstate.agent,
                    totalRoomNumber = FixturesConstants.RealEstate.totalRoomNumber,
                    bedroomNumber = FixturesConstants.RealEstate.bedRoomNumber,
                    bathroomNumber = FixturesConstants.RealEstate.bathRoomNumber,
                    address = UIAddressItemUtils.create(),
                    photos =  listOf(UIPhotoItemUtils.create())
                )
        }
    }
    
    class UIAddressItemUtils {
        companion object {
            fun create(): UIAddressItem =
                UIAddressItem(
                    id = FixturesConstants.Address.id,
                    country = FixturesConstants.Address.country,
                    city = FixturesConstants.Address.city,
                    road = FixturesConstants.Address.road,
                    postalCode = FixturesConstants.Address.postalCode,
                    latitude = FixturesConstants.Address.latitude,
                    longitude = FixturesConstants.Address.longitude
                )
        }
    }
    
    class UIPhotoItemUtils {
        companion object {
            fun create(): UIPhotoItem =
                UIPhotoItem(
                    id = FixturesConstants.Photo.id,
                    photoReference = FixturesConstants.Photo.photoReference,
                    roomType = FixturesConstants.Photo.roomType
                )
        }
    }
}