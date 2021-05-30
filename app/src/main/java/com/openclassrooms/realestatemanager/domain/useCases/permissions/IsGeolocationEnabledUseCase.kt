package com.openclassrooms.realestatemanager.domain.useCases.permissions

import com.openclassrooms.realestatemanager.domain.repositories.permissions.PermissionsRepository
import javax.inject.Inject

class IsGeolocationEnabledUseCase @Inject constructor(
    private val permissionsRepository: PermissionsRepository
) {
    fun invoke(): Boolean =
        permissionsRepository.getGeolocationAccessPermissionStatus().grantedPermissions.isNotEmpty()
}
