package com.example.buzidroidapplication.ui

import android.content.pm.ActivityInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.buzidroidapplication.R
import com.example.buzidroidapplication.data.model.MarkerInfo
import com.example.buzidroidapplication.databinding.ActivityMainBinding
import com.example.buzidroidapplication.ui.recognize_feature.screen.MainScreenFragment
import kotlinx.serialization.json.Json

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Lock the orientation mode because there are still no solution
        // for persisting drawn PaintView picture
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
    }

    private fun testJsonEncoding() {
        val jsonString = application.assets.open("dsClassificator.json")
            .bufferedReader().use {
                it.readText()
            }

        val markers = Json.decodeFromString<Map<Int, MarkerInfo>>(jsonString)

        markers.forEach { mapEntry ->
            Log.d("${MainActivity::class.simpleName}", "${mapEntry.key} - ${mapEntry.value}")
        }
    }
}