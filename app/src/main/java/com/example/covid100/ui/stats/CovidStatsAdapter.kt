package com.example.covid100.ui.stats

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.covid100.data.response.CovidStatSingleResponse
import com.example.covid100.databinding.HolderCovidCasesStatewiseBinding

class CovidStatsAdapter
    : ListAdapter<CovidStatSingleResponse, CovidStatsAdapter.CovidStatViewHolder>(
    diffUtil) {

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<CovidStatSingleResponse>() {
            override fun areItemsTheSame(
                oldItem: CovidStatSingleResponse,
                newItem: CovidStatSingleResponse
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: CovidStatSingleResponse,
                newItem: CovidStatSingleResponse
            ): Boolean {
                return oldItem.stateCode == newItem.stateCode
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CovidStatViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return CovidStatViewHolder(HolderCovidCasesStatewiseBinding.inflate(inflater, parent, false))
    }

    override fun onBindViewHolder(holder: CovidStatViewHolder, position: Int) {
        val stat = getItem(position)
        holder.populateViews(stat)
    }

    class CovidStatViewHolder(private val binding: HolderCovidCasesStatewiseBinding) : RecyclerView.ViewHolder(binding.root) {
        fun populateViews(stat: CovidStatSingleResponse) {
            binding.apply {
                tvState.text = stat.state
                tvConfirmed.text = stat.confirmed
                tvActive.text = stat.active
                tvRecovered.text = stat.recovered
                tvDeath.text = stat.deaths
            }
        }
    }
}