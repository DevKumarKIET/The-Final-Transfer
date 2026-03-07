package com.example.thefinaltransfer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.NavController
import com.example.thefinaltransfer.presentation.navigation.TFTNavigationSystem
import com.example.thefinaltransfer.ui.theme.TheFinalTransferTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TheFinalTransferTheme {
                TFTNavigationSystem()
            }
        }
    }
}
