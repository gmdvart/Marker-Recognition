package com.example.buzidroidapplication.ui.recognize_feature.components

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.buzidroidapplication.domain.model.MarkerUiModel

class MarkerListViewController(
    private val recyclerView: RecyclerView,
    private val onItemClick: (MarkerUiModel) -> Unit = {}
) {
    private val adapter = MarkerListAdapter(onItemClick)

    init {
        val context = recyclerView.context
        recyclerView.layoutManager = GridLayoutManager(context, SPAN_COUNT)
        recyclerView.adapter = adapter
    }

    fun updateMarkerList(markerList: List<MarkerUiModel>) {
        adapter.submitList(markerList)
    }

    companion object {
        private const val SPAN_COUNT = 3
    }
}