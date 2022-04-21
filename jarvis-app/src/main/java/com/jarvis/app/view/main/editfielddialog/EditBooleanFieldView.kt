package com.jarvis.app.view.main.editfielddialog

import android.content.Context
import android.view.View
import com.google.android.material.switchmaterial.SwitchMaterial
import com.jarvis.app.R
import com.jarvis.app.view.main.BooleanFieldItemViewModel

class EditBooleanFieldView(
    context: Context,
    private val item: BooleanFieldItemViewModel
) : EditFieldView {

    private var container: View =
        View.inflate(context, R.layout.view_edit_boolean_field, null).apply {
            setOnClickListener {
                switch.toggle()
            }
        }

    private var switch: SwitchMaterial =
        container.findViewById<SwitchMaterial>(R.id.edit_boolean_switch).apply {
            isChecked = item.value
        }

    override val view: View = container

    override val value: Any
        get() = switch.isChecked

    override var isPublished: Boolean = item.isPublished
        set(value) {
            field = value
            switch.isEnabled = value
        }

    override fun setDefault() {
        switch.isChecked = item.defaultValue
    }

    override fun isValid(): Boolean = true
}
