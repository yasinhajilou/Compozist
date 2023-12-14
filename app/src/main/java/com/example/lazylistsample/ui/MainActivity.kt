package com.example.lazylistsample.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.lazylistsample.AppNavHost
import com.example.lazylistsample.theme.LazyListSampleTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LazyListSampleTheme {
                AppNavHost()
            }
        }
    }
}