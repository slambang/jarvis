package com.jarvis.app.view.main.recyclerview

import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.jarvis.app.R
import com.jarvis.app.view.main.ConfigGroupItemViewModel
import com.jarvis.app.view.main.FieldItemViewModel
import com.jarvis.app.view.main.StringListFieldItemViewModel

class GroupItemViewHolder(
    private val groupView: View,
    private val onItemClicked: (FieldItemViewModel<*>) -> Unit
) : RecyclerView.ViewHolder(groupView) {

    private val groupName = groupView.findViewById<TextView>(R.id.group_item_name)
    private val collapseToggle = groupView.findViewById<View>(R.id.group_item_collapse_toggle)
    private val fieldContainer = groupView.findViewById<LinearLayout>(R.id.group_item_field_container)

    fun bind(model: ConfigGroupItemViewModel, isActive: Boolean) {
        groupName.text = model.name

        collapseToggle.isVisible = model.isCollapsable

        if (model.isCollapsable) {
            val getRotation = { if (model.isCollapsed) 180f else 0f }

            collapseToggle.rotation = getRotation()

            collapseToggle.setOnClickListener {
                model.isCollapsed = !model.isCollapsed
                collapseToggle.animate().setDuration(200).rotation(getRotation())
                // TODO animate expand/collapse here
            }
        }

        model.fields.forEach {
            val fieldView = View.inflate(groupView.context, R.layout.view_field_list_item, null)
            bindField(fieldView, it, isActive)
            with (fieldContainer) {
                removeAllViews()
                addView(fieldView)
                requestLayout()
            }
        }
    }

    private fun bindField(fieldView: View, model: FieldItemViewModel<*>, isActive: Boolean) {

        val fieldName = fieldView.findViewById<TextView>(R.id.field_list_item_name)
        val fieldValue = fieldView.findViewById<TextView>(R.id.field_list_item_value)

        fieldName.text = model.name
        fieldValue.text = when (model) {
            is StringListFieldItemViewModel -> model.value[model.currentSelection]
            else -> model.value.toString()
        }

        // TODO selectableBackground needs to be set here!
        when {
            !isActive || !model.isPublished -> R.color.gray
            model.valueIsOverridden() -> R.color.green
            else -> R.color.white
        }.also { fieldView.setBackgroundResource(it) }

        when (isActive) {
            true -> fieldView.setOnClickListener { onItemClicked(model) }
            else -> fieldView.setOnClickListener(null)
        }
    }

    private fun FieldItemViewModel<*>.valueIsOverridden(): Boolean =
        when (this) {
            is StringListFieldItemViewModel -> this.defaultSelection != this.currentSelection
            else -> this.value != this.defaultValue
        }
}
