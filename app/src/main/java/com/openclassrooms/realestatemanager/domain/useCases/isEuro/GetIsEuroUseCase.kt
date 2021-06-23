package com.openclassrooms.realestatemanager.domain.useCases.isEuro

import com.openclassrooms.realestatemanager.domain.repositories.local.RealEstateRepository
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class GetIsEuroUseCase @Inject constructor(
    private val realEstateRepository: RealEstateRepository
) {
    fun invoke(): Single<Boolean> =
        realEstateRepository.getIsEuro()
}