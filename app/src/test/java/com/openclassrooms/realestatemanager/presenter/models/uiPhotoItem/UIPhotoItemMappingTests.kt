package com.openclassrooms.realestatemanager.presenter.models.uiPhotoItem

import com.openclassrooms.realestatemanager.presenter.models.uiAddressItem.toUIItem
import com.openclassrooms.realestatemanager.utils.fixtures.entities.DomainFixtures
import com.openclassrooms.realestatemanager.utils.fixtures.entities.PresenterFixtures
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class UIPhotoItemMappingTests {
    
    @Test
    fun testToUIItem() {
        val expectedUIItem = PresenterFixtures.UIPhotoItemUtils.create()
        val actualDomain = DomainFixtures.DomainPhotoUtils.create()
        assertThat(actualDomain.toUIItem(), equalTo(expectedUIItem))
    }
}