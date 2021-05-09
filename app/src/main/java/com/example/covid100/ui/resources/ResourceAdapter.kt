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
    private val itemClick : (id: String) -> Unit,
    private val likeHandle : (id: String, up: Int, down: Int, isLike: Boolean, toggle: Boolean) -> Unit
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
        val binding = HolderResourceBinding.inflate(inflater, parent, false)
        return ResourceViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ResourceViewHolder, position: Int) {
        val item = getItem(position)
        holder.populateView(item, itemClick, likeHandle)
    }

    class ResourceViewHolder(private val binding: HolderResourceBinding) : RecyclerView.ViewHolder(binding.root) {

        fun populateView(
            item: ResourceBody,
            itemClick: (id: String) -> Unit,
            likeHandle : (id: String, up: Int, down: Int, isLike: Boolean, toggle: Boolean) -> Unit
            ) {
            binding.apply {
                tvName.text = item.name
                tvContact.text = item.contact
                tvDate.text = item.dateToDisplay
                tvResourceType.text = mapResourceCodeToResourceString(item.resourceType ?: -1)
                tvUpvote.text = item.upVotes.toString()
                tvDownvote.text = item.downVotes.toString()
            }
            binding.root.setOnClickListener {
                item.id?.let { id ->
                    itemClick(id)
                }
            }

            //unchecking the downvote if upvote ischecked and downvote was checked
            binding.cbUpvote.setOnCheckedChangeListener { buttonView, isChecked ->
                val toggle = isChecked && binding.cbDownvote.isChecked

                item.id?.let {
                    likeHandle(item.id, item.upVotes, item.downVotes,true, toggle )
                }

                if (toggle) {
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
                val toggle = isChecked && binding.cbUpvote.isChecked

                item.id?.let {
                    likeHandle(item.id, item.upVotes, item.downVotes,false, toggle )
                }
                if(toggle) {
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