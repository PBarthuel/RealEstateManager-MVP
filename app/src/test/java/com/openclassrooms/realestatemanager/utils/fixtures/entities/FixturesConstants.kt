package com.openclassrooms.realestatemanager.utils.fixtures.entities

class FixturesConstants {
    
    class RealEstate {
        companion object {
            const val id: Long = 0
            const val type = "appartement"
            const val price = "100000"
            const val surface = "120"
            const val description = "Very beautiful place"
            const val isSold = true
            const val school = true
            const val commerce = false
            const val parc = true
            const val trainStation = true
            const val entryDate = "04/06/2021"
            const val exitDate = "04/06/2022"
            const val agent = "Gerard"
            const val totalRoomNumber = "8"
            const val bedRoomNumber = "4"
            const val bathRoomNumber = "2"
        }
    }
    
    class Address {
        companion object {
            const val id: Long = 0
            const val country = "France"
            const val city = "Paris"
            const val road = "Rue du paradis"
            const val postalCode = "75012"
            const val latitude: Double = 4.12
            const val longitude: Double = 3.14
        }
    }
    
    class Photo {
        companion object {
            const val id: Long = 0
            const val photoReference = "courgette"
            const val roomType = "bedRoom"
        }
    }
}