package com.jarvis.app.contentprovider

import com.jarvis.app.contentprovider.util.SimpleCursor

class JarvisCursor(
    private val fieldValue: String
) : SimpleCursor() {

    override fun getCount(): Int = 2

    override fun getColumnIndex(columnName: String): Int = if (columnName == "value") 0 else 1

    override fun getColumnNames(): Array<String> = arrayOf("value")

    override fun getString(column: Int): String = fieldValue

    override fun isNull(column: Int): Boolean = false
}
