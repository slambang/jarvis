package com.jarvis.app.view.main.recyclerview

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.jarvis.app.R
import com.jarvis.app.view.main.FieldItemViewModel
import com.jarvis.app.view.main.StringListFieldItemViewModel

class ConfigListItemViewHolder(
    private val view: View,
    private val onItemClicked: (FieldItemViewModel<*>) -> Unit
) : RecyclerView.ViewHolder(view) {

    private val fieldName = view.findViewById<TextView>(R.id.field_list_item_name)
    private val fieldValue = view.findViewById<TextView>(R.id.field_list_item_value)

    fun bind(model: FieldItemViewModel<*>, deactivateAllFields: Boolean) {

        fieldName.text = model.name
        fieldValue.text = when (model) {
            is StringListFieldItemViewModel -> model.value[model.currentSelection]
            else -> model.value.toString()
        }

        when {
            deactivateAllFields || !model.isPublished -> R.color.gray
            isOverridden(model) -> R.color.green
            else -> R.color.white
        }.also { view.setBackgroundResource(it) }

        when (deactivateAllFields) {
            true -> itemView.setOnClickListener(null)
            else -> itemView.setOnClickListener { onItemClicked(model) }
        }
    }

    private fun isOverridden(model: FieldItemViewModel<*>): Boolean =
        when (model) {
            is StringListFieldItemViewModel -> model.defaultSelection != model.currentSelection
            else -> model.value != model.defaultValue
        }
}
