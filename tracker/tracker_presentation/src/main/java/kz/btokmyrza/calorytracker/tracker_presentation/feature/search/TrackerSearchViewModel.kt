package kz.btokmyrza.calorytracker.tracker_presentation.feature.search

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import kz.btokmyrza.calorytracker.core.R
import kz.btokmyrza.calorytracker.core.use_case.FilterOutDigits
import kz.btokmyrza.calorytracker.core.util.UiEvent
import kz.btokmyrza.calorytracker.core.util.UiText
import kz.btokmyrza.calorytracker.tracker_domain.model.MealType
import kz.btokmyrza.calorytracker.tracker_domain.model.TrackableFood
import kz.btokmyrza.calorytracker.tracker_domain.use_case.TrackerUseCases
import kz.btokmyrza.calorytracker.tracker_presentation.mapper.TrackedFoodDvoMapper
import kz.btokmyrza.calorytracker.tracker_presentation.model.TrackableFoodDvo
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class TrackerSearchViewModel @Inject constructor(
    private val trackerUseCases: TrackerUseCases,
    private val filterOutDigits: FilterOutDigits,
    private val trackedFoodDvoMapper: TrackedFoodDvoMapper,
) : ViewModel() {

    var uiState by mutableStateOf(TrackerSearchUiState())
        private set

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onEvent(event: SearchEvent) = when (event) {
        is SearchEvent.OnQueryChange -> onQueryChange(query = event.query)
        is SearchEvent.OnAmountForFoodChange -> onAmountForFoodChange(
            food = event.food,
            amount = event.amount,
        )
        is SearchEvent.OnSearch -> onSearch()
        is SearchEvent.OnToggleTrackableFood -> onToggleTrackableFood(food = event.food)
        is SearchEvent.OnSearchFocusChange -> onSearchFocusChange(isFocused = event.isFocused)
        is SearchEvent.OnTrackFoodClick -> onTrackFoodClick(
            food = event.food,
            mealType = event.mealType,
            date = event.date,
        )
    }

    private fun onQueryChange(query: String) {
        uiState = uiState.copy(query = query)
    }

    private fun onAmountForFoodChange(food: TrackableFoodDvo, amount: String) {
        uiState = uiState.copy(
            trackableFood = uiState.trackableFood.map {
                if (it.food == food) it.copy(amount = filterOutDigits(amount)) else it
            },
        )
    }

    private fun onSearch() {
        viewModelScope.launch {
            uiState = uiState.copy(
                isSearching = true,
                trackableFood = emptyList(),
            )
            trackerUseCases
                .searchFood(query = uiState.query)
                .onSuccess { onSearchSuccess(foods = it) }
                .onFailure { onSearchFailure() }
        }
    }

    private fun onSearchSuccess(foods: List<TrackableFood>) {
        uiState = uiState.copy(
            trackableFood = foods.map {
                TrackerSearchUiState.TrackableFoodItem(
                    food = trackedFoodDvoMapper.toTrackableFoodDvo(it),
                )
            },
            isSearching = false,
            query = "",
        )
    }

    private suspend fun onSearchFailure() {
        uiState = uiState.copy(isSearching = false)
        _uiEvent.send(
            UiEvent.ShowSnackbar(
                message = UiText.StringResource(R.string.error_something_went_wrong),
            ),
        )
    }

    private fun onToggleTrackableFood(food: TrackableFoodDvo) {
        uiState = uiState.copy(
            trackableFood = uiState.trackableFood.map {
                if (it.food == food) it.copy(isExpanded = it.isExpanded.not()) else it
            },
        )
    }

    private fun onSearchFocusChange(isFocused: Boolean) {
        uiState = uiState.copy(isHintVisible = isFocused.not() && uiState.query.isBlank())
    }

    private fun onTrackFoodClick(food: TrackableFoodDvo, mealType: MealType, date: LocalDate) {
        viewModelScope.launch {
            trackerUseCases.trackFood(
                food = getTrackableFoodOrNull(food = food) ?: return@launch,
                amount = getTrackableFoodAmountOrNull(food = food) ?: return@launch,
                mealType = mealType,
                date = date,
            )
            _uiEvent.send(UiEvent.NavigateUp)
        }
    }

    private fun getTrackableFoodOrNull(food: TrackableFoodDvo): TrackableFood? =
        uiState.trackableFood
            .find { it.food == food }
            ?.food
            ?.let(trackedFoodDvoMapper::toTrackableFood)

    private fun getTrackableFoodAmountOrNull(food: TrackableFoodDvo): Int? = uiState.trackableFood
        .find { it.food == food }?.amount?.toIntOrNull()
}