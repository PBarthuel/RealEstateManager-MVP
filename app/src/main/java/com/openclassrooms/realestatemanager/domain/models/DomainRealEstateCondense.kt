package com.openclassrooms.realestatemanager.domain.models

data class DomainRealEstateCondense(
    val id: Long,
    val type: String,
    val city: String,
    val price: String,
    val isSold: Boolean,
    val photo: String
)