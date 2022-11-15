package com.jarvis.demo.advanced.repository

import android.content.Context
import com.jarvis.demo.advanced.ConfigRepository

fun getRepository(context: Context): ConfigRepository = ReleaseConfigRepository()

/**
 * Only seen by the `release` build variant.
 */
class ReleaseConfigRepository : ConfigRepository {

    /**
     *  Directly calls the "real" remote config service to get the value.
     *  Do whatever you need to here, just return a value.
     */
    override fun getStringValue(): String = "Release value"
}
