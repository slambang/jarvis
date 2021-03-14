package com.jarvis.app.view.util

import android.content.Context
import androidx.annotation.StringRes

class ResourceProvider(
    private val context: Context
) {
    fun getString(@StringRes resourceId: Int, vararg args: Any): String =
        context.getString(resourceId, *args)
}
