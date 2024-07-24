package com.example.buzidroidapplication.ui.recognize_feature.screen

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.buzidroidapplication.R
import com.example.buzidroidapplication.appComponent
import com.example.buzidroidapplication.databinding.FragmentMarkerListScreenBinding
import com.example.buzidroidapplication.ui.recognize_feature.Action
import com.example.buzidroidapplication.ui.recognize_feature.State
import com.example.buzidroidapplication.ui.recognize_feature.RecognizeFeatureViewModel
import com.example.buzidroidapplication.ui.recognize_feature.components.MarkerListViewController
import com.example.buzidroidapplication.ui.utils.collectLatestState
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
            viewModel.onAction(action = Action.SelectById(it.id))
            findNavController().navigate(R.id.markerListScreenFragment_to_mainScreenFragment)
        }

        collectLatestState(viewModel.state) { state ->
            if (state is State.Ready)
                markerListViewController.updateMarkerList(state.markerList)
        }
    }

    private fun FragmentMarkerListScreenBinding.setUpToolBar() {
        toolBar.setNavigationOnClickListener {
            findNavController().navigate(R.id.markerListScreenFragment_to_mainScreenFragment)
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = MarkerListScreenFragment()
    }
}