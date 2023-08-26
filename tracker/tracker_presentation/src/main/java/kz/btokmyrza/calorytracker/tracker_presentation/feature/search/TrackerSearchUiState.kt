package kz.btokmyrza.calorytracker.tracker_presentation.feature.search

import androidx.compose.ui.text.input.ImeAction
import kz.btokmyrza.calorytracker.tracker_presentation.model.TrackableFoodDvo

data class TrackerSearchUiState(
    val query: String = "",
    val isHintVisible: Boolean = false,
    val isSearching: Boolean = false,
    val trackableFoods: List<TrackableFoodItem> = emptyList(),
) {

    data class TrackableFoodItem(
        val food: TrackableFoodDvo,
        val isExpanded: Boolean = false,
        val amount: String = "",
    ) {

        fun getKeyboardAction(): ImeAction =
            if (amount.isNotBlank()) ImeAction.Done else ImeAction.Default
    }
}