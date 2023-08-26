package kz.btokmyrza.calorytracker.tracker_presentation.feature.overview

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import kz.btokmyrza.calorytracker.core_ui.theme.CaloryTrackerTheme
import kz.btokmyrza.calorytracker.core_ui.theme.LocalSpacing
import kz.btokmyrza.calorytracker.tracker_presentation.feature.overview.components.DaySelector
import kz.btokmyrza.calorytracker.tracker_presentation.feature.overview.components.expandable.meal.ExpandableMealItem
import kz.btokmyrza.calorytracker.tracker_presentation.feature.overview.components.expandable.meal.ExpandableMealWidget
import kz.btokmyrza.calorytracker.tracker_presentation.feature.overview.components.header.NutrientsHeader
import kz.btokmyrza.calorytracker.tracker_presentation.model.MealDvo
import kz.btokmyrza.calorytracker.tracker_presentation.model.TrackedFoodDvo
import kz.btokmyrza.calorytracker.tracker_presentation.model.defaultMeals
import java.time.LocalDate

@Composable
fun TrackerOverviewScreen(
    viewModel: TrackerOverviewViewModel = hiltViewModel(),
    onNavigateToSearch: (String, Int, Int, Int) -> Unit,
) {
    val uiState = viewModel.uiState
    val context = LocalContext.current
    TrackerOverviewScreenContent(
        uiState = uiState,
        onPreviousDayClick = { viewModel.onEvent(TrackerOverviewEvent.OnPreviousDayClick) },
        onNextDayClick = { viewModel.onEvent(TrackerOverviewEvent.OnNextDayClick) },
        onToggleMealClick = { viewModel.onEvent(TrackerOverviewEvent.OnToggleMealClick(it)) },
        onDeleteTrackedFoodClick = { trackedFood ->
            viewModel.onEvent(TrackerOverviewEvent.OnDeleteTrackedFoodClick(trackedFood))
        },
        onAddFoodClick = { meal ->
            onNavigateToSearch(
                meal.name.asString(context),
                uiState.getDay(),
                uiState.getMonth(),
                uiState.getYear(),
            )
        },
    )
}

@Composable
private fun TrackerOverviewScreenContent(
    uiState: TrackerOverviewUiState,
    onPreviousDayClick: () -> Unit,
    onNextDayClick: () -> Unit,
    onToggleMealClick: (MealDvo) -> Unit,
    onDeleteTrackedFoodClick: (TrackedFoodDvo) -> Unit,
    onAddFoodClick: (MealDvo) -> Unit,
) {
    val spacing = LocalSpacing.current
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = spacing.spaceMedium),
    ) {
        item {
            NutrientsHeader(uiState = uiState)
            Spacer(modifier = Modifier.height(spacing.spaceMedium))
            DaySelector(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = spacing.spaceMedium),
                date = uiState.getFormattedDate(),
                onPreviousDayClick = onPreviousDayClick,
                onNextDayClick = onNextDayClick,
            )
            Spacer(modifier = Modifier.height(spacing.spaceMedium))
        }
        items(uiState.meals) { meal ->
            ExpandableMealWidget(
                modifier = Modifier.fillMaxWidth(),
                meal = meal,
                onToggleClick = { onToggleMealClick(meal) },
                content = {
                    ExpandableMealItem(
                        trackedFoods = uiState.trackedFoods,
                        meal = meal,
                        onDeleteTrackedFoodClick = onDeleteTrackedFoodClick,
                        onAddFoodClick = onAddFoodClick,
                    )
                },
            )
        }
    }
}

@Preview(showBackground = true, heightDp = 1000)
@Composable
private fun TrackerOverviewScreenPreview() {
    CaloryTrackerTheme {
        TrackerOverviewScreenContent(
            uiState = TrackerOverviewUiState(
                totalCarbs = 40,
                totalProtein = 30,
                totalFat = 30,
                totalCalories = 70,
                carbsGoal = 100,
                proteinGoal = 100,
                fatGoal = 100,
                caloriesGoal = 100,
                date = LocalDate.now(),
                trackedFoods = emptyList(),
                meals = defaultMeals,
            ),
            onPreviousDayClick = {},
            onNextDayClick = {},
            onToggleMealClick = {},
            onDeleteTrackedFoodClick = {},
            onAddFoodClick = {},
        )
    }
}