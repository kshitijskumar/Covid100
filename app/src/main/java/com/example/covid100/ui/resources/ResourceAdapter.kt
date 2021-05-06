package com.example.covid100.ui.resources

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.covid100.data.model.ResourceBody
import com.example.covid100.databinding.HolderResourceBinding
import com.example.covid100.utils.UtilFunctions.mapResourceCodeToResourceString

class ResourceAdapter(
    private val itemClick : (resource: ResourceBody) -> Unit
) : ListAdapter<ResourceBody, ResourceAdapter.ResourceViewHolder>(diffUtil) {

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<ResourceBody>() {
            override fun areItemsTheSame(oldItem: ResourceBody, newItem: ResourceBody): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: ResourceBody, newItem: ResourceBody): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResourceViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = HolderResourceBinding.inflate(inflater)
        return ResourceViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ResourceViewHolder, position: Int) {
        val item = getItem(position)
        holder.populateView(item, itemClick)
    }

    class ResourceViewHolder(private val binding: HolderResourceBinding) : RecyclerView.ViewHolder(binding.root) {

        fun populateView(item: ResourceBody, itemClick: (resource: ResourceBody) -> Unit) {
            binding.apply {
                tvName.text = item.name
                tvContact.text = item.contact
                tvDate.text = item.date
                tvResourceType.text = mapResourceCodeToResourceString(item.resourceType ?: -1)
                tvUpvote.text = item.upVotes.toString()
                tvDownvote.text = item.downVotes.toString()
            }
            binding.root.setOnClickListener {
                itemClick(item)
            }

            //unchecking the downvote if upvote ischecked and downvote was checked
            binding.cbUpvote.setOnCheckedChangeListener { buttonView, isChecked ->
                if (isChecked && binding.cbDownvote.isChecked) {
                    binding.cbDownvote.isChecked = false
                    binding.tvDownvote.text = (item.downVotes).toString()
                }
                if(isChecked) {
                    binding.tvUpvote.text = (item.upVotes + 1).toString()
                }else {
                    binding.tvUpvote.text = item.upVotes.toString()
                }
            }
            //unchecking the upvote if downvote is checked and upvote was checked
            binding.cbDownvote.setOnCheckedChangeListener { buttonView, isChecked ->
                if(isChecked && binding.cbUpvote.isChecked) {
                    binding.cbUpvote.isChecked = false
                    binding.tvUpvote.text = (item.upVotes).toString()
                }
                if(isChecked) {
                    binding.tvDownvote.text = (item.downVotes + 1).toString()
                }else {
                    binding.tvDownvote.text = item.downVotes.toString()
                }
            }
        }
    }
}