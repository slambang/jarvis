package com.jarvis.app.view.main.editfielddialog

import android.content.Context
import android.view.View
import android.widget.ArrayAdapter
import androidx.appcompat.widget.AppCompatSpinner
import com.jarvis.app.R
import com.jarvis.app.view.main.StringListFieldItemViewModel

class EditStringListFieldView(
    private val item: StringListFieldItemViewModel,
    context: Context
) : EditFieldView {

    private val container =
        View.inflate(context, R.layout.view_edit_string_list_field, null)

    private val spinner =
        container.findViewById<AppCompatSpinner>(R.id.edit_string_list_spinner).apply {
            val fieldAdapter = ArrayAdapter<Any?>(
                context,
                R.layout.support_simple_spinner_dropdown_item,
                item.value
            )
            this.adapter = fieldAdapter
            setSelection(item.currentSelection, false)
        }

    override val view: View = container

    override val value: Any
        get() = spinner.selectedItemPosition

    override var error: String = ""

    override var isPublished: Boolean = item.isPublished
        set(value) {
            field = value
            spinner.isEnabled = value
        }

    override fun setDefault() {
        spinner.setSelection(item.defaultSelection, false)
    }

    override fun isValid(): Boolean = true
}
