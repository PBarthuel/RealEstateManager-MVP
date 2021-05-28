package com.openclassrooms.realestatemanager.app.utils.viewExtension

import android.os.SystemClock
import android.view.View

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