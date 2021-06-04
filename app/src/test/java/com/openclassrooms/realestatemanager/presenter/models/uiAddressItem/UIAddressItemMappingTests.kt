package com.openclassrooms.realestatemanager.presenter.models.uiAddressItem

import com.openclassrooms.realestatemanager.utils.fixtures.entities.DomainFixtures
import com.openclassrooms.realestatemanager.utils.fixtures.entities.PresenterFixtures
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class UIAddressItemMappingTests {
    
    @Test
    fun testToUIItem() {
        val expectedUIItem = PresenterFixtures.UIAddressItemUtils.create()
        val actualDomain = DomainFixtures.DomainAddressUtils.create()
        assertThat(actualDomain.toUIItem(), equalTo(expectedUIItem))
    }
}