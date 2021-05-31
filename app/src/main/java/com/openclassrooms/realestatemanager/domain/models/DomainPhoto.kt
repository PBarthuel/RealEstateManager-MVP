package com.openclassrooms.realestatemanager.domain.models

data class DomainPhoto(
    val id: Long,
    val photoReference: String,
    val roomType: String?
)