package com.asmaa.weather.presentation.ui

import android.view.View
import com.asmaa.weather.data.models.Time
import java.util.*

open class BaseActivity :LocationActivity() {

    protected fun getTime(locationTime: String?): Time {
        val tk = StringTokenizer(locationTime)
        return Time(tk.nextToken(), tk.nextToken())
    }

    protected fun hideStatusBar() {
        // Hide the status bar.
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
    }
}