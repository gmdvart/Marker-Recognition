package com.example.buzidroidapplication.ui.recognize_feature.screen

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.example.buzidroidapplication.appComponent
import com.example.buzidroidapplication.databinding.FragmentMarkerListScreenBinding
import com.example.buzidroidapplication.ui.recognize_feature.RecognizeFeatureAction
import com.example.buzidroidapplication.ui.recognize_feature.RecognizeFeatureState
import com.example.buzidroidapplication.ui.recognize_feature.RecognizeFeatureViewModel
import com.example.buzidroidapplication.ui.recognize_feature.components.MarkerListViewController
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

class MarkerListScreenFragment : Fragment() {

    private lateinit var binding: FragmentMarkerListScreenBinding

    private val viewModel by activityViewModels<RecognizeFeatureViewModel> { factory }
    @Inject
    lateinit var factory: RecognizeFeatureViewModel.Factory

    override fun onAttach(context: Context) {
        context.appComponent.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMarkerListScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.setUpState()
    }

    private fun FragmentMarkerListScreenBinding.setUpState() {
        setUpToolBar()

        val markerListViewController = MarkerListViewController(recyclerView) {
            viewModel.onAction(action = RecognizeFeatureAction.SelectById(it.id))
        }
        viewModel.state.collectLatestState {
            if (it is RecognizeFeatureState.Ready)
                markerListViewController.updateMarkerList(it.markerList)
        }
    }

    private fun FragmentMarkerListScreenBinding.setUpToolBar() {
        toolBar.setNavigationOnClickListener {

        }
    }

    private fun <T> Flow<T>.collectLatestState(callback: (T) -> Unit) {
        lifecycleScope.launch {
            collectLatest {
                callback(it)
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = MarkerListScreenFragment()
    }
}