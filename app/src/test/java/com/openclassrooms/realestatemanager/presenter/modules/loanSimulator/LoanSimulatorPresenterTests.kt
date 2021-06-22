package com.openclassrooms.realestatemanager.presenter.modules.loanSimulator

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.verifyNoMoreInteractions
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class LoanSimulatorPresenterTests {
    
    private val mockView: LoanSimulatorView = mock()
    private val presenter = LoanSimulatorPresenterImpl()
    
    @Before
    fun setup() {
        presenter.attach(mockView)
    }
    
    @Test
    fun testDidSelectLoanYears() {
        val expectedLoanYears = 25
        
        presenter.didSelectLoanYears(25)
    
        assertThat(presenter.loanYears, equalTo(expectedLoanYears))
        verifyNoMoreInteractions(mockView)
    }
    
    @Test
    fun testDidSelectLoanValue() {
        val expectedLoanValue = 250000
        
        presenter.didSelectLoanValue(250000)
        
        assertThat(presenter.loanValue, equalTo(expectedLoanValue))
        verifyNoMoreInteractions(mockView)
    }
    
    @Test
    fun testCheckFormWithZeroAsValue() {
        presenter.loanValue = 0
        
        presenter.checkForm()
        
        verify(mockView).onEmptyAllView()
        verifyNoMoreInteractions(mockView)
    }
    
    @Test
    fun testCheckFormWithLoanYears15() {
        presenter.loanValue = 250000
        presenter.loanYears = 15
        val expectedNotaryFees = (presenter.loanValue * 0.08).toFloat()
        val expectedInterestRate = (presenter.loanValue / 10.25).toFloat()
        val expectedTotalLoan = (presenter.loanValue + expectedNotaryFees + expectedInterestRate)
        val expectedMonthlyPayment = (expectedTotalLoan / (presenter.loanYears * 12))
        
        presenter.checkForm()
        
        verify(mockView).onShowLoanDetail(expectedNotaryFees, expectedInterestRate, presenter.loanValue.toFloat(), expectedTotalLoan, expectedMonthlyPayment)
        verifyNoMoreInteractions(mockView)
    }
    
    @Test
    fun testCheckFormWithLoanYears20() {
        presenter.loanValue = 250000
        presenter.loanYears = 20
        val expectedNotaryFees = (presenter.loanValue * 0.08).toFloat()
        val expectedInterestRate = (presenter.loanValue / 6.72).toFloat()
        val expectedTotalLoan = (presenter.loanValue + expectedNotaryFees + expectedInterestRate)
        val expectedMonthlyPayment = (expectedTotalLoan / (presenter.loanYears * 12))
        
        presenter.checkForm()
        
        verify(mockView).onShowLoanDetail(expectedNotaryFees, expectedInterestRate, presenter.loanValue.toFloat(), expectedTotalLoan, expectedMonthlyPayment)
        verifyNoMoreInteractions(mockView)
    }
    
    @Test
    fun testCheckFormWithLoanYears25() {
        presenter.loanValue = 250000
        presenter.loanYears = 25
        val expectedNotaryFees = (presenter.loanValue * 0.08).toFloat()
        val expectedInterestRate = (presenter.loanValue / 4.20).toFloat()
        val expectedTotalLoan = (presenter.loanValue + expectedNotaryFees + expectedInterestRate)
        val expectedMonthlyPayment = (expectedTotalLoan / (presenter.loanYears * 12))
        
        presenter.checkForm()
        
        verify(mockView).onShowLoanDetail(expectedNotaryFees, expectedInterestRate, presenter.loanValue.toFloat(), expectedTotalLoan, expectedMonthlyPayment)
        verifyNoMoreInteractions(mockView)
    }
    
    @Test
    fun testCheckFormWithLoanYearsBug() {
        presenter.loanValue = 250000
        presenter.loanYears = 50
        val expectedNotaryFees = (presenter.loanValue * 0.08).toFloat()
        val expectedInterestRate = 0f
        val expectedTotalLoan = (presenter.loanValue + expectedNotaryFees + expectedInterestRate)
        val expectedMonthlyPayment = (expectedTotalLoan / (presenter.loanYears * 12))
        
        presenter.checkForm()
        
        verify(mockView).onShowLoanDetail(expectedNotaryFees, expectedInterestRate, presenter.loanValue.toFloat(), expectedTotalLoan, expectedMonthlyPayment)
        verifyNoMoreInteractions(mockView)
    }
}