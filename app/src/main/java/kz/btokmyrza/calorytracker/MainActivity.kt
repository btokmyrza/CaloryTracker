package kz.btokmyrza.calorytracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import kz.btokmyrza.calorytracker.core.navigation.Route
import kz.btokmyrza.calorytracker.core_ui.theme.CaloryTrackerTheme
import kz.btokmyrza.calorytracker.navigation.navigate
import kz.btokmyrza.calorytracker.onboarding_presentation.gender.GenderPickerScreen
import kz.btokmyrza.calorytracker.onboarding_presentation.welcome.WelcomeScreen

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CaloryTrackerTheme {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = Route.ONBOARDING_WELCOME,
                ) {
                    composable(route = Route.ONBOARDING_WELCOME) {
                        WelcomeScreen(onNavigate = navController::navigate)
                    }
                    composable(route = Route.ONBOARDING_GENDER) {
                        GenderPickerScreen(onNavigate = navController::navigate)
                    }
                    composable(route = Route.ONBOARDING_AGE) {

                    }
                    composable(route = Route.ONBOARDING_HEIGHT) {

                    }
                    composable(route = Route.ONBOARDING_WEIGHT) {

                    }
                    composable(route = Route.ONBOARDING_NUTRIENT_GOAL) {

                    }
                    composable(route = Route.ONBOARDING_ACTIVITY) {

                    }
                    composable(route = Route.ONBOARDING_GOAL) {

                    }
                    composable(route = Route.TRACKER_OVERVIEW) {

                    }
                    composable(route = Route.TRACKER_SEARCH) {

                    }
                }
            }
        }
    }
}