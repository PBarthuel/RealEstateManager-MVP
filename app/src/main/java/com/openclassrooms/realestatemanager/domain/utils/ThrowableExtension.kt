package com.openclassrooms.realestatemanager.domain.utils

import com.openclassrooms.realestatemanager.domain.models.DomainException

fun Throwable.toDomainExceptionType(): DomainException? =
    this as? DomainException
