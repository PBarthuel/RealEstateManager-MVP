package com.openclassrooms.realestatemanager.data.repositories.local

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.verifyNoMoreInteractions
import com.nhaarman.mockitokotlin2.whenever
import com.openclassrooms.realestatemanager.data.vendors.local.RealEstateDao
import com.openclassrooms.realestatemanager.data.vendors.local.objectRequest.PhotoRequest
import com.openclassrooms.realestatemanager.domain.models.DomainPhoto
import com.openclassrooms.realestatemanager.utils.fixtures.entities.DataFixtures
import com.openclassrooms.realestatemanager.utils.fixtures.entities.DomainFixtures
import io.reactivex.rxjava3.core.Single
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class RealEstateRepositoryTests {
    private val mockDao: RealEstateDao = mock()
    private val repository = RealEstateRepositoryImpl(mockDao)
    
    @Test
    fun testGetRealEstateCondense() {
        val expectedRealEstateCondense = DataFixtures.RealEstateCondenseResponseUtils.create()
        val expectedDomainRealEstateCondenseUtils =  DomainFixtures.DomainRealEstateCondenseUtils.create()
        whenever(mockDao.getRealEstateCondense()).thenReturn(Single.just(listOf(expectedRealEstateCondense)))
        
        repository.getRealEstateCondense()
                .test()
                .assertValue(listOf(expectedDomainRealEstateCondenseUtils))
        
        verify(mockDao).getRealEstateCondense()
        verifyNoMoreInteractions(mockDao)
    }
    
    @Test
    fun testCreateRealEstate() {
        val expectedRealEstateRequest = DataFixtures.RealEstateRequestUtils.create()
        val expectedAddressRequest = DataFixtures.AddressRequestUtils.create()
        val expectedPhotoRequest = DataFixtures.PhotoRequestUtils.create()
        val expectedDomainRealEstateMasterDetail = DomainFixtures.DomainRealEstateMasterDetailUtils.create()
        whenever(mockDao.createRealEstate(expectedRealEstateRequest)).thenReturn(0)
        
        repository.createRealEstate(expectedDomainRealEstateMasterDetail)
                .test()
                .assertComplete()
        
        verify(mockDao).createRealEstate(expectedRealEstateRequest)
        verify(mockDao).createAddress(expectedAddressRequest)
        verify(mockDao).createPhoto(listOf(expectedPhotoRequest))
        verifyNoMoreInteractions(mockDao)
    }
    
    @Test
    fun testEditRealEstate() {
        val expectedRealEstateRequest = DataFixtures.RealEstateRequestUtils.create()
        val expectedAddressRequest = DataFixtures.AddressRequestUtils.create()
        val expectedPhotoRequest = DataFixtures.PhotoRequestUtils.create()
        val expectedDomainRealEstateMasterDetail = DomainFixtures.DomainRealEstateMasterDetailUtils.create()
        
        repository.editRealEstate(expectedDomainRealEstateMasterDetail, listOf(DomainPhoto(0, "25dzd", "courgette")))
                .test()
                .assertComplete()
        
        verify(mockDao).updateRealEstate(expectedRealEstateRequest)
        verify(mockDao).updateAddress(expectedAddressRequest)
        verify(mockDao).createPhoto(listOf(expectedPhotoRequest))
        verify(mockDao).deletePhoto(PhotoRequest(0, 0, "25dzd", "courgette"))
        verifyNoMoreInteractions(mockDao)
    }
    
    @Test
    fun testGetRealEstateMasterDetail() {
        val expectedRealEstateMasterDetailResponse = DataFixtures.RealEstateMasterDetailResponseUtils.create()
        val expectedDomainRealEstateMasterDetail = DomainFixtures.DomainRealEstateMasterDetailUtils.create()
        whenever(mockDao.getRealEstateMasterDetail(0)).thenReturn(Single.just(expectedRealEstateMasterDetailResponse))
        
        repository.getRealEstateMasterDetail(0)
                .test()
                .assertValue(expectedDomainRealEstateMasterDetail)
        
        verify(mockDao).getRealEstateMasterDetail(0)
        verifyNoMoreInteractions(mockDao)
    }
    
    @Test
    fun testGetAllRealEstateMasterDetail() {
        val expectedRealEstateMasterDetailResponse = DataFixtures.RealEstateMasterDetailResponseUtils.create()
        val expectedDomainRealEstateMasterDetail = DomainFixtures.DomainRealEstateMasterDetailUtils.create()
        whenever(mockDao.getAllRealEstateMasterDetail()).thenReturn(Single.just(listOf(expectedRealEstateMasterDetailResponse)))
        
        repository.getAllRealEstateMasterDetail()
                .test()
                .assertValue(listOf(expectedDomainRealEstateMasterDetail))
        
        verify(mockDao).getAllRealEstateMasterDetail()
        verifyNoMoreInteractions(mockDao)
    }
}