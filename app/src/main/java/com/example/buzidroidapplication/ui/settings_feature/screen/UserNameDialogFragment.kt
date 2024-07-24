package com.example.buzidroidapplication.ui.settings_feature.screen

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.example.buzidroidapplication.appComponent
import com.example.buzidroidapplication.databinding.FragmentUserNameDialogBinding
import com.example.buzidroidapplication.ui.settings_feature.Action
import com.example.buzidroidapplication.ui.settings_feature.State
import com.example.buzidroidapplication.ui.settings_feature.SettingsFeatureViewModel
import com.example.buzidroidapplication.ui.utils.collectLatestState
import javax.inject.Inject

class UserNameDialogFragment : DialogFragment() {

    private lateinit var binding: FragmentUserNameDialogBinding

    private val viewModel by activityViewModels<SettingsFeatureViewModel> { factory }
    @Inject
    lateinit var factory: SettingsFeatureViewModel.Factory

    private lateinit var oldUserName: String

    override fun onAttach(context: Context) {
        context.appComponent.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentUserNameDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.setUpState()
    }

    private fun FragmentUserNameDialogBinding.setUpState() {
        collectLatestState(viewModel.state) { state ->
            if (state is State.Ready) {
                oldUserName = state.userName
                enterButton.isEnabled = state.userName.isNotBlank() && state.userName != oldUserName
                userNameEditText.setText(state.userName)
            }
        }

        cancelButton.setOnClickListener { dismiss() }
        enterButton.setOnClickListener {
            viewModel.onAction(action = Action.EnterName(userNameEditText.text.toString()))
            dismiss()
        }
        userNameEditText.addTextChangedListener(
            object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
                override fun afterTextChanged(editable: Editable?) {
                    val inputText = editable.toString()
                    enterButton.isEnabled = inputText.isNotBlank() && inputText != oldUserName
                }
            }
        )
    }

    companion object {
        fun newInstance() = UserNameDialogFragment()
        const val TAG = "UserNameDialogFragment"
    }
}