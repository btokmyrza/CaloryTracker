package kz.btokmyrza.calorytracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import kz.btokmyrza.calorytracker.core.navigation.Route
import kz.btokmyrza.calorytracker.core_ui.theme.CaloryTrackerTheme
import kz.btokmyrza.calorytracker.navigation.navigate
import kz.btokmyrza.calorytracker.onboarding_presentation.feature.age.AgeEnterScreen
import kz.btokmyrza.calorytracker.onboarding_presentation.feature.gender.GenderPickerScreen
import kz.btokmyrza.calorytracker.onboarding_presentation.feature.height.HeightEnterScreen
import kz.btokmyrza.calorytracker.onboarding_presentation.feature.weight.WeightEnterScreen
import kz.btokmyrza.calorytracker.onboarding_presentation.feature.welcome.WelcomeScreen

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CaloryTrackerTheme {
                val navController = rememberNavController()
                val scaffoldState = rememberScaffoldState()
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    scaffoldState = scaffoldState,
                ) {
                    NavHost(
                        modifier = Modifier.padding(it),
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
                            AgeEnterScreen(
                                scaffoldState = scaffoldState,
                                onNavigate = navController::navigate,
                            )
                        }
                        composable(route = Route.ONBOARDING_HEIGHT) {
                            HeightEnterScreen(
                                scaffoldState = scaffoldState,
                                onNavigate = navController::navigate,
                            )
                        }
                        composable(route = Route.ONBOARDING_WEIGHT) {
                            WeightEnterScreen(
                                scaffoldState = scaffoldState,
                                onNavigate = navController::navigate,
                            )
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
}