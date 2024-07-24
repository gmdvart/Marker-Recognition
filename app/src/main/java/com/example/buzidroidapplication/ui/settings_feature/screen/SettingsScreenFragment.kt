package com.example.buzidroidapplication.ui.settings_feature.screen

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.buzidroidapplication.R
import com.example.buzidroidapplication.appComponent
import com.example.buzidroidapplication.databinding.FragmentSettingsScreenBinding
import com.example.buzidroidapplication.ui.settings_feature.Action
import com.example.buzidroidapplication.ui.settings_feature.State
import com.example.buzidroidapplication.ui.settings_feature.SettingsFeatureViewModel
import com.example.buzidroidapplication.ui.utils.collectLatestState
import javax.inject.Inject

class SettingsScreenFragment : Fragment() {

    private lateinit var binding: FragmentSettingsScreenBinding

    private val viewModel by activityViewModels<SettingsFeatureViewModel> { factory }
    @Inject
    lateinit var factory: SettingsFeatureViewModel.Factory

    override fun onAttach(context: Context) {
        context.appComponent.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSettingsScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.setUpState()
    }

    private fun FragmentSettingsScreenBinding.setUpState() {
        toolBar.setNavigationOnClickListener {
            findNavController().navigate(R.id.settingScreen_to_mainScreen)
        }
        userName.setOnClickListener {
            UserNameDialogFragment.newInstance().show(parentFragmentManager, UserNameDialogFragment.TAG)
        }
        predictionModeSwitch.setOnCheckedChangeListener { _, isChecked ->
            viewModel.onAction(action = Action.SwitchAppMode(isChecked))
        }
        networkModeSwitch.setOnCheckedChangeListener { _, isChecked ->
            viewModel.onAction(action = Action.SwitchNetworkMode(isChecked))
        }

        collectLatestState(viewModel.state) { state ->
            if (state is State.Ready) {
                progressBar.visibility = View.GONE

                predictionModeSwitch.isChecked = state.isPredictionModeEnabled
                networkModeSwitch.isChecked = state.isLocalNetworkModeEnabled
                userName.text = state.userName.ifBlank { getString(R.string.no_user_name) }

                settingsLayout.visibility = View.VISIBLE
            }
        }
    }


    companion object {
        @JvmStatic
        fun newInstance() = SettingsScreenFragment()
    }
}