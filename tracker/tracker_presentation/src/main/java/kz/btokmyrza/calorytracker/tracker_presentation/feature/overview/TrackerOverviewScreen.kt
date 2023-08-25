package kz.btokmyrza.calorytracker.tracker_presentation.feature.overview

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import kz.btokmyrza.calorytracker.core.util.UiEvent
import kz.btokmyrza.calorytracker.core_ui.theme.CaloryTrackerTheme
import kz.btokmyrza.calorytracker.core_ui.theme.LocalSpacing
import kz.btokmyrza.calorytracker.tracker_presentation.event.TrackerOverviewEvent
import kz.btokmyrza.calorytracker.tracker_presentation.feature.overview.components.ExpandableMeal
import kz.btokmyrza.calorytracker.tracker_presentation.feature.overview.components.header.NutrientsHeader
import kz.btokmyrza.calorytracker.tracker_presentation.model.MealDvo
import kz.btokmyrza.calorytracker.tracker_presentation.model.defaultMeals
import java.time.LocalDate

@Composable
fun TrackerOverviewScreen(
    viewModel: TrackerOverviewViewModel = hiltViewModel(),
    onNavigate: (UiEvent.Navigate) -> Unit,
) {
    val uiState = viewModel.uiState
    TrackerOverviewScreenContent(
        uiState = uiState,
        onToggleMealClick = { viewModel.onEvent(TrackerOverviewEvent.OnToggleMealClick(it)) },
    )
}

@Composable
private fun TrackerOverviewScreenContent(
    uiState: TrackerOverviewUiState,
    onToggleMealClick: (MealDvo) -> Unit,
) {
    val spacing = LocalSpacing.current
    val context = LocalContext.current
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = spacing.spaceMedium),
    ) {
        item {
            NutrientsHeader(uiState = uiState)
        }
        items(uiState.meals) { meal ->
            ExpandableMeal(
                modifier = Modifier.fillMaxWidth(),
                meal = meal,
                onToggleClick = { onToggleMealClick(meal) },
                content = {

                },
            )
        }
    }
}

@Preview(showBackground = true, heightDp = 900)
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
            onToggleMealClick = {},
        )
    }
}