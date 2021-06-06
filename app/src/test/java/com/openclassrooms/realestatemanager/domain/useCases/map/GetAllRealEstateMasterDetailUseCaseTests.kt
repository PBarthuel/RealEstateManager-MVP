package com.openclassrooms.realestatemanager.domain.useCases.map

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
class GetAllRealEstateMasterDetailUseCaseTests {
    private val repository: RealEstateRepository = mock()
    private val getAllRealEstateMasterDetail = GetAllRealEstateMasterDetailUseCase(repository)
    
    @Test
    fun testInvoke() {
        val expectedDomainRealEstateMasterDetailItem = DomainFixtures.DomainRealEstateMasterDetailUtils.create()
        whenever(getAllRealEstateMasterDetail.invoke()).thenReturn(Single.just(listOf(expectedDomainRealEstateMasterDetailItem)))
    
        getAllRealEstateMasterDetail.invoke()
            .test()
            .assertValue(listOf(expectedDomainRealEstateMasterDetailItem))
        
        verify(repository).getAllRealEstateMasterDetail()
        verifyNoMoreInteractions(repository)
    }
}