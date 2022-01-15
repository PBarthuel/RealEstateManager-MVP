package com.openclassrooms.realestatemanager.data.vendors.contentProvider

import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import androidx.annotation.Nullable
import com.openclassrooms.realestatemanager.data.vendors.local.RealEstateDao
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.EntryPointAccessors
import dagger.hilt.components.SingletonComponent

class CustomContentProvider() : ContentProvider() {

    @EntryPoint
    @InstallIn(SingletonComponent::class)
    interface CustomContentProviderEntryPoint {
        fun realEstateDao(): RealEstateDao
    }

    companion object {

        val AUTHORITY = "com.openclassrooms.realestatemanager.data.vendors.contentProvider.CustomContentProvider"
        private val REAL_ESTATE_TABLE = "realEstate"
        val CONTENT_URI = Uri.parse("content://$AUTHORITY/$REAL_ESTATE_TABLE")

        private const val REAL_ESTATE= 1

        private val MATCHER = UriMatcher(UriMatcher.NO_MATCH)

        init {
            MATCHER.addURI(
                AUTHORITY,
                REAL_ESTATE_TABLE,
                REAL_ESTATE
            )
        }
    }

    override fun onCreate(): Boolean {
        return true
    }

    override fun update(uri: Uri, values: ContentValues?, selection: String?, selectionArgs: Array<String?>?): Int { throw UnsupportedOperationException("Not yet implemented") }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String?>?): Int { throw UnsupportedOperationException("Not yet implemented") }

    override fun insert(uri: Uri, values: ContentValues?): Uri? { throw UnsupportedOperationException("Not yet implemented") }

    @Nullable
    override fun query(uri: Uri, @Nullable projection: Array<String>?, @Nullable selection: String?, @Nullable selectionArgs: Array<String>?, @Nullable sortOrder: String?): Cursor? {
        val appContext = context?.applicationContext ?: throw IllegalStateException()
        val hiltEntryPoint =
            EntryPointAccessors.fromApplication(appContext, CustomContentProviderEntryPoint::class.java)
        val realEstateDao = hiltEntryPoint.realEstateDao()

        val code = MATCHER.match(uri)
        return if (code == REAL_ESTATE) {
            val context = context ?: return null
            val cursor: Cursor = realEstateDao.getAllRealEstateMasterDetailWithCursor()
            cursor.setNotificationUri(context.contentResolver, uri)
            cursor
        } else {
            throw IllegalArgumentException("Unknown URI: $uri")
        }
    }

    @Nullable
    override fun getType(uri: Uri): String? { throw UnsupportedOperationException("Not yet implemented") }
}