package com.openclassrooms.realestatemanager.presenter.modules.editRealEstate

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.verifyNoMoreInteractions
import com.nhaarman.mockitokotlin2.whenever
import com.openclassrooms.realestatemanager.domain.useCases.editRealEstate.EditRealEstateUseCase
import com.openclassrooms.realestatemanager.domain.useCases.main.GetRealEstateMasterDetailUseCase
import com.openclassrooms.realestatemanager.presenter.models.uiAddressItem.UIAddressItem
import com.openclassrooms.realestatemanager.presenter.models.uiRealEstateMasterDetailItem.UIRealEstateMasterDetailItem
import com.openclassrooms.realestatemanager.utils.TestNetworkSchedulers
import com.openclassrooms.realestatemanager.utils.fixtures.entities.DomainFixtures
import com.openclassrooms.realestatemanager.utils.fixtures.entities.FixturesConstants
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
        verifyNoMoreInteractions(mockView, mockEditRealEstate, mockGetRealEstateMasterDetail)
    }
    
    @Test
    fun testDidAddPhoto() {
        val expectedUIPhotoItem = PresenterFixtures.UIPhotoItemUtils.create()
        
        presenter.didAddPhoto(expectedUIPhotoItem.roomType!!, expectedUIPhotoItem.photoReference)
    
        assertThat(presenter.photos, equalTo(arrayListOf(expectedUIPhotoItem)))
        verify(mockView).onUpdateList(presenter.photos)
        verifyNoMoreInteractions(mockView, mockEditRealEstate, mockGetRealEstateMasterDetail)
    }
    
    @Test
    fun testDidDeletePhoto() {
        val expectedUIPhotoItem = PresenterFixtures.UIPhotoItemUtils.create()
        presenter.photos = arrayListOf(expectedUIPhotoItem)
        
        presenter.didDeletePhoto(expectedUIPhotoItem)
    
        assertThat(presenter.photos, equalTo(arrayListOf()))
        verify(mockView).onUpdateList(presenter.photos)
        verifyNoMoreInteractions(mockView, mockEditRealEstate, mockGetRealEstateMasterDetail)
    }
    
    @Test
    fun testDidChooseAddress() {
        val expectedUIAddressItem = PresenterFixtures.UIAddressItemUtils.create()
        val expectedAddressResult = UIAddressItem(
            1,
            FixturesConstants.Address.country,
            FixturesConstants.Address.city,
            FixturesConstants.Address.road,
            FixturesConstants.Address.postalCode,
            FixturesConstants.Address.latitude,
            FixturesConstants.Address.longitude
        )
        presenter.addressId = 1
    
        presenter.didChooseAddress(expectedUIAddressItem)
    
        assertThat(presenter.address, equalTo(expectedAddressResult))
        verify(mockView).onShowAddress(presenter.address?.getString()!!)
        verifyNoMoreInteractions(mockView, mockEditRealEstate, mockGetRealEstateMasterDetail)
    }
    
    @Test
    fun testDidSelectEdit() {
        val expectedUIRealEstateMasterDetail = PresenterFixtures.UIRealEstateMasterDetailItemUtils.create()
        presenter.id = 0
        presenter.addressId = 0
        presenter.entryDate = "04/06/2021"
        presenter.photosToDelete = arrayListOf()
        presenter.address = PresenterFixtures.UIAddressItemUtils.create()
        presenter.photos = arrayListOf(PresenterFixtures.UIPhotoItemUtils.create())
        val item = UIRealEstateMasterDetailItem(
                id = presenter.id,
                type = expectedUIRealEstateMasterDetail.type,
                price = expectedUIRealEstateMasterDetail.price,
                surface = expectedUIRealEstateMasterDetail.surface,
                description = expectedUIRealEstateMasterDetail.description,
                school = expectedUIRealEstateMasterDetail.school,
                commerce = expectedUIRealEstateMasterDetail.commerce,
                parc = expectedUIRealEstateMasterDetail.parc,
                trainStation = expectedUIRealEstateMasterDetail.trainStation,
                false,
                presenter.entryDate!!,
                "05/05/2085",
                agent = expectedUIRealEstateMasterDetail.agent,
                totalRoomNumber = expectedUIRealEstateMasterDetail.totalRoomNumber,
                bedroomNumber = expectedUIRealEstateMasterDetail.bedroomNumber,
                bathroomNumber = expectedUIRealEstateMasterDetail.bathroomNumber,
                presenter.address!!,
                presenter.photos
        )
        whenever(mockEditRealEstate.invoke(item, arrayListOf())).thenReturn(Completable.complete())
    
        presenter.didSelectEdit(
                type = expectedUIRealEstateMasterDetail.type,
                price = expectedUIRealEstateMasterDetail.price,
                surface = expectedUIRealEstateMasterDetail.surface,
                description = expectedUIRealEstateMasterDetail.description,
                isSold = expectedUIRealEstateMasterDetail.isSold,
                school = expectedUIRealEstateMasterDetail.school,
                commerce = expectedUIRealEstateMasterDetail.commerce,
                parc = expectedUIRealEstateMasterDetail.parc,
                trainStation = expectedUIRealEstateMasterDetail.trainStation,
                agent = expectedUIRealEstateMasterDetail.agent,
                totalRoomNumber = expectedUIRealEstateMasterDetail.totalRoomNumber,
                bedRoomNumber = expectedUIRealEstateMasterDetail.bedroomNumber,
                bathRoomNumber = expectedUIRealEstateMasterDetail.bathroomNumber
        )
    
        verify(mockView).onDismissView()
        verify(mockEditRealEstate).invoke(item, presenter.photosToDelete)
        verifyNoMoreInteractions(mockView, mockEditRealEstate, mockGetRealEstateMasterDetail)
    }
}