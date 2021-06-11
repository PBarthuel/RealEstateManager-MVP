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
class GetRealEstateCondenseUseCaseTests {
    private val repository: RealEstateRepository = mock()
    private val getRealEstateCondense = GetRealEstateCondenseUseCase(repository)
    
    @Test
    fun testInvoke() {
        val expectedDomainRealEstateCondenseItem = DomainFixtures.DomainRealEstateCondenseUtils.create()
        whenever(getRealEstateCondense.invoke()).thenReturn(Single.just(listOf(expectedDomainRealEstateCondenseItem)))
    
        getRealEstateCondense.invoke()
                .test()
                .assertValue(listOf(expectedDomainRealEstateCondenseItem))
        
        verify(repository).getRealEstateCondense()
        verifyNoMoreInteractions(repository)
    }
}