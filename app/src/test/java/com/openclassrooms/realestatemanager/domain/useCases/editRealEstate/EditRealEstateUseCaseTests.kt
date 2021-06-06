package com.openclassrooms.realestatemanager.domain.useCases.editRealEstate

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
class EditRealEstateUseCaseTests {
    private val repository: RealEstateRepository = mock()
    private val editRealEstate = EditRealEstateUseCase(repository)
    
    @Test
    fun testInvoke() {
        val expectedUIRealEstateMasterDetailItem = PresenterFixtures.UIRealEstateMasterDetailItemUtils.create()
        val expectedDomainRealEstateMasterDetailItem = DomainFixtures.DomainRealEstateMasterDetailUtils.create()
        val expectedUIPhotoItem = PresenterFixtures.UIPhotoItemUtils.create()
        val expectedDomainPhotoItem = DomainFixtures.DomainPhotoUtils.create()
    
        editRealEstate.invoke(expectedUIRealEstateMasterDetailItem, listOf(expectedUIPhotoItem))
        
        verify(repository).editRealEstate(expectedDomainRealEstateMasterDetailItem, listOf(expectedDomainPhotoItem))
        verifyNoMoreInteractions(repository)
    }
}