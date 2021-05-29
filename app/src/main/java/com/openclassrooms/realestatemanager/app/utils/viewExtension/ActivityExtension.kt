package com.openclassrooms.realestatemanager.app.utils.viewExtension

import android.app.Activity
import android.view.ViewGroup

val Activity.activityRootView: ViewGroup?
    get() = findViewById(android.R.id.content)

val Activity.RESULT_BACK: Int
    get() = 10
