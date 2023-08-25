package kz.btokmyrza.calorytracker.tracker_presentation.feature.overview

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import kz.btokmyrza.calorytracker.core.R
import kz.btokmyrza.calorytracker.tracker_presentation.model.MealDvo
import kz.btokmyrza.calorytracker.tracker_presentation.model.TrackedFoodDvo
import kz.btokmyrza.calorytracker.tracker_presentation.model.defaultMeals
import java.time.LocalDate
import java.time.format.DateTimeFormatter

private const val DATE_FORMAT = "dd LLLL"

data class TrackerOverviewUiState(
    val totalCarbs: Int = 0,
    val totalProtein: Int = 0,
    val totalFat: Int = 0,
    val totalCalories: Int = 0,
    val carbsGoal: Int = 0,
    val proteinGoal: Int = 0,
    val fatGoal: Int = 0,
    val caloriesGoal: Int = 0,
    val date: LocalDate = LocalDate.now(),
    val trackedFoods: List<TrackedFoodDvo> = emptyList(),
    val meals: List<MealDvo> = defaultMeals,
) {

    @Composable
    fun getFormattedDate(): String {
        val today = LocalDate.now()
        return when (date) {
            today -> stringResource(id = R.string.today)
            today.minusDays(1) -> stringResource(id = R.string.yesterday)
            today.plusDays(1) -> stringResource(id = R.string.tomorrow)
            else -> DateTimeFormatter.ofPattern(DATE_FORMAT).format(date)
        }
    }
}