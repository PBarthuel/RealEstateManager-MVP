package com.openclassrooms.realestatemanager.data.vendors.local.objectRequest

import android.content.ContentValues
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.openclassrooms.realestatemanager.data.models.domainMappingProtocols.DomainModelConvertible
import com.openclassrooms.realestatemanager.domain.models.DomainPhoto
import kotlinx.serialization.Serializable

@Entity
@Serializable
data class PhotoRequest(
    @PrimaryKey (autoGenerate = true) var photoId: Long,
    val realEstateOwnerId: Long,
    @ColumnInfo(name = "photo_reference")
    val photoReference: String,
    @ColumnInfo(name = "room_type")
    val roomType: String?
) : DomainModelConvertible<DomainPhoto> {
    override fun toDomain(): DomainPhoto =
        DomainPhoto(photoId, photoReference, roomType)
    
    fun fromContentValues(values: ContentValues): PhotoRequest {
        return if(values.containsKey("photoId") && values.containsKey("photo_reference") && values.containsKey("room_type")) {
            PhotoRequest(
                values.getAsLong("photoId"),
                0,
                values.getAsString("photo_reference"),
                values.getAsString("room_type")
            )
        } else {
            PhotoRequest(0 ,0 , "", "")
        }
    }
}