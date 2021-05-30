package com.openclassrooms.realestatemanager.domain.useCases.main

import com.openclassrooms.realestatemanager.domain.models.DomainRealEstateCondense
import com.openclassrooms.realestatemanager.domain.repositories.local.RealEstateRepository
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class GetRealEstateCondenseUseCase @Inject constructor(
    private val realEstateRepository: RealEstateRepository
) {
    fun invoke(): Single<List<DomainRealEstateCondense>> =
        realEstateRepository.getRealEstateCondense()
}