package kz.btokmyrza.calorytracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import kz.btokmyrza.calorytracker.onboarding_presentation.welcome.WelcomeScreen
import kz.btokmyrza.calorytracker.core_ui.theme.CaloryTrackerTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CaloryTrackerTheme {
                WelcomeScreen()
            }
        }
    }
}