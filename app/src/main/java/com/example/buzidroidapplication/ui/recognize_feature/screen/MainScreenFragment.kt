package com.example.buzidroidapplication.ui.recognize_feature.screen

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import androidx.fragment.app.activityViewModels
import com.example.buzidroidapplication.appComponent
import com.example.buzidroidapplication.databinding.FragmentMainScreenBinding
import com.example.buzidroidapplication.ui.recognize_feature.RecognizeFeatureViewModel
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
    }

    private fun FragmentMainScreenBinding.setUpPainter() {
        paintView.viewTreeObserver.addOnGlobalLayoutListener(this@MainScreenFragment)
        undoButton.setOnClickListener { paintView.undo() }
        clearButton.setOnClickListener { paintView.clear() }
    }

    override fun onGlobalLayout() {
        with(binding) {
            paintView.viewTreeObserver.removeOnGlobalLayoutListener(this@MainScreenFragment)
            val width = paintCardView.measuredWidth
            val height = paintCardView.measuredHeight
            paintView.create(width, height)
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = MainScreenFragment()
    }
}