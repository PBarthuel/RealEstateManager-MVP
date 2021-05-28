package com.openclassrooms.realestatemanager.app.di.local

import android.content.Context
import androidx.room.Room
import com.openclassrooms.realestatemanager.data.vendors.local.RealEstateDao
import com.openclassrooms.realestatemanager.data.vendors.local.RealEstateDb
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataBaseModule {
    
    @Provides
    fun provideRealEstateDao(realEstateDb: RealEstateDb): RealEstateDao {
        return realEstateDb.RealEstateDao()
    }
    
    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): RealEstateDb {
        return Room.databaseBuilder(appContext, RealEstateDb::class.java, "RealEstate")
                .build()
    }
}