package com.openclassrooms.realestatemanager.utils.fixtures.entities

import com.openclassrooms.realestatemanager.data.models.entities.RealEstateCondenseResponse
import com.openclassrooms.realestatemanager.data.models.entities.RealEstateMasterDetailResponse
import com.openclassrooms.realestatemanager.data.models.entities.addressSearch.AddressResponse
import com.openclassrooms.realestatemanager.data.vendors.local.objectRequest.AddressRequest
import com.openclassrooms.realestatemanager.data.vendors.local.objectRequest.PhotoRequest
import com.openclassrooms.realestatemanager.data.vendors.local.objectRequest.RealEstateRequest

class DataFixtures {
    
    class RealEstateCondenseResponseUtils {
        companion object {
            fun create(): RealEstateCondenseResponse =
                RealEstateCondenseResponse(
                        RealEstateRequestUtils.create(),
                        AddressRequestUtils.create(),
                        listOf(PhotoRequestUtils.create())
                )
        }
    }
    
    class RealEstateMasterDetailResponseUtils {
        companion object {
            fun create(): RealEstateMasterDetailResponse =
                RealEstateMasterDetailResponse(
                        RealEstateRequestUtils.create(),
                        AddressRequestUtils.create(),
                        listOf(PhotoRequestUtils.create())
                )
        }
    }
    
    class RealEstateRequestUtils {
        companion object {
            fun create(): RealEstateRequest =
                RealEstateRequest(
                        FixturesConstants.RealEstate.id,
                        FixturesConstants.RealEstate.type,
                        FixturesConstants.RealEstate.price,
                        FixturesConstants.RealEstate.surface,
                        FixturesConstants.RealEstate.description,
                        FixturesConstants.RealEstate.school,
                        FixturesConstants.RealEstate.commerce,
                        FixturesConstants.RealEstate.parc,
                        FixturesConstants.RealEstate.trainStation,
                        FixturesConstants.RealEstate.isSold,
                        FixturesConstants.RealEstate.entryDate,
                        FixturesConstants.RealEstate.exitDate,
                        FixturesConstants.RealEstate.agent,
                        FixturesConstants.RealEstate.totalRoomNumber,
                        FixturesConstants.RealEstate.bedRoomNumber,
                        FixturesConstants.RealEstate.bathRoomNumber
                )
        }
    }
    
    class AddressRequestUtils {
        companion object {
            fun create(): AddressRequest =
                AddressRequest(
                        FixturesConstants.Address.id,
                        FixturesConstants.RealEstate.id,
                        FixturesConstants.Address.country,
                        FixturesConstants.Address.city,
                        FixturesConstants.Address.road,
                        FixturesConstants.Address.postalCode,
                        FixturesConstants.Address.latitude,
                        FixturesConstants.Address.longitude
                )
        }
    }
    
    class AddressResponseUtils {
        companion object {
            fun create(): AddressResponse =
                AddressResponse(
                        FixturesConstants.Address.country,
                        FixturesConstants.Address.city,
                        FixturesConstants.Address.road,
                        FixturesConstants.Address.postalCode,
                        FixturesConstants.Address.latitude,
                        FixturesConstants.Address.longitude
                )
        }
    }
    
    class PhotoRequestUtils {
        companion object {
            fun create(): PhotoRequest =
                PhotoRequest(
                        FixturesConstants.Photo.id,
                        FixturesConstants.RealEstate.id,
                        FixturesConstants.Photo.photoReference,
                        FixturesConstants.Photo.roomType
                )
        }
    }
}