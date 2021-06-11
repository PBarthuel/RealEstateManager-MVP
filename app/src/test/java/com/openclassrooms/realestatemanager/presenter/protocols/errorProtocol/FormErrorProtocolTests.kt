package com.openclassrooms.realestatemanager.presenter.protocols.errorProtocol

import com.openclassrooms.realestatemanager.domain.models.DomainFormException
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Assert.fail
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

class TestFormErrorProtocol : FormErrorProtocol {
    private var onReceiveWrongTypeFormatErrorCalled = false
    private var onReceiveWrongPriceFormatErrorCalled = false
    private var onReceiveWrongSurfaceFormatErrorCalled = false
    private var onReceiveWrongDescriptionFormatErrorCalled = false
    private var onReceiveWrongAgentFormatErrorCalled = false
    private var onReceiveWrongTotalRoomNumberFormatErrorCalled = false
    private var onReceiveWrongBedroomNumberFormatErrorCalled = false
    private var onReceiveWrongBathroomNumberFormatErrorCalled = false
    
    override fun onReceiveWrongTypeFormatError() { onReceiveWrongTypeFormatErrorCalled = true }
    override fun onReceiveWrongPriceFormatError() { onReceiveWrongPriceFormatErrorCalled = true }
    override fun onReceiveWrongSurfaceFormatError() { onReceiveWrongSurfaceFormatErrorCalled = true }
    override fun onReceiveWrongDescriptionFormatError() { onReceiveWrongDescriptionFormatErrorCalled = true }
    override fun onReceiveWrongAgentFormatError() { onReceiveWrongAgentFormatErrorCalled = true }
    override fun onReceiveWrongTotalRoomNumberFormatError() { onReceiveWrongTotalRoomNumberFormatErrorCalled = true }
    override fun onReceiveWrongBedroomNumberFormatError() { onReceiveWrongBedroomNumberFormatErrorCalled = true }
    override fun onReceiveWrongBathroomNumberFormatError() { onReceiveWrongBathroomNumberFormatErrorCalled = true }
    
    fun resetExpectedCall() {
        onReceiveWrongTypeFormatErrorCalled = false
        onReceiveWrongPriceFormatErrorCalled = false
        onReceiveWrongSurfaceFormatErrorCalled = false
        onReceiveWrongDescriptionFormatErrorCalled = false
        onReceiveWrongAgentFormatErrorCalled = false
        onReceiveWrongTotalRoomNumberFormatErrorCalled = false
        onReceiveWrongBedroomNumberFormatErrorCalled = false
        onReceiveWrongBathroomNumberFormatErrorCalled = false
    }
    
    fun isOnReceiveWrongTypeFormatErrorCalled() = onReceiveWrongTypeFormatErrorCalled
    fun isOnReceiveWrongPriceFormatErrorCalled() = onReceiveWrongPriceFormatErrorCalled
    fun isOnReceiveWrongSurfaceFormatErrorCalled() = onReceiveWrongSurfaceFormatErrorCalled
    fun isOnReceiveWrongDescriptionFormatErrorCalled() = onReceiveWrongDescriptionFormatErrorCalled
    fun isOnReceiveWrongAgentFormatErrorCalled() = onReceiveWrongAgentFormatErrorCalled
    fun isOnReceiveWrongTotalRoomNumberFormatErrorCalled() = onReceiveWrongTotalRoomNumberFormatErrorCalled
    fun isOnReceiveWrongBedroomNumberFormatErrorCalled() = onReceiveWrongBedroomNumberFormatErrorCalled
    fun isOnReceiveWrongBathroomNumberFormatErrorCalled() = onReceiveWrongBathroomNumberFormatErrorCalled
}

@RunWith(MockitoJUnitRunner::class)
class FormErrorProtocolTests {
    private val testView = TestFormErrorProtocol()

    @Test
    fun testOnReceiveWrongEmailFormatError() {
        DomainFormException::class.nestedClasses.forEach {
            when (it) {
                DomainFormException.WrongTypeFormat::class -> {
                    testView.onReceiveError(DomainFormException.WrongTypeFormat("expected message"))
                    assertThat(testView.isOnReceiveWrongTypeFormatErrorCalled(), equalTo(true))
                }
                DomainFormException.WrongPriceFormat::class -> {
                    testView.onReceiveError(DomainFormException.WrongPriceFormat("expected message"))
                    assertThat(testView.isOnReceiveWrongPriceFormatErrorCalled(), equalTo(true))
                }
                DomainFormException.WrongSurfaceFormat::class -> {
                    testView.onReceiveError(DomainFormException.WrongSurfaceFormat("expected message"))
                    assertThat(testView.isOnReceiveWrongSurfaceFormatErrorCalled(), equalTo(true))
                }
                DomainFormException.WrongDescriptionFormat::class -> {
                    testView.onReceiveError(DomainFormException.WrongDescriptionFormat("expected message"))
                    assertThat(testView.isOnReceiveWrongDescriptionFormatErrorCalled(), equalTo(true))
                }
                DomainFormException.WrongAgentFormat::class -> {
                    testView.onReceiveError(DomainFormException.WrongAgentFormat("expected message"))
                    assertThat(testView.isOnReceiveWrongAgentFormatErrorCalled(), equalTo(true))
                }
                DomainFormException.WrongTotalRoomNumberFormat::class -> {
                    testView.onReceiveError(DomainFormException.WrongTotalRoomNumberFormat("expected message"))
                    assertThat(testView.isOnReceiveWrongTotalRoomNumberFormatErrorCalled(), equalTo(true))
                }
                DomainFormException.WrongBedRoomNumberFormat::class -> {
                    testView.onReceiveError(DomainFormException.WrongBedRoomNumberFormat("expected message"))
                    assertThat(testView.isOnReceiveWrongBedroomNumberFormatErrorCalled(), equalTo(true))
                }
                DomainFormException.WrongBathRoomNumberFormat::class -> {
                    testView.onReceiveError(DomainFormException.WrongBathRoomNumberFormat("expected message"))
                    assertThat(testView.isOnReceiveWrongBathroomNumberFormatErrorCalled(), equalTo(true))
                }
                else -> fail("Missing test for new value of enum DomainFormException.Error")
            }
            testView.resetExpectedCall()
        }
    }
}
