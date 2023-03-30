package com.tesera.base.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.tesera.base.ui.screens.ApplicationScreen
import com.tesera.core.designsystem.theme.TeseraTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AppActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContent { TeseraTheme { ApplicationScreen() } }
    }
}
