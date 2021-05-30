package com.openclassrooms.realestatemanager.domain.useCases.main

import com.openclassrooms.realestatemanager.domain.models.DomainRealEstateMasterDetail
import com.openclassrooms.realestatemanager.domain.repositories.local.RealEstateRepository
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class GetRealEstateMasterDetailUseCase @Inject constructor(
    private val realEstateRepository: RealEstateRepository
) {
    fun invoke(id: Long): Single<DomainRealEstateMasterDetail> =
        realEstateRepository.getRealEstateMasterDetail(id)
}