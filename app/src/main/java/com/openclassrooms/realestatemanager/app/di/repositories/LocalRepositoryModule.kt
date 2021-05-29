package com.openclassrooms.realestatemanager.app.di.repositories

import com.openclassrooms.realestatemanager.data.repositories.local.RealEstateRepositoryImpl
import com.openclassrooms.realestatemanager.domain.repositories.local.RealEstateRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@Module
@InstallIn(ActivityComponent::class)
abstract class LocalRepositoryModule {
    
    @Binds
    abstract fun bindRealEstateRepository(realEstateRepository: RealEstateRepositoryImpl): RealEstateRepository
}