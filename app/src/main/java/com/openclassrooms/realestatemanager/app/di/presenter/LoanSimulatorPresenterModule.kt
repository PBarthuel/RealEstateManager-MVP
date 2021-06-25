package com.openclassrooms.realestatemanager.app.di.presenter

import com.openclassrooms.realestatemanager.presenter.modules.loanSimulator.LoanSimulatorPresenter
import com.openclassrooms.realestatemanager.presenter.modules.loanSimulator.LoanSimulatorPresenterImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@Module
@InstallIn(ActivityComponent::class)
abstract class LoanSimulatorPresenterModule {
    
    @Binds
    abstract fun bindLoanSimulatorPresenter(loanSimulatorPresenter: LoanSimulatorPresenterImpl): LoanSimulatorPresenter
}