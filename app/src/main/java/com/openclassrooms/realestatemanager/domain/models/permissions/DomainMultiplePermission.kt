package com.openclassrooms.realestatemanager.domain.models.permissions

enum class DomainAccessPermission {
    GEOLOCATION_FOREGROUND,
    GEOLOCATION_BACKGROUND,
    NONE
}

data class DomainMultiplePermission(
    val grantedPermissions: List<DomainAccessPermission>,
    val deniedPermissions: List<DomainAccessPermission>
)
