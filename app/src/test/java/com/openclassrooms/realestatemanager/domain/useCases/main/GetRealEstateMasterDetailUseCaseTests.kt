package com.openclassrooms.realestatemanager.domain.useCases.main

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.verifyNoMoreInteractions
import com.nhaarman.mockitokotlin2.whenever
import com.openclassrooms.realestatemanager.domain.repositories.local.RealEstateRepository
import com.openclassrooms.realestatemanager.utils.fixtures.entities.DomainFixtures
import io.reactivex.rxjava3.core.Single
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class GetRealEstateMasterDetailUseCaseTests {
    private val repository: RealEstateRepository = mock()
    private val getRealEstateMasterDetail = GetRealEstateMasterDetailUseCase(repository)
    
    @Test
    fun testInvoke() {
        val expectedDomainRealEstateMasterDetailItem = DomainFixtures.DomainRealEstateMasterDetailUtils.create()
        whenever(getRealEstateMasterDetail.invoke(0)).thenReturn(Single.just(expectedDomainRealEstateMasterDetailItem))
    
        getRealEstateMasterDetail.invoke(0)
                .test()
                .assertValue(expectedDomainRealEstateMasterDetailItem)
        
        verify(repository).getRealEstateMasterDetail(0)
        verifyNoMoreInteractions(repository)
    }
}