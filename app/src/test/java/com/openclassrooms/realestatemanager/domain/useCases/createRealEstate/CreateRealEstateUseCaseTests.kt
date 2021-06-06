package com.openclassrooms.realestatemanager.domain.useCases.createRealEstate

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.verifyNoMoreInteractions
import com.openclassrooms.realestatemanager.domain.repositories.local.RealEstateRepository
import com.openclassrooms.realestatemanager.utils.fixtures.entities.DomainFixtures
import com.openclassrooms.realestatemanager.utils.fixtures.entities.PresenterFixtures
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class CreateRealEstateUseCaseTests {
    private val repository: RealEstateRepository = mock()
    private val createRealEstate = CreateRealEstateUseCase(repository)

    @Test
    fun testInvoke() {
        val expectedUIRealEstateMasterDetailItem = PresenterFixtures.UIRealEstateMasterDetailItemUtils.create()
        val expectedDomainRealEstateMasterDetailItem = DomainFixtures.DomainRealEstateMasterDetailUtils.create()
    
        createRealEstate.invoke(expectedUIRealEstateMasterDetailItem)

        verify(repository).createRealEstate(expectedDomainRealEstateMasterDetailItem)
        verifyNoMoreInteractions(repository)
    }
}