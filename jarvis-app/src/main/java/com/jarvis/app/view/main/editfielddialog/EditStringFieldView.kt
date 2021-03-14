package com.jarvis.app.view.main.editfielddialog

import android.content.Context
import android.text.InputType
import android.view.View
import androidx.core.widget.addTextChangedListener
import com.google.android.material.textfield.TextInputLayout
import com.jarvis.app.R
import com.jarvis.app.view.main.StringFieldItemViewModel

class EditStringFieldView(
    private val item: StringFieldItemViewModel,
    private val onFieldEmpty: (Boolean) -> Unit,
    context: Context
) : EditFieldView {

    private val container =
        View.inflate(context, R.layout.view_edit_string_field, null)

    private val textInput =
        container.findViewById<TextInputLayout>(R.id.edit_text_input_layout)
            .apply {
                hint = item.hint
                editText?.inputType = INPUT_TYPE_TEXT
                editText?.isSingleLine = false
            }

    init {
        setValue(item.value)
    }

    override val view: View = container

    override val value: Any
        get() = textInput.editText!!.text.toString()

    override var error: String = ""
        set(value) {
            field = value
            textInput.error = value
        }

    override var isPublished: Boolean = item.isPublished
        set(value) {
            field = value
            textInput.isEnabled = value
        }

    override fun setDefault() {
        setValue(item.defaultValue)
    }

    override fun isValid(): Boolean {
        val errorMessage = item.validate(value as String)
        if (errorMessage != null) {
            textInput.error = errorMessage
        }
        return errorMessage == null
    }

    private fun setValue(value: String) {
        with (textInput) {
            clearOnEditTextAttachedListeners()
            editText?.setText(value)
            editText?.setSelection(value.length)
            editText?.addTextChangedListener {
                onFieldEmpty(it?.isEmpty() == true)
            }
        }
    }

    companion object {
        private const val INPUT_TYPE_TEXT =
            InputType.TYPE_CLASS_TEXT or
                    InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS
    }
}
