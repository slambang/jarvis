package com.jarvis.app.view.main.editfielddialog

import android.content.Context
import android.view.View
import android.widget.TextView
import com.google.android.material.switchmaterial.SwitchMaterial
import com.jarvis.app.R
import com.jarvis.app.view.main.BooleanFieldItemViewModel

class EditBooleanFieldView(
    private val item: BooleanFieldItemViewModel,
    context: Context
) : EditFieldView {

    private var container: View =
        View.inflate(context, R.layout.view_edit_boolean_field, null)

    private var switch: SwitchMaterial =
        container.findViewById<SwitchMaterial>(R.id.edit_boolean_switch).apply {
            isChecked = item.value
        }

    private var errorMessageView: TextView =
        container.findViewById(R.id.edit_boolean_error_message)

    override val view: View = container

    override val value: Any
        get() = switch.isChecked

    override var error: String = ""

    override var isPublished: Boolean = item.isPublished
        set(value) {
            field = value
            switch.isEnabled = value
        }

    override fun setDefault() {
        switch.isChecked = item.defaultValue
    }

    override fun isValid(): Boolean {
        val errorMessage = item.validate(value as Boolean)
        if (errorMessage != null) {
            errorMessageView.text = errorMessage
        }
        return errorMessage == null
    }
}
