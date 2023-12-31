package kz.btokmyrza.calorytracker.tracker_presentation.feature.overview

import kz.btokmyrza.calorytracker.tracker_presentation.model.MealDvo
import kz.btokmyrza.calorytracker.tracker_presentation.model.TrackedFoodDvo

sealed class TrackerOverviewEvent {
    data object OnNextDayClick : TrackerOverviewEvent()
    data object OnPreviousDayClick : TrackerOverviewEvent()
    data class OnToggleMealClick(val meal: MealDvo) : TrackerOverviewEvent()
    data class OnDeleteTrackedFoodClick(val trackedFood: TrackedFoodDvo) : TrackerOverviewEvent()
}