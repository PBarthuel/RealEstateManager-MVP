package com.openclassrooms.realestatemanager.app.utils.viewExtension

import android.content.Context
import android.text.format.DateUtils
import java.util.*

fun Date.getFormatWithoutTime(context: Context?): String =
    DateUtils.formatDateTime(
        context,
        this.time,
        DateUtils.FORMAT_SHOW_DATE
            or DateUtils.FORMAT_SHOW_WEEKDAY
            or DateUtils.FORMAT_ABBREV_WEEKDAY
            or DateUtils.FORMAT_ABBREV_MONTH
    ).capitalize(Locale.getDefault())