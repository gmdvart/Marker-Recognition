package com.example.buzidroidapplication.ui.settings_feature.screen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.buzidroidapplication.databinding.FragmentSettingsScreenBinding

class SettingsScreenFragment : Fragment() {

    private lateinit var binding: FragmentSettingsScreenBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSettingsScreenBinding.inflate(inflater, container, false)
        return binding.root
    }



    companion object {
        @JvmStatic
        fun newInstance() = SettingsScreenFragment()
    }
}