package com.example.buzidroidapplication.ui.recognize_feature.screen

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.example.buzidroidapplication.appComponent
import com.example.buzidroidapplication.databinding.FragmentResultDialogBinding
import com.example.buzidroidapplication.ui.recognize_feature.RecognizeFeatureViewModel
import javax.inject.Inject

class ResultDialogFragment : DialogFragment() {

    private lateinit var binding: FragmentResultDialogBinding

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
        binding = FragmentResultDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance() = ResultDialogFragment()
        const val TAG = "ResultDialogFragment"
    }
}