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
) : RecyclerView.Adapter<GroupItemViewHolder>() {

    private val differ: AsyncListDiffer<ConfigGroupItemViewModel> =
        AsyncListDiffer(this, differCallback)

    var deactivateAllFields: Boolean = false
        set(value) {
            if (field == value) return
            field = value
            notifyItemRangeChanged(0, differ.currentList.size)
        }

    override fun getItemCount() = differ.currentList.size

    fun setGroups(groups: List<ConfigGroupItemViewModel>): Unit =
        differ.submitList(groups)

    override fun onCreateViewHolder(
        viewGroup: ViewGroup,
        viewType: Int
    ): GroupItemViewHolder =
        GroupItemViewHolder(
            LayoutInflater.from(viewGroup.context)
                .inflate(R.layout.view_config_group, viewGroup, false),
            onItemClicked
        )

    override fun onBindViewHolder(viewHolder: GroupItemViewHolder, position: Int): Unit =
        viewHolder.bind(differ.currentList[position], !deactivateAllFields)
}

private val differCallback = object : DiffUtil.ItemCallback<ConfigGroupItemViewModel>() {

    override fun areItemsTheSame(
        oldItem: ConfigGroupItemViewModel,
        newItem: ConfigGroupItemViewModel
    ): Boolean = oldItem.name == newItem.name

    override fun areContentsTheSame(
        oldItem: ConfigGroupItemViewModel,
        newItem: ConfigGroupItemViewModel
    ): Boolean = oldItem == newItem
}
