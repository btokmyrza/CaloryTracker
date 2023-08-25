package kz.btokmyrza.calorytracker.tracker_presentation.feature.search

import kz.btokmyrza.calorytracker.tracker_presentation.model.TrackableFoodDvo

data class TrackerSearchUiState(
    val query: String = "",
    val isHintVisible: Boolean = false,
    val isSearching: Boolean = false,
    val trackableFood: List<TrackableFoodItem> = emptyList(),
) {

    data class TrackableFoodItem(
        val food: TrackableFoodDvo,
        val isExpanded: Boolean = false,
        val amount: String = "",
    )
}