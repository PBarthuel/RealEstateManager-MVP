package com.openclassrooms.realestatemanager.data.models.entities.permissions

import android.Manifest
import android.os.Build
import com.openclassrooms.realestatemanager.data.models.domainMappingProtocols.DomainModelConvertible
import com.openclassrooms.realestatemanager.domain.models.permissions.DomainAccessPermission

enum class AccessPermission : DomainModelConvertible<DomainAccessPermission>, AndroidPermissionConvertible {
    GEOLOCATION_FOREGROUND {
        override fun toDomain(): DomainAccessPermission = DomainAccessPermission.GEOLOCATION_FOREGROUND
        override fun toAndroidPermission(): String = Manifest.permission.ACCESS_COARSE_LOCATION
    },
    GEOLOCATION_BACKGROUND {
        override fun toDomain(): DomainAccessPermission = DomainAccessPermission.GEOLOCATION_BACKGROUND
        override fun toAndroidPermission(): String =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                Manifest.permission.ACCESS_BACKGROUND_LOCATION
            } else {
                Manifest.permission.ACCESS_COARSE_LOCATION
            }
    },
    NONE {
        override fun toDomain(): DomainAccessPermission = DomainAccessPermission.NONE
        override fun toAndroidPermission(): String = ""
    };

    companion object {
        fun fromAndroidPermission(permissionName: String): AccessPermission = when (permissionName) {
            Manifest.permission.ACCESS_COARSE_LOCATION -> GEOLOCATION_FOREGROUND
            Manifest.permission.ACCESS_BACKGROUND_LOCATION -> GEOLOCATION_BACKGROUND
            else -> NONE
        }
    }
}

fun List<AccessPermission>.toDomain(): List<DomainAccessPermission> =
    map { accessPhotoPermission -> accessPhotoPermission.toDomain() }
