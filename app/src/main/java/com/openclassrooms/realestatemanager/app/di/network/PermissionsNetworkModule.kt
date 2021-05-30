package com.openclassrooms.realestatemanager.app.di.network

import android.content.Context
import com.karumi.dexter.Dexter
import com.karumi.dexter.DexterBuilder
import com.openclassrooms.realestatemanager.data.vendors.dexter.PermissionServiceApi
import com.openclassrooms.realestatemanager.data.vendors.dexter.PermissionServiceApiImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.qualifiers.ApplicationContext

@Module
@InstallIn(ActivityComponent::class)
abstract class PermissionsNetworkModule {

    @Binds
    abstract fun providePermissionServiceApi(permissionServiceApiImpl: PermissionServiceApiImpl): PermissionServiceApi
}

@Module
@InstallIn(ActivityComponent::class)
class DexterModule {

    @Provides
    fun provideDexterBuilder(@ApplicationContext context: Context): DexterBuilder.Permission = Dexter.withContext(context)
}
