package com.example.buzidroidapplication.ui.recognize_feature.screen

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.example.buzidroidapplication.R
import com.example.buzidroidapplication.appComponent
import com.example.buzidroidapplication.databinding.FragmentResultDialogBinding
import com.example.buzidroidapplication.ui.recognize_feature.Action
import com.example.buzidroidapplication.ui.recognize_feature.State
import com.example.buzidroidapplication.ui.recognize_feature.RecognizeFeatureViewModel
import com.example.buzidroidapplication.ui.utils.collectLatestState
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.setUpState()
    }

    private fun FragmentResultDialogBinding.setUpState() {
        collectLatestState(viewModel.state) { state ->
            if (state is State.Ready) {
                setUpResultWindow(state)
                setUpSendButton(state)
            }
        }
    }

    private fun FragmentResultDialogBinding.setUpResultWindow(readyState: State.Ready) {
        val recognitionState = readyState.recognition
        if (recognitionState is State.Recognition.Intent && recognitionState.recognizedMarker != null) {
            progressBar.visibility = View.INVISIBLE

            val isAnswerCorrect = recognitionState.recognizedMarker.id == readyState.currentMarker.id
                    || recognitionState.recognizedMarker.fullName == readyState.currentMarker.fullName

            if (isAnswerCorrect) {
                summaryTextView.setTextColor(Color.GREEN)
                summaryTextView.text = getText(R.string.correct_answer)
            } else {
                summaryTextView.setTextColor(Color.RED)
                summaryTextView.text = getText(R.string.incorrect_answer)
            }

            actualAnswerTextView.text = recognitionState.recognizedMarker.fullName
            expectedAnswerTextView.text = readyState.currentMarker.fullName

            resultLayout.visibility = View.VISIBLE
        }
    }

    private fun FragmentResultDialogBinding.setUpSendButton(readyState: State.Ready) {
        sendButton.isEnabled = readyState.isAbleToSendData
        sendButton.setOnClickListener {
            viewModel.onAction(action = Action.SendData)
            dismiss()
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = ResultDialogFragment()
        const val TAG = "ResultDialogFragment"
    }
}