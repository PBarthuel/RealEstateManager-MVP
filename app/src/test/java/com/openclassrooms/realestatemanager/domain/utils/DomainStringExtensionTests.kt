package com.openclassrooms.realestatemanager.domain.utils

import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class DomainStringExtensionTests {

    @Test
    fun testIsNameValidSuccess() {
        var expectedValidName = "Valid"
        var result = expectedValidName.isNameValid()
        assertThat(result, equalTo(true))

        expectedValidName = "Valid-valid"
        result = expectedValidName.isNameValid()
        assertThat(result, equalTo(true))

        expectedValidName = "Valid valid"
        result = expectedValidName.isNameValid()
        assertThat(result, equalTo(true))
    }

    @Test
    fun testIsNameValidFailure() {
        var expectedNotValidName = "Not valid 1"
        var result = expectedNotValidName.isNameValid()
        assertThat(result, equalTo(false))

        expectedNotValidName = "Not 2 valid"
        result = expectedNotValidName.isNameValid()
        assertThat(result, equalTo(false))

        expectedNotValidName = "Not @ valid"
        result = expectedNotValidName.isNameValid()
        assertThat(result, equalTo(false))
    }
    
    @Test
    fun testIsNameOnlyNumberSuccess() {
        var expectedValidName = "10"
        var result = expectedValidName.isOnlyNumber()
        assertThat(result, equalTo(true))
    }
    
    @Test
    fun testIsNameOnlyNumberFailure() {
        var expectedNotValidName = "Not valid 1"
        var result = expectedNotValidName.isOnlyNumber()
        assertThat(result, equalTo(false))
    }
}
