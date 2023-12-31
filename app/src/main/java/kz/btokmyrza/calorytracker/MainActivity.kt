package kz.btokmyrza.calorytracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedContentTransitionScope
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
import kz.btokmyrza.calorytracker.core.preferences.UserPreferences
import kz.btokmyrza.calorytracker.core_ui.theme.CaloryTrackerTheme
import kz.btokmyrza.calorytracker.navigation.Route
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
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var userPreferences: UserPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val shouldShowOnboarding = userPreferences.loadShouldShowOnboarding()
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
                        startDestination = if (shouldShowOnboarding) {
                            Route.ONBOARDING_WELCOME
                        } else {
                            Route.TRACKER_OVERVIEW
                        },
                    ) {
                        screen(route = Route.ONBOARDING_WELCOME) {
                            WelcomeScreen(
                                onNextClick = { navController.navigate(Route.ONBOARDING_GENDER) },
                            )
                        }
                        screen(route = Route.ONBOARDING_GENDER) {
                            GenderPickerScreen(
                                onNextClick = { navController.navigate(Route.ONBOARDING_AGE) },
                            )
                        }
                        screen(route = Route.ONBOARDING_AGE) {
                            AgeEnterScreen(
                                scaffoldState = scaffoldState,
                                onNextClick = { navController.navigate(Route.ONBOARDING_HEIGHT) },
                            )
                        }
                        screen(route = Route.ONBOARDING_HEIGHT) {
                            HeightEnterScreen(
                                scaffoldState = scaffoldState,
                                onNextClick = { navController.navigate(Route.ONBOARDING_WEIGHT) },
                            )
                        }
                        screen(route = Route.ONBOARDING_WEIGHT) {
                            WeightEnterScreen(
                                scaffoldState = scaffoldState,
                                onNextClick = {
                                    navController.navigate(Route.ONBOARDING_ACTIVITY_LEVEL)
                                },
                            )
                        }
                        screen(route = Route.ONBOARDING_ACTIVITY_LEVEL) {
                            ActivityLevelPickerScreen(
                                onNextClick = {
                                    navController.navigate(Route.ONBOARDING_GOAL)
                                },
                            )
                        }
                        screen(route = Route.ONBOARDING_GOAL) {
                            GoalTypePickerScreen(
                                onNextClick = {
                                    navController.navigate(Route.ONBOARDING_NUTRIENT_GOAL)
                                },
                            )
                        }
                        screen(route = Route.ONBOARDING_NUTRIENT_GOAL) {
                            NutrientGoalScreen(
                                scaffoldState = scaffoldState,
                                onNextClick = {
                                    navController.navigate(Route.TRACKER_OVERVIEW)
                                },
                            )
                        }
                        screen(route = Route.TRACKER_OVERVIEW) {
                            TrackerOverviewScreen(
                                onNavigateToSearch = { mealName, day, month, year ->
                                    navController.navigate(
                                        Route.TRACKER_SEARCH
                                                + "/$mealName"
                                                + "/$day"
                                                + "/$month"
                                                + "/$year",
                                    )
                                },
                            )
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
                            direction = AnimatedContentTransitionScope.SlideDirection.Up,
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