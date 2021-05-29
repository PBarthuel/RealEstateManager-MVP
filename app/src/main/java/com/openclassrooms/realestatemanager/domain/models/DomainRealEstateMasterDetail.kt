package com.openclassrooms.realestatemanager.domain.models

data class DomainRealEstateMasterDetail(
    val id: Long,
    val type: String,
    val price: String,
    val surface: String,
    val description: String,
    val school: Boolean,
    val commerce: Boolean,
    val parc: Boolean,
    val trainStation: Boolean,
    val isSold: Boolean,
    val entryDate: String,
    val exitDate: String,
    val agent: String,
    val totalRoomNumber: String,
    val bedroomNumber: String,
    val bathroomNumber: String,
    val address: DomainAddress,
    val photos: List<DomainPhoto>
)