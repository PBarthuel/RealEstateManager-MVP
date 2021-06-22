package com.openclassrooms.realestatemanager.presenter.modules.loanSimulator

import com.openclassrooms.realestatemanager.presenter.protocols.DisposablePresenter
import javax.inject.Inject

interface LoanSimulatorView {
    fun onShowLoanDetail(notaryFees: Float, interestRate: Float, loan: Float, totalLoan: Float, monthlyPayment: Float)
    fun onEmptyAllView()
}

interface LoanSimulatorPresenter : DisposablePresenter<LoanSimulatorView> {
    fun didSelectLoanYears(loanYears: Int)
    fun didSelectLoanValue(loanValue: Int)
    fun checkForm()
}

class LoanSimulatorPresenterImpl @Inject constructor(): LoanSimulatorPresenter {
    
    override var view: LoanSimulatorView? = null
    
    var loanYears: Int = 15
    var loanValue: Int = 0
    
    override fun attach(view: LoanSimulatorView) {
        this.view = view
    }
    
    override fun didSelectLoanYears(loanYears: Int) {
        this.loanYears = loanYears
    }
    
    override fun didSelectLoanValue(loanValue: Int) {
        this.loanValue = loanValue
    }
    
    override fun checkForm() {
        if(loanValue != 0) {
            val notaryFees = (loanValue * 0.08).toFloat()
            val interestRate = when(loanYears) {
                15 -> loanValue / 10.25
                20 -> loanValue / 6.72
                25 -> loanValue / 4.20
                else -> 0f
            }.toFloat()
            val totalLoan = (loanValue + notaryFees + interestRate)
            val monthlyPayment = (totalLoan / (loanYears * 12))
            view?.onShowLoanDetail(notaryFees, interestRate, loanValue.toFloat(), totalLoan, monthlyPayment)
        } else {
            view?.onEmptyAllView()
        }
    }
}