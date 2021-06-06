package com.openclassrooms.realestatemanager.utils.fixtures.entities

import com.openclassrooms.realestatemanager.domain.models.DomainAddress
import com.openclassrooms.realestatemanager.domain.models.DomainPhoto
import com.openclassrooms.realestatemanager.domain.models.DomainRealEstateCondense
import com.openclassrooms.realestatemanager.domain.models.DomainRealEstateMasterDetail
import com.openclassrooms.realestatemanager.domain.models.permissions.DomainAccessPermission
import com.openclassrooms.realestatemanager.domain.models.permissions.DomainMultiplePermission

class DomainFixtures {
    
    class DomainRealEstateCondenseUtils {
        companion object {
            fun create(): DomainRealEstateCondense =
                    DomainRealEstateCondense(
                            id = FixturesConstants.RealEstate.id,
                            type = FixturesConstants.RealEstate.type,
                            city = FixturesConstants.Address.city,
                            price = FixturesConstants.RealEstate.price,
                            isSold = FixturesConstants.RealEstate.isSold,
                            photo = FixturesConstants.Photo.photoReference
                    )
        }
    }
    
    class DomainRealEstateMasterDetailUtils {
        companion object {
            fun create(): DomainRealEstateMasterDetail =
                    DomainRealEstateMasterDetail(
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
                            address = DomainAddressUtils.create(),
                            photos =  listOf(DomainPhotoUtils.create())
                    )
        }
    }
    
    class DomainAddressUtils {
        companion object {
            fun create(): DomainAddress =
                    DomainAddress(
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
    
    class DomainPhotoUtils {
        companion object {
            fun create(): DomainPhoto =
                    DomainPhoto(
                            id = FixturesConstants.Photo.id,
                            photoReference = FixturesConstants.Photo.photoReference,
                            roomType = FixturesConstants.Photo.roomType
                    )
        }
    }
    
    class DomainMultiplePermissionsUtils {
        companion object {
            fun createEmpty(): DomainMultiplePermission =
                    DomainMultiplePermission(listOf(), listOf())
            
            fun createWithGeolocation(): DomainMultiplePermission = DomainMultiplePermission(
                    listOf(
                            DomainAccessPermission.GEOLOCATION_FOREGROUND,
                            DomainAccessPermission.GEOLOCATION_BACKGROUND
                    ), listOf()
            )
            
            fun createWithoutGeolocation(): DomainMultiplePermission = DomainMultiplePermission(
                    listOf(),
                    listOf(
                            DomainAccessPermission.GEOLOCATION_FOREGROUND,
                            DomainAccessPermission.GEOLOCATION_BACKGROUND
                    )
            )
            
            fun createWithForegroundGeolocation(): DomainMultiplePermission =
                    DomainMultiplePermission(
                            listOf(DomainAccessPermission.GEOLOCATION_FOREGROUND),
                            listOf(DomainAccessPermission.GEOLOCATION_BACKGROUND)
                    )
        }
    }
}