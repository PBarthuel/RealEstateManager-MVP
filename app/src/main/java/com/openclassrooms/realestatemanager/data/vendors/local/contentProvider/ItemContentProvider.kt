package com.openclassrooms.realestatemanager.data.vendors.local.contentProvider

import android.content.ContentProvider
import android.content.ContentUris
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.net.Uri
import com.openclassrooms.realestatemanager.data.vendors.local.RealEstateDb
import com.openclassrooms.realestatemanager.data.vendors.local.objectRequest.PhotoRequest
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class ItemContentProvider @Inject constructor(
    private val realEstateDb: RealEstateDb
): ContentProvider() {
    
    val AUTHORITY = "com.openclassrooms.realestatemanager"
    val TABLE_NAME: String = PhotoRequest::class.java.simpleName
    val URI_ITEM = Uri.parse("content://$AUTHORITY/$TABLE_NAME")
    
    override fun onCreate(): Boolean { return true }
    
    override fun query(uri: Uri, projection: Array<out String>?, selection: String?, selectionArgs: Array<out String>?, sortOrder: String?): Cursor? {
        if (context != null) {
            val id = ContentUris.parseId(uri)
            val cursor: Cursor = realEstateDb
                    .RealEstateDao()
                    .getRealEstatePhotoWithCursor(id)
            cursor.setNotificationUri(context!!.contentResolver, uri)
            return cursor
        }
    
        throw IllegalArgumentException("Failed to query row for uri $uri")
    }
    
    override fun getType(uri: Uri): String {
        return "vnd.android.cursor.item/" + AUTHORITY + "." + TABLE_NAME;
    }
    
    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        TODO("Not yet implemented")
    }
    
    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?): Int {
        TODO("Not yet implemented")
    }
    
    override fun update(uri: Uri, values: ContentValues?, selection: String?, selectionArgs: Array<out String>?): Int {
        TODO("Not yet implemented")
    }
}