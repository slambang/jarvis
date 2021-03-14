package com.jarvis.app.view.main.editfielddialog

import android.content.Context
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import android.widget.FrameLayout
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.jarvis.app.R
import com.jarvis.app.view.main.*

/**
 * TODO
 *  Range slider
 *  signed/unsigned
 *  Help dialog
 */
class EditFieldDialogFactory {

    fun getEditFieldDialog(
        item: FieldItemViewModel<*>,
        context: Context,
        onDismiss: () -> Unit,
        onFieldUpdated: (Any, Boolean) -> Unit
    ): AlertDialog {

        var dialog: AlertDialog? = null
        val onFieldEmpty = { isEmpty: Boolean ->
            setButtonTextColour(dialog!!.getButton(AlertDialog.BUTTON_POSITIVE), !isEmpty)
        }

        val editFieldView = getEditFieldView(item, onFieldEmpty, context)
        val dialogView = getDialogContainerView(item, editFieldView, context)

        dialog = MaterialAlertDialogBuilder(context)
            .setView(dialogView)
            .setPositiveButton(R.string.edit_dialog_save, null)
            .setOnDismissListener { onDismiss() }
            .create()

        with(dialog) {
            // TODO This causes the weird click/dismiss issue at the top of the dialog!
            window?.setBackgroundDrawableResource(R.drawable.edit_dialog_dialog_background)

            setOnShowListener {
                val saveButton = getButton(AlertDialog.BUTTON_POSITIVE)

                saveButton.setOnClickListener {
                    if (editFieldView.isValid()) {
                        onFieldUpdated(editFieldView.value, editFieldView.isPublished)
                        onDismiss()
                    }
                }

                setButtonTextColour(saveButton, true)
            }
        }

        return dialog
    }

    private fun setButtonTextColour(button: Button, isEnabled: Boolean) {
        val textColour = when (isEnabled) {
            true -> R.color.colorAccent
            false -> R.color.grey
        }

        button.isEnabled = isEnabled
        button.setTextColor(ContextCompat.getColor(button.context, textColour))
    }

    private fun getDialogContainerView(
        item: FieldItemViewModel<*>,
        editFieldView: EditFieldView,
        context: Context
    ): View = View.inflate(context, R.layout.view_edit_dialog, null).apply {

        findViewById<TextView>(R.id.edit_dialog_header_field_name).apply {
            text = item.name
        }

        findViewById<TextView>(R.id.edit_dialog_header_field_description)
            .apply {
                text = item.description
                isVisible = !item.description.isNullOrBlank()
            }

        findViewById<FrameLayout>(R.id.edit_dialog_content_container).apply {
            addView(editFieldView.view)
        }

        findViewById<TextView>(R.id.edit_dialog_header_title).apply {
            setOnClickListener {
                editFieldView.setDefault()
            }
        }

        findViewById<CheckBox>(R.id.edit_dialog_header_is_published).apply {
            setOnCheckedChangeListener { _, isChecked ->
                editFieldView.isPublished = isChecked
            }

            isChecked = item.isPublished
        }
    }

    private fun getEditFieldView(
        item: FieldItemViewModel<*>,
        onFieldEmpty: (Boolean) -> Unit,
        context: Context
    ): EditFieldView = when (item) {
        is StringFieldItemViewModel -> EditStringFieldView(item, onFieldEmpty, context)
        is LongFieldItemViewModel -> EditLongFieldView(item, onFieldEmpty, context)
        is DoubleFieldItemViewModel -> EditDoubleFieldView(item, onFieldEmpty, context)
        is BooleanFieldItemViewModel -> EditBooleanFieldView(item, context)
        is StringListFieldItemViewModel -> EditStringListFieldView(item, context)
    }
}
