package com.openclassrooms.realestatemanager.domain.repositories.local

import com.openclassrooms.realestatemanager.domain.models.DomainRealEstateCondense
import com.openclassrooms.realestatemanager.domain.models.DomainRealEstateMasterDetail
import io.reactivex.rxjava3.core.Single

interface RealEstateRepository {
    fun createRealEstate(domainRealEstateMasterDetail: DomainRealEstateMasterDetail): Single<Unit>
    fun getRealEstateCondense(): Single<List<DomainRealEstateCondense>>
}