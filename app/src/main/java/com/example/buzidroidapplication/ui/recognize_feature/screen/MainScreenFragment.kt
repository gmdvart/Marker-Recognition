package com.example.buzidroidapplication.ui.recognize_feature.screen

import android.content.Context
import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import coil.load
import com.example.buzidroidapplication.R
import com.example.buzidroidapplication.appComponent
import com.example.buzidroidapplication.databinding.FragmentMainScreenBinding
import com.example.buzidroidapplication.ui.recognize_feature.RecognizeFeatureAction
import com.example.buzidroidapplication.ui.recognize_feature.RecognizeFeatureState
import com.example.buzidroidapplication.ui.recognize_feature.RecognizeFeatureViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainScreenFragment : Fragment(), ViewTreeObserver.OnGlobalLayoutListener {

    private lateinit var binding: FragmentMainScreenBinding

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
        binding = FragmentMainScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.setUpState()
    }

    private fun FragmentMainScreenBinding.setUpState() {
        setUpPainter()
        setUpCurrentMarkerDisplay()
        setUpRandomMarkerButton()
    }

    private fun FragmentMainScreenBinding.setUpPainter() {
        paintView.viewTreeObserver.addOnGlobalLayoutListener(this@MainScreenFragment)
        undoButton.setOnClickListener { paintView.undo() }
        clearButton.setOnClickListener { paintView.clear() }
    }

    private fun FragmentMainScreenBinding.setUpCurrentMarkerDisplay() {
        markerImageView.setOnClickListener {
            findNavController().navigate(R.id.mainScreen_to_markerListScreen)
        }

        viewModel.state.collectLatestState {
            if (it is RecognizeFeatureState.Ready)
                markerImageView.load(it.currentMarker.drawableId) { crossfade(true) }
        }
    }

    private fun FragmentMainScreenBinding.setUpRandomMarkerButton() {
        randomMarkImageButton.setOnClickListener {
            viewModel.onAction(action = RecognizeFeatureAction.SelectRandom)
        }
    }

    override fun onGlobalLayout() {
        with(binding) {
            paintView.viewTreeObserver.removeOnGlobalLayoutListener(this@MainScreenFragment)
            val width = paintCardView.measuredWidth
            val height = paintCardView.measuredHeight
            paintView.create(width, height)
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
        fun newInstance() = MainScreenFragment()
    }
}