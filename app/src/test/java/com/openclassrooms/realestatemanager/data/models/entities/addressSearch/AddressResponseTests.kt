package com.openclassrooms.realestatemanager.data.models.entities.addressSearch

import com.openclassrooms.realestatemanager.utils.fixtures.entities.DataFixtures
import com.openclassrooms.realestatemanager.utils.fixtures.entities.DomainFixtures
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class AddressResponseTests {
    
    @Test
    fun testToDomain() {
        val expectedDomain = DomainFixtures.DomainAddressUtils.create()
        val actualResponse = DataFixtures.AddressResponseUtils.create()
        assertThat(actualResponse.toDomain(), equalTo(expectedDomain))
    }
}