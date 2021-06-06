package com.openclassrooms.realestatemanager.presenter.modules.editRealEstate

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.verifyNoMoreInteractions
import com.nhaarman.mockitokotlin2.whenever
import com.openclassrooms.realestatemanager.domain.useCases.editRealEstate.EditRealEstateUseCase
import com.openclassrooms.realestatemanager.domain.useCases.main.GetRealEstateMasterDetailUseCase
import com.openclassrooms.realestatemanager.utils.TestNetworkSchedulers
import com.openclassrooms.realestatemanager.utils.fixtures.entities.DomainFixtures
import com.openclassrooms.realestatemanager.utils.fixtures.entities.PresenterFixtures
import io.reactivex.rxjava3.core.Single
import org.hamcrest.CoreMatchers
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class EditRealEstatePresenterTests {
    private val mockGetRealEstateMasterDetail: GetRealEstateMasterDetailUseCase = mock()
    private val mockEditRealEstate: EditRealEstateUseCase = mock()
    private val testNetworkSchedulers = TestNetworkSchedulers()
    private val mockView: EditRealEstateView = mock()
    private val presenter = EditRealEstatePresenterImpl(mockGetRealEstateMasterDetail, mockEditRealEstate, testNetworkSchedulers)
    
    @Before
    fun setup() {
        presenter.attach(mockView)
        
        presenter.entryDate = "04/06/2021"
        presenter.addressId = 0
        presenter.id = 0
        presenter.address = PresenterFixtures.UIAddressItemUtils.create()
    }
    
    @Test
    fun testSetup() {
        val expectedId: Long = 0
        val expectedDomainRealEstateMasterDetail = DomainFixtures.DomainRealEstateMasterDetailUtils.create()
        val expectedUIRealEstateMasterDetail = PresenterFixtures.UIRealEstateMasterDetailItemUtils.create()
        whenever(mockGetRealEstateMasterDetail.invoke(0)).thenReturn(Single.just(expectedDomainRealEstateMasterDetail))
        
        presenter.setup(expectedId)
    
        verify(mockView).onSetupUI(expectedUIRealEstateMasterDetail)
        verify(mockGetRealEstateMasterDetail).invoke(expectedId)
        verifyNoMoreInteractions(mockView, mockGetRealEstateMasterDetail)
    }
    
    @Test
    fun testDidAddPhoto() {
        val expectedUIPhotoItem = PresenterFixtures.UIPhotoItemUtils.create()
        
        presenter.didAddPhoto(expectedUIPhotoItem.roomType!!, expectedUIPhotoItem.photoReference)
    
        assertThat(presenter.photos, equalTo(arrayListOf(expectedUIPhotoItem)))
        verify(mockView).onUpdateList(presenter.photos)
        verifyNoMoreInteractions(mockView, mockGetRealEstateMasterDetail)
    }
    
    @Test
    fun testDidDeletePhoto() {
        val expectedUIPhotoItem = PresenterFixtures.UIPhotoItemUtils.create()
        presenter.photos = arrayListOf(expectedUIPhotoItem)
        
        presenter.didDeletePhoto(expectedUIPhotoItem)
    
        assertThat(presenter.photos, equalTo(arrayListOf()))
        verify(mockView).onUpdateList(presenter.photos)
        verifyNoMoreInteractions(mockView, mockGetRealEstateMasterDetail)
    }
}