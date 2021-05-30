package com.openclassrooms.realestatemanager.domain.repositories.permissions

import com.openclassrooms.realestatemanager.domain.models.permissions.DomainMultiplePermission
import io.reactivex.rxjava3.core.Single

interface PermissionsRepository {
    fun requestGeolocationAccessPermission(): Single<DomainMultiplePermission>
    fun getGeolocationAccessPermissionStatus(): DomainMultiplePermission
}
