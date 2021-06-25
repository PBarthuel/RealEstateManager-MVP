package com.openclassrooms.realestatemanager.domain.useCases.isEuro

import com.openclassrooms.realestatemanager.domain.repositories.local.RealEstateRepository
import io.reactivex.rxjava3.core.Completable
import javax.inject.Inject

class UpdateIsEuroUseCase @Inject constructor(
    private val realEstateRepository: RealEstateRepository
) {
    fun invoke(isEuro: Boolean): Completable =
            realEstateRepository.updateIsEuro(isEuro)
}