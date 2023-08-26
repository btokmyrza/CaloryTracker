package kz.btokmyrza.calorytracker.tracker_presentation.feature.search

import kz.btokmyrza.calorytracker.tracker_domain.model.MealType
import kz.btokmyrza.calorytracker.tracker_presentation.model.TrackableFoodDvo
import java.time.LocalDate

sealed class TrackerSearchEvent {

    data class OnQueryChange(val query: String) : TrackerSearchEvent()

    data object OnSearch : TrackerSearchEvent()

    data class OnToggleTrackableFood(val food: TrackableFoodDvo) : TrackerSearchEvent()

    data class OnAmountForFoodChange(
        val food: TrackableFoodDvo,
        val amount: String,
    ) : TrackerSearchEvent()

    data class OnTrackFoodClick(
        val food: TrackableFoodDvo,
        val mealType: MealType,
        val date: LocalDate,
    ) : TrackerSearchEvent()

    data class OnSearchFocusChange(val isFocused: Boolean) : TrackerSearchEvent()
}