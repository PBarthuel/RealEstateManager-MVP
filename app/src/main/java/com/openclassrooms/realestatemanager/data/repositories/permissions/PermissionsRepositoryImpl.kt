package com.openclassrooms.realestatemanager.data.repositories.permissions

import android.os.Build
import com.openclassrooms.realestatemanager.data.models.entities.permissions.AccessPermission
import com.openclassrooms.realestatemanager.data.utils.throwDomainExceptionOnError
import com.openclassrooms.realestatemanager.data.vendors.dexter.PermissionServiceApi
import com.openclassrooms.realestatemanager.domain.models.DomainPermissionException
import com.openclassrooms.realestatemanager.domain.models.permissions.DomainMultiplePermission
import com.openclassrooms.realestatemanager.domain.repositories.permissions.PermissionsRepository
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class PermissionsRepositoryImpl @Inject constructor(
    private val permissionServiceApi: PermissionServiceApi
) : PermissionsRepository {

    override fun requestGeolocationAccessPermission(): Single<DomainMultiplePermission> {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            Single.error(DomainPermissionException.BackgroundLocationError("From Android R, user needs to access background permission in settings"))
        } else {
            permissionServiceApi.requestPermissions(listOf(AccessPermission.GEOLOCATION_FOREGROUND, AccessPermission.GEOLOCATION_BACKGROUND))
                .map { dataReport -> dataReport.toDomain() }
                .throwDomainExceptionOnError()
        }
    }

    override fun getGeolocationAccessPermissionStatus(): DomainMultiplePermission =
        permissionServiceApi.getPermissionsStatus(listOf(AccessPermission.GEOLOCATION_FOREGROUND, AccessPermission.GEOLOCATION_BACKGROUND))
            .toDomain()
}
