package com.openclassrooms.realestatemanager.domain.utils

fun String.isNameValid(): Boolean =
    matches("^[\\p{L} .'-]+\$".toRegex())

fun String.isOnlyNumber(): Boolean =
    matches("\\d+".toRegex())