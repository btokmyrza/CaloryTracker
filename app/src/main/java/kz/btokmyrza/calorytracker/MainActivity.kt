package kz.btokmyrza.calorytracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import dagger.hilt.android.AndroidEntryPoint
import kz.btokmyrza.calorytracker.core.navigation.Route
import kz.btokmyrza.calorytracker.core_ui.theme.CaloryTrackerTheme
import kz.btokmyrza.calorytracker.navigation.navigate
import kz.btokmyrza.calorytracker.navigation.screen
import kz.btokmyrza.calorytracker.onboarding_presentation.feature.activity_level.ActivityLevelPickerScreen
import kz.btokmyrza.calorytracker.onboarding_presentation.feature.age.AgeEnterScreen
import kz.btokmyrza.calorytracker.onboarding_presentation.feature.gender.GenderPickerScreen
import kz.btokmyrza.calorytracker.onboarding_presentation.feature.goal_type.GoalTypePickerScreen
import kz.btokmyrza.calorytracker.onboarding_presentation.feature.height.HeightEnterScreen
import kz.btokmyrza.calorytracker.onboarding_presentation.feature.nutrient_goal.NutrientGoalScreen
import kz.btokmyrza.calorytracker.onboarding_presentation.feature.weight.WeightEnterScreen
import kz.btokmyrza.calorytracker.onboarding_presentation.feature.welcome.WelcomeScreen
import kz.btokmyrza.calorytracker.tracker_presentation.feature.overview.TrackerOverviewScreen
import kz.btokmyrza.calorytracker.tracker_presentation.feature.search.TrackerSearchScreen

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
                ) { paddingValues ->
                    NavHost(
                        modifier = Modifier.padding(paddingValues),
                        navController = navController,
                        startDestination = Route.ONBOARDING_WELCOME,
                    ) {
                        screen(route = Route.ONBOARDING_WELCOME) {
                            WelcomeScreen(onNavigate = navController::navigate)
                        }
                        screen(route = Route.ONBOARDING_GENDER) {
                            GenderPickerScreen(onNavigate = navController::navigate)
                        }
                        screen(route = Route.ONBOARDING_AGE) {
                            AgeEnterScreen(
                                scaffoldState = scaffoldState,
                                onNavigate = navController::navigate,
                            )
                        }
                        screen(route = Route.ONBOARDING_HEIGHT) {
                            HeightEnterScreen(
                                scaffoldState = scaffoldState,
                                onNavigate = navController::navigate,
                            )
                        }
                        screen(route = Route.ONBOARDING_WEIGHT) {
                            WeightEnterScreen(
                                scaffoldState = scaffoldState,
                                onNavigate = navController::navigate,
                            )
                        }
                        screen(route = Route.ONBOARDING_ACTIVITY_LEVEL) {
                            ActivityLevelPickerScreen(onNavigate = navController::navigate)
                        }
                        screen(route = Route.ONBOARDING_GOAL) {
                            GoalTypePickerScreen(onNavigate = navController::navigate)
                        }
                        screen(route = Route.ONBOARDING_NUTRIENT_GOAL) {
                            NutrientGoalScreen(
                                scaffoldState = scaffoldState,
                                onNavigate = navController::navigate,
                            )
                        }
                        screen(route = Route.TRACKER_OVERVIEW) {
                            TrackerOverviewScreen(onNavigate = navController::navigate)
                        }
                        screen(
                            route = Route.TRACKER_SEARCH + Route.TRACKER_SEARCH_ARGS,
                            arguments = listOf(
                                navArgument(Route.TRACKER_SEARCH_ARG_MEAL_NAME) {
                                    type = NavType.StringType
                                },
                                navArgument(Route.TRACKER_SEARCH_ARG_DAY) {
                                    type = NavType.IntType
                                },
                                navArgument(Route.TRACKER_SEARCH_ARG_MONTH) {
                                    type = NavType.IntType
                                },
                                navArgument(Route.TRACKER_SEARCH_ARG_YEAR) {
                                    type = NavType.IntType
                                },
                            ),
                        ) {
                            TrackerSearchScreen(
                                scaffoldState = scaffoldState,
                                mealName = it.arguments?.getString(
                                    Route.TRACKER_SEARCH_ARG_MEAL_NAME,
                                ).orEmpty(),
                                dayOfMonth = it.arguments?.getInt(
                                    Route.TRACKER_SEARCH_ARG_DAY,
                                ) ?: 0,
                                month = it.arguments?.getInt(
                                    Route.TRACKER_SEARCH_ARG_MONTH,
                                ) ?: 0,
                                year = it.arguments?.getInt(
                                    Route.TRACKER_SEARCH_ARG_YEAR,
                                ) ?: 0,
                                onNavigateUp = navController::navigateUp,
                            )
                        }
                    }
                }
            }
        }
    }
}