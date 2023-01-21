package com.jarvis.app.contentprovider.util

import android.database.AbstractCursor
import java.lang.UnsupportedOperationException

abstract class SimpleCursor : AbstractCursor() {
    override fun getShort(column: Int): Short = unsupported()
    override fun getInt(column: Int): Int = unsupported()
    override fun getLong(column: Int): Long = unsupported()
    override fun getFloat(column: Int): Float = unsupported()
    override fun getDouble(column: Int): Double = unsupported()

    private fun unsupported(): Nothing =
        throw UnsupportedOperationException("Unsupported.")
}
