package com.openclassrooms.realestatemanager.domain.models

data class DomainAddress(
    val country: String,
    val city: String,
    val houseNumber: String,
    val road: String,
    val postalCode: String
)