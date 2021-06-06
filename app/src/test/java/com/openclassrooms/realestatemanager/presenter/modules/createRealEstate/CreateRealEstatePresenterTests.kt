package com.openclassrooms.realestatemanager.presenter.modules.createRealEstate

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.verifyNoMoreInteractions
import com.nhaarman.mockitokotlin2.whenever
import com.openclassrooms.realestatemanager.domain.useCases.createRealEstate.CreateRealEstateUseCase
import com.openclassrooms.realestatemanager.presenter.models.uiPhotoItem.UIPhotoItem
import com.openclassrooms.realestatemanager.utils.TestNetworkSchedulers
import com.openclassrooms.realestatemanager.utils.fixtures.entities.PresenterFixtures
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class CreateRealEstatePresenterTests {
    private val mockCreateRealEstate: CreateRealEstateUseCase = mock()
    private val testNetworkSchedulers = TestNetworkSchedulers()
    private val mockView: CreateRealEstateView = mock()
    private val presenter = CreateRealEstatePresenterImpl(mockCreateRealEstate, testNetworkSchedulers)
    
    @Before
    fun setup() {
        presenter.attach(mockView)
    }
    
    @Test
    fun testDidSubmitRealEstate() {
        // TODO regarder pourquoi j'ai une null pointer
        val expectedUIRealEstateMasterDetail = PresenterFixtures.UIRealEstateMasterDetailItemUtils.create()
        presenter.address = PresenterFixtures.UIAddressItemUtils.create()
        presenter.photos = arrayListOf(PresenterFixtures.UIPhotoItemUtils.create())
        whenever(mockCreateRealEstate.invoke(expectedUIRealEstateMasterDetail)).thenReturn(Completable.complete())
        
        presenter.didSubmitRealEstate(
            expectedUIRealEstateMasterDetail.type,
            expectedUIRealEstateMasterDetail.price,
            expectedUIRealEstateMasterDetail.surface,
            expectedUIRealEstateMasterDetail.description,
            expectedUIRealEstateMasterDetail.school,
            expectedUIRealEstateMasterDetail.commerce,
            expectedUIRealEstateMasterDetail.parc,
            expectedUIRealEstateMasterDetail.trainStation,
            expectedUIRealEstateMasterDetail.agent,
            expectedUIRealEstateMasterDetail.totalRoomNumber,
            expectedUIRealEstateMasterDetail.bedroomNumber,
            expectedUIRealEstateMasterDetail.bathroomNumber
        )
    
        verify(mockView).onDismissView()
        verify(mockCreateRealEstate).invoke(expectedUIRealEstateMasterDetail)
        verifyNoMoreInteractions(mockView, mockCreateRealEstate)
    }
    
    @Test
    fun testDidChooseAddress() {
        val expectedUIAddressItem = PresenterFixtures.UIAddressItemUtils.create()
        
        presenter.didChooseAddress(expectedUIAddressItem)
    
        assertThat(presenter.address, equalTo(expectedUIAddressItem))
        verify(mockView).onShowAddress(expectedUIAddressItem.getString())
        verifyNoMoreInteractions(mockView, mockCreateRealEstate)
    }
    
    @Test
    fun testDidAddPhoto() {
        val expectedUIPhotoItem = PresenterFixtures.UIPhotoItemUtils.create()
        
        presenter.didAddPhoto(expectedUIPhotoItem.roomType!!, expectedUIPhotoItem.photoReference)
    
        assertThat(presenter.photos, equalTo(arrayListOf(expectedUIPhotoItem)))
        verify(mockView).onUpdateList(presenter.photos)
        verifyNoMoreInteractions(mockView, mockCreateRealEstate)
    }
    
    @Test
    fun testDidDeletePhoto() {
        val expectedUIPhotoItem = PresenterFixtures.UIPhotoItemUtils.create()
        presenter.photos = arrayListOf(expectedUIPhotoItem)
        
        presenter.didDeletePhoto(expectedUIPhotoItem)
        
        assertThat(presenter.photos, equalTo(arrayListOf()))
        verify(mockView).onUpdateList(presenter.photos)
        verifyNoMoreInteractions(mockView, mockCreateRealEstate)
    }
}