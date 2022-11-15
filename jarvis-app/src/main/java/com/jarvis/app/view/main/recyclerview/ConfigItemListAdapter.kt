package com.jarvis.app.view.main.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.jarvis.app.R
import com.jarvis.app.view.main.*

class ConfigItemListAdapter(
    private val onItemClicked: (FieldItemViewModel<*>) -> Unit
) : RecyclerView.Adapter<ConfigListItemViewHolder>() {

    private val differ: AsyncListDiffer<FieldItemViewModel<*>> =
        AsyncListDiffer(this, differCallback)

    var deactivateAllFields: Boolean = false
        set(value) {
            if (field == value) return
            field = value
            notifyItemRangeChanged(0, differ.currentList.size)
        }

    override fun getItemCount() = differ.currentList.size

    fun setFields(fields: List<FieldItemViewModel<*>>) {
        differ.submitList(fields)
    }

    override fun onCreateViewHolder(
        viewGroup: ViewGroup,
        viewType: Int
    ): ConfigListItemViewHolder =
        ConfigListItemViewHolder(
            LayoutInflater.from(viewGroup.context)
                .inflate(R.layout.view_field_list_item, viewGroup, false),
            onItemClicked
        )

    override fun onBindViewHolder(viewHolder: ConfigListItemViewHolder, position: Int) =
        viewHolder.bind(differ.currentList[position], deactivateAllFields)
}

private val differCallback = object : DiffUtil.ItemCallback<FieldItemViewModel<*>>() {

    override fun areItemsTheSame(
        oldItem: FieldItemViewModel<*>,
        newItem: FieldItemViewModel<*>
    ): Boolean = oldItem.name == newItem.name

    override fun areContentsTheSame(
        oldItem: FieldItemViewModel<*>,
        newItem: FieldItemViewModel<*>
    ): Boolean = oldItem == newItem
}
