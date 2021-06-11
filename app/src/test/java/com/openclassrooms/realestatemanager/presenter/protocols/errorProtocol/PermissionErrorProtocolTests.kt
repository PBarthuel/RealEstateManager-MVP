package com.openclassrooms.realestatemanager.presenter.protocols.errorProtocol

import com.openclassrooms.realestatemanager.domain.models.DomainPermissionException
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

class TestPermissionErrorProtocol : PermissionErrorProtocol {
    private var onReceivePermissionReportErrorCalled = false
    private var onReceiveBackgroundLocationErrorCalled = false

    override fun onReceivePermissionReportError() {
        onReceivePermissionReportErrorCalled = true
    }

    override fun onReceiveBackgroundLocationMissingOnAndroidRError() {
        onReceiveBackgroundLocationErrorCalled = true
    }

    fun isOnReceivePermissionReportErrorCalled() = onReceivePermissionReportErrorCalled

    fun isOnReceiveBackgroundLocationErrorCalled() = onReceiveBackgroundLocationErrorCalled

    fun resetExpectedCall() {
        onReceivePermissionReportErrorCalled = false
        onReceiveBackgroundLocationErrorCalled = false
    }
}

@RunWith(MockitoJUnitRunner::class)
class PermissionErrorProtocolTests {

    private val testView = TestPermissionErrorProtocol()

    @Test
    fun testOnReceiveError() {
        DomainPermissionException::class.nestedClasses.forEach {
            when (it) {
                DomainPermissionException.ReportError::class -> {
                    testView.onReceiveError(DomainPermissionException.ReportError("DomainPermissionException report error"))
                    assertThat(testView.isOnReceivePermissionReportErrorCalled(), equalTo(true))
                }
                DomainPermissionException.BackgroundLocationError::class -> {
                    testView.onReceiveError(DomainPermissionException.BackgroundLocationError("DomainPermissionException report error"))
                    assertThat(testView.isOnReceiveBackgroundLocationErrorCalled(), equalTo(true))
                }
            }
            testView.resetExpectedCall()
        }
    }
}
