package com.openclassrooms.realestatemanager.presenter.models

import com.openclassrooms.realestatemanager.utils.fixtures.entities.DomainFixtures
import com.openclassrooms.realestatemanager.utils.fixtures.entities.PresenterFixtures
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class UIRealEstateItemMappingTests {
    
    @Test
    fun testToUICondenseItemDollar() {
        val expectedUIItem = PresenterFixtures.UIRealEstateCondenseItemUtils.createDollar()
        val actualDomain = DomainFixtures.DomainRealEstateCondenseUtils.create()
        assertThat(actualDomain.toUICondenseItemDollar(), equalTo(expectedUIItem))
    }
    
    @Test
    fun testToUICondenseItemEuro() {
        val expectedUIItem = PresenterFixtures.UIRealEstateCondenseItemUtils.createEuro()
        val actualDomain = DomainFixtures.DomainRealEstateCondenseUtils.create()
        assertThat(actualDomain.toUICondenseItemEuro(), equalTo(expectedUIItem))
    }
    
    @Test
    fun testToUIMasterDetailItem() {
        val expectedUIItem = PresenterFixtures.UIRealEstateMasterDetailItemUtils.create()
        val actualDomain = DomainFixtures.DomainRealEstateMasterDetailUtils.create()
        assertThat(actualDomain.toUIMasterDetailItem(), equalTo(expectedUIItem))
    }
}