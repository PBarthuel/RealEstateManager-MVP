package com.openclassrooms.realestatemanager.app.utils

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.provider.Settings

fun Activity.showAppSettings() {
    Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.parse("package:$packageName")).apply {
        addCategory(Intent.CATEGORY_DEFAULT)
        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(this)
    }
}

fun Activity.showAppLocationSettings() {
    Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS, Uri.parse("package:$packageName")).apply {
        addCategory(Intent.CATEGORY_DEFAULT)
        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(this)
    }
}
