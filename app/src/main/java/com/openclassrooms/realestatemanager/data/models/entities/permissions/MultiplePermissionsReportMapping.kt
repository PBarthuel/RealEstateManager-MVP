package com.openclassrooms.realestatemanager.data.models.entities.permissions

import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse

fun MultiplePermissionsReport.toData(): DataMultiplePermission =
    DataMultiplePermission(grantedPermissionResponses.toData(), deniedPermissionResponses.toData())

@JvmName("toDomainPermissionGrantedResponse")
fun List<PermissionGrantedResponse>.toData(): List<AccessPermission> =
    map { permissionResponse ->
        AccessPermission.fromAndroidPermission(permissionResponse.permissionName)
    }

@JvmName("toDomainPermissionDeniedResponse")
fun List<PermissionDeniedResponse>.toData(): List<AccessPermission> =
    map { permissionResponse ->
        AccessPermission.fromAndroidPermission(permissionResponse.permissionName)
    }
