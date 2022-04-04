package com.jarvis.app.view.util

import android.content.Context
import androidx.annotation.StringRes
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class ResourceProvider @Inject constructor(
    @ApplicationContext private val context: Context
) {
    fun getString(@StringRes resourceId: Int, vararg args: Any): String =
        context.getString(resourceId, *args)
}
