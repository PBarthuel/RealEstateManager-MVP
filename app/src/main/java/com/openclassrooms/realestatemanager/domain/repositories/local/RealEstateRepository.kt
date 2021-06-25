package com.openclassrooms.realestatemanager.domain.repositories.local

import com.openclassrooms.realestatemanager.domain.models.DomainPhoto
import com.openclassrooms.realestatemanager.domain.models.DomainRealEstateCondense
import com.openclassrooms.realestatemanager.domain.models.DomainRealEstateMasterDetail
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

interface RealEstateRepository {
    fun createRealEstate(domainRealEstateMasterDetail: DomainRealEstateMasterDetail): Completable
    fun getRealEstateCondense(): Single<List<DomainRealEstateCondense>>
    fun getRealEstateMasterDetail(id: Long): Single<DomainRealEstateMasterDetail>
    fun getAllRealEstateMasterDetail(): Single<List<DomainRealEstateMasterDetail>>
    fun editRealEstate(domainRealEstateMasterDetail: DomainRealEstateMasterDetail, photosToDelete: List<DomainPhoto>): Completable
    fun createIsEuro(id: Int, isEuro: Boolean): Completable
    fun getIsEuro(): Single<Boolean>
    fun updateIsEuro(isEuro: Boolean): Completable
}