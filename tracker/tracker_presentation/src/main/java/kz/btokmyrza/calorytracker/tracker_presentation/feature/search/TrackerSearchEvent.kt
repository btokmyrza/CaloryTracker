package kz.btokmyrza.calorytracker.tracker_presentation.feature.search

import kz.btokmyrza.calorytracker.tracker_domain.model.MealType
import kz.btokmyrza.calorytracker.tracker_presentation.model.TrackableFoodDvo
import java.time.LocalDate

sealed class SearchEvent {

    data class OnQueryChange(val query: String) : SearchEvent()

    data object OnSearch : SearchEvent()

    data class OnToggleTrackableFood(val food: TrackableFoodDvo) : SearchEvent()

    data class OnAmountForFoodChange(
        val food: TrackableFoodDvo,
        val amount: String,
    ) : SearchEvent()

    data class OnTrackFoodClick(
        val food: TrackableFoodDvo,
        val mealType: MealType,
        val date: LocalDate,
    ) : SearchEvent()

    data class OnSearchFocusChange(val isFocused: Boolean) : SearchEvent()
}