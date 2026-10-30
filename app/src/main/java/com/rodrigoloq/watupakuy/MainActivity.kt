package com.rodrigoloq.watupakuy

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.rodrigoloq.watupakuy.feature.selector.SelectorView
import com.rodrigoloq.watupakuy.navigation.AppNavigation
import com.rodrigoloq.watupakuy.ui.theme.WatupakuyTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            WatupakuyTheme {
                AppNavigation(modifier = Modifier)
            }
        }
    }
}