package com.openclassrooms.realestatemanager.app.di.repositories

import com.openclassrooms.realestatemanager.data.repositories.permissions.PermissionsRepositoryImpl
import com.openclassrooms.realestatemanager.domain.repositories.permissions.PermissionsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@Module
@InstallIn(ActivityComponent::class)
abstract class PermissionsRepositoryModule {

    @Binds
    abstract fun bindPermissionsRepository(permissionsRepository: PermissionsRepositoryImpl): PermissionsRepository
}