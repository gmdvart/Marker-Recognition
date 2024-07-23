package com.example.buzidroidapplication.ui.recognize_feature.screen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.buzidroidapplication.databinding.FragmentMarkerListScreenBinding

class MarkerListScreenFragment : Fragment() {

    private lateinit var binding: FragmentMarkerListScreenBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMarkerListScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

    }

    companion object {
        @JvmStatic
        fun newInstance() = MarkerListScreenFragment()
    }
}