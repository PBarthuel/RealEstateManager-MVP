package com.openclassrooms.realestatemanager.data.vendors.local

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.openclassrooms.realestatemanager.data.models.entities.RealEstateMasterDetailResponse
import com.openclassrooms.realestatemanager.data.vendors.local.objectRequest.AddressRequest
import com.openclassrooms.realestatemanager.data.vendors.local.objectRequest.PhotoRequest
import com.openclassrooms.realestatemanager.data.vendors.local.objectRequest.RealEstateRequest
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(manifest = Config.NONE)
class RealEstateDaoTests {
    
    private lateinit var dao: RealEstateDao
    private lateinit var db: RealEstateDb
    
    private val expectedId: Long = 0
    
    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext() as Context
        db = Room.inMemoryDatabaseBuilder(context, RealEstateDb::class.java)
            .allowMainThreadQueries()
            .build()
        
        dao = db.RealEstateDao()
    }
    
    @After
    fun closeDb() {
        db.close()
    }
    
    @Test
    fun shouldReturnInsertion() {
        val realEstateRequest = RealEstateRequest(
                expectedId,
                "appartement",
                "100000",
                "200",
                "beautiful",
                true,
                false,
                false,
                true,
                true,
                "21/06/2021",
                "20/08/2085",
                "gerard",
                "8",
                "3",
                "2"
        )
        
        val addressRequest = AddressRequest(
                expectedId,
                expectedId,
                "france",
                "paris",
                "rue de paradis",
                "75016",
                4.0,
                2.0
        )
        
        val photoRequest =
            listOf(
                PhotoRequest(
                        expectedId,
                        expectedId,
                        "d5zpdqdqd5d6qd",
                        "Kitchen"
                )
            )
        
        dao.createRealEstate(realEstateRequest)
        dao.createAddress(addressRequest)
        dao.createPhoto(photoRequest)

        dao.getRealEstateMasterDetail(expectedId)
            .test()
            .assertValue {
                assertThat(it, equalTo(RealEstateMasterDetailResponse(realEstateRequest, addressRequest, photoRequest)))
                true
            }
    }
}