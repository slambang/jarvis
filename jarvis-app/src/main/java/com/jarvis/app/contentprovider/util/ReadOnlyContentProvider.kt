package com.jarvis.app.contentprovider.util

import android.content.ContentProvider
import android.content.ContentValues
import android.net.Uri
import java.lang.UnsupportedOperationException

abstract class ReadOnlyContentProvider : ContentProvider() {

    override fun getType(uri: Uri): String? =
        unsupported()

    override fun insert(uri: Uri, values: ContentValues?): Uri? =
        unsupported()

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?): Int =
        unsupported()

    override fun update(
        uri: Uri,
        values: ContentValues?,
        selection: String?,
        selectionArgs: Array<out String>?
    ): Int = unsupported()

    private fun unsupported(): Nothing =
        throw UnsupportedOperationException("Unsupported.")
}
