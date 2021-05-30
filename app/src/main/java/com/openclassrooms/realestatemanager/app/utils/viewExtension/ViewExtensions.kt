package com.openclassrooms.realestatemanager.app.utils.viewExtension

import android.os.Build
import android.os.SystemClock
import android.view.View
import androidx.annotation.RequiresApi
import com.google.android.material.snackbar.Snackbar
import com.openclassrooms.realestatemanager.R

// https://stackoverflow.com/a/56462570/304876
fun View.setClickWithDelay(debounceTime: Long = 600L, action: (view: View) -> Unit) {
    this.setOnClickListener(object : View.OnClickListener {
        private var lastClickTime: Long = 0
        
        override fun onClick(v: View) {
            if (SystemClock.elapsedRealtime() - lastClickTime < debounceTime) return
            else action(v)
            
            lastClickTime = SystemClock.elapsedRealtime()
        }
    })
}

fun View.showShortSnackbar(text: String, actionLabel: String? = null, action: ((View) -> Unit)? = null) {
    val snackbar = Snackbar.make(this, text, Snackbar.LENGTH_LONG)
    if (actionLabel != null) {
        snackbar.setAction(actionLabel, action)
    }
    snackbar.show()
}