package com.example.covid100.ui.help

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.covid100.data.model.HelpBody
import com.example.covid100.databinding.HolderHelpBinding
import com.example.covid100.utils.UtilFunctions.mapResourceCodeToResourceString

class HelpAdapter(
    private val itemClick : (id: String) -> Unit
) : ListAdapter<HelpBody, HelpAdapter.HelpViewHolder>(diffUtil) {

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<HelpBody>() {
            override fun areItemsTheSame(oldItem: HelpBody, newItem: HelpBody): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: HelpBody, newItem: HelpBody): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HelpViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = HolderHelpBinding.inflate(inflater, parent, false)
        return HelpViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HelpViewHolder, position: Int) {
        holder.populateViews(getItem(position), itemClick)
    }

    class HelpViewHolder(private val binding: HolderHelpBinding) : RecyclerView.ViewHolder(binding.root) {
        fun populateViews(item: HelpBody, itemClick: (id: String) -> Unit) {
            binding.apply {
                tvName.text = item.name
                tvAge.text = item.age.toString()
                tvContact.text = item.contact
                tvDate.text = item.dateToDisplay
                tvResourceType.text = mapResourceCodeToResourceString(item.resourceType ?: -1)
            }

            binding.root.setOnClickListener {
                item.id?.let {
                    itemClick(it)
                }
            }
        }
    }
}