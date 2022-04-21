package com.jarvis.app.view.main.editfielddialog

import android.view.View

interface EditFieldView {
    val view: View
    val value: Any
    var isPublished: Boolean

    fun setDefault()
    fun isValid(): Boolean
}
