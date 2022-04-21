package com.jarvis.app.view.main.editfielddialog

import android.content.Context
import android.text.InputType
import android.view.View
import androidx.core.widget.addTextChangedListener
import com.google.android.material.textfield.TextInputLayout
import com.jarvis.app.R
import com.jarvis.app.view.main.LongFieldItemViewModel
import com.jarvis.app.view.util.toSafeLong

class EditLongFieldView(
    context: Context,
    private val item: LongFieldItemViewModel,
    private val onFieldEmpty: (Boolean) -> Unit
) : EditFieldView {

    private var container: View =
        View.inflate(context, R.layout.view_edit_string_field, null)

    private val textInput =
        container.findViewById<TextInputLayout>(R.id.edit_text_input_layout)
            .apply {
                hint = item.hint
                editText?.inputType = INPUT_TYPE_INTEGER
                editText?.isSingleLine = false
            }

    init {
        setValue(item.value)
    }

    override val view: View = container

    override val value: Any
        get() = textInput.editText!!.text.toString().toSafeLong(item.defaultValue)

    override var isPublished: Boolean = item.isPublished
        set(value) {
            field = value
            textInput.isEnabled = value
        }

    override fun setDefault() {
        setValue(item.defaultValue)
    }

    override fun isValid(): Boolean {
        val errorMessage = item.validate(value as Long)
        if (errorMessage != null) {
            textInput.error = errorMessage
        }
        return errorMessage == null
    }

    private fun setValue(value: Long) {
        with (textInput) {
            clearOnEditTextAttachedListeners()
            editText?.setText(value.toString())
            editText?.setSelection(value.toString().length)
            editText?.addTextChangedListener {
                onFieldEmpty(it?.isEmpty() == true)
            }
        }
    }

    companion object {
        private const val INPUT_TYPE_INTEGER =
            InputType.TYPE_CLASS_NUMBER or
                    InputType.TYPE_NUMBER_FLAG_SIGNED
    }
}
