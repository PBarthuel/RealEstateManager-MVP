package com.openclassrooms.realestatemanager.domain.repositories.local

import com.openclassrooms.realestatemanager.domain.models.DomainRealEstateMasterDetail

interface RealEstateRepository {
    fun createRealEstate(domainRealEstateMasterDetail: DomainRealEstateMasterDetail)
}