package com.openclassrooms.realestatemanager.domain.useCases.permissions

import com.openclassrooms.realestatemanager.domain.models.permissions.DomainMultiplePermission
import com.openclassrooms.realestatemanager.domain.repositories.permissions.PermissionsRepository
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class RequestGeolocationPermissionUseCase @Inject constructor(
    private val permissionsRepository: PermissionsRepository
) {
    fun invoke(): Single<DomainMultiplePermission> = permissionsRepository.requestGeolocationAccessPermission()
}
