package com.openclassrooms.realestatemanager.domain.useCases.map

import com.openclassrooms.realestatemanager.domain.models.DomainRealEstateMasterDetail
import com.openclassrooms.realestatemanager.domain.repositories.local.RealEstateRepository
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class GetAllRealEstateMasterDetailUseCase @Inject constructor(
    private val realEstateRepository: RealEstateRepository
) {
    fun invoke(): Single<List<DomainRealEstateMasterDetail>> =
            realEstateRepository.getAllRealEstateMasterDetail()
}