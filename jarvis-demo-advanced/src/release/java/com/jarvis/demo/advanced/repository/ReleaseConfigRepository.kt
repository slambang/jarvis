package com.jarvis.demo.advanced.repository

import android.content.Context
import com.jarvis.demo.advanced.ConfigRepository

fun injectRepository(context: Context): ConfigRepository = ReleaseConfigRepository()

/**
 * Only seen by the `release` build variant
 */
class ReleaseConfigRepository : ConfigRepository {

    /**
     *  Returns the release string value
     */
    override fun getStringValue(): String = "Release value"
}
