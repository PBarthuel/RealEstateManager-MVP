package com.openclassrooms.realestatemanager.domain.useCases.isEuro

import com.openclassrooms.realestatemanager.domain.repositories.local.RealEstateRepository
import io.reactivex.rxjava3.core.Completable
import javax.inject.Inject

class CreateIsEuroUseCase @Inject constructor(
    private val realEstateRepository: RealEstateRepository
) {
    fun invoke(): Completable =
            realEstateRepository.createIsEuro(0, false)
}
