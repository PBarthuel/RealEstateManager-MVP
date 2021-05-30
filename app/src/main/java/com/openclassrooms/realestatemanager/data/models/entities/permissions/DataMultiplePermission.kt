package com.openclassrooms.realestatemanager.data.models.entities.permissions

import com.openclassrooms.realestatemanager.data.models.domainMappingProtocols.DomainModelConvertible
import com.openclassrooms.realestatemanager.domain.models.permissions.DomainMultiplePermission

data class DataMultiplePermission(
    private val grantedPermission: List<AccessPermission>,
    private val deniedPermission: List<AccessPermission>
) : DomainModelConvertible<DomainMultiplePermission> {
    override fun toDomain(): DomainMultiplePermission =
        DomainMultiplePermission(grantedPermission.toDomain(), deniedPermission.toDomain())
}
