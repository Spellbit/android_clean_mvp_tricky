package dev.stanislav.testtask

import android.app.Activity

class ActivityHolder {
    var activity: Activity? = null
        private set

    fun registerActivity(activity: Activity) {
        this.activity = activity
    }

    fun unregisterActivity() {
        activity = null
    }
}