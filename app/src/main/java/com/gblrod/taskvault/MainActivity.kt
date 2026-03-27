package com.gblrod.taskvault

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.gblrod.taskvault.screen.TaskScreen
import com.gblrod.taskvault.ui.theme.TaskVaultTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TaskVaultTheme {
                TaskScreen()
            }
        }
    }
}