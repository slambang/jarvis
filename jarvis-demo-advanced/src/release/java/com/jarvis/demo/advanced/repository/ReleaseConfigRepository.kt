package com.jarvis.demo.advanced.repository

import android.content.Context
import com.jarvis.demo.advanced.ConfigRepository

/**
 * This file is only seen by the release build variant
 */

@Suppress("UNUSED_PARAMETER")
fun injectRepository(context: Context): ConfigRepository =
    ReleaseConfigRepository()

class ReleaseConfigRepository : ConfigRepository {

    /**
     *  Returns the release string value
     */
    override fun getStringValue(): String = "Release value"
}
