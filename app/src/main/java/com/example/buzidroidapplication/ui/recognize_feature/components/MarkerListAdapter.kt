package com.example.buzidroidapplication.ui.recognize_feature.components

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.buzidroidapplication.databinding.MarkerListItemBinding
import com.example.buzidroidapplication.domain.model.MarkerUiModel

class MarkerListAdapter(
    private val onItemClick: (MarkerUiModel) -> Unit = {}
) : ListAdapter<MarkerUiModel, MarkerListAdapter.MarkerListItemViewHolder>(MarkerListItemDiffCallback()) {

    override fun onCreateViewHolder(viewGroup: ViewGroup, position: Int): MarkerListItemViewHolder {
        val inflater = LayoutInflater.from(viewGroup.context)
        val binding = MarkerListItemBinding.inflate(inflater, viewGroup, false)
        return MarkerListItemViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: MarkerListItemViewHolder, position: Int) {
        val markerUiModel = getItem(position)
        viewHolder.bind(markerUiModel)
    }

    inner class MarkerListItemViewHolder(
        private val binding: MarkerListItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(markerUiModel: MarkerUiModel) {
            itemView.setOnClickListener { onItemClick(markerUiModel) }
            with(binding) {
                markerImageView.setImageResource(markerUiModel.drawableId)
                markerImageView.contentDescription = markerUiModel.fullName
            }
        }
    }

    private class MarkerListItemDiffCallback : DiffUtil.ItemCallback<MarkerUiModel>() {
        override fun areItemsTheSame(model0: MarkerUiModel, model1: MarkerUiModel): Boolean {
            return model0.id == model1.id
        }

        override fun areContentsTheSame(model0: MarkerUiModel, model1: MarkerUiModel): Boolean {
            return model0 == model1
        }
    }
}