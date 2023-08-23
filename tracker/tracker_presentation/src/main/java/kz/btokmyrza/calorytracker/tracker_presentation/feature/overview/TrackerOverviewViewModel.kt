package kz.btokmyrza.calorytracker.tracker_presentation.feature.overview

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import kz.btokmyrza.calorytracker.core.navigation.Route
import kz.btokmyrza.calorytracker.core.preferences.Preferences
import kz.btokmyrza.calorytracker.core.util.UiEvent
import kz.btokmyrza.calorytracker.tracker_domain.use_case.TrackerUseCases
import kz.btokmyrza.calorytracker.tracker_presentation.event.TrackerOverviewEvent
import kz.btokmyrza.calorytracker.tracker_presentation.mapper.TrackedFoodDvoMapper
import javax.inject.Inject

@HiltViewModel
class TrackerOverviewViewModel @Inject constructor(
    preferences: Preferences,
    private val trackerUseCases: TrackerUseCases,
    private val trackedFoodDvoMapper: TrackedFoodDvoMapper,
) : ViewModel() {

    var state by mutableStateOf(TrackerOverviewUiState())
        private set

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    private var getFoodsForDateJob: Job? = null

    init {
        preferences.saveShouldShowOnboarding(false)
    }

    fun onEvent(event: TrackerOverviewEvent) {
        when (event) {
            is TrackerOverviewEvent.OnAddFoodClick -> {
                viewModelScope.launch {
                    _uiEvent.send(
                        UiEvent.Navigate(
                            route = Route.TRACKER_SEARCH
                                    + "/${event.meal.mealType.name}"
                                    + "/${state.date.dayOfMonth}"
                                    + "/${state.date.monthValue}"
                                    + "/${state.date.year}",
                        )
                    )
                }
            }
            is TrackerOverviewEvent.OnDeleteTrackedFoodClick -> {
                viewModelScope.launch {
                    trackedFoodDvoMapper.toTrackedFood(event.trackedFood).also {
                        trackerUseCases.deleteTrackedFood(it)
                    }
                    refreshFoods()
                }
            }
            is TrackerOverviewEvent.OnNextDayClick -> viewModelScope.launch {
                state = state.copy(date = state.date.plusDays(1))
                refreshFoods()
            }
            is TrackerOverviewEvent.OnPreviousDayClick -> viewModelScope.launch {
                state = state.copy(date = state.date.minusDays(1))
                refreshFoods()
            }
            is TrackerOverviewEvent.OnToggleMealClick -> {
                state = state.copy(
                    meals = state.meals.map {
                        if (it.name == event.meal.name) {
                            it.copy(isExpanded = !it.isExpanded)
                        } else {
                            it
                        }
                    }
                )
            }
        }
    }

    private suspend fun refreshFoods() {
        getFoodsForDateJob?.cancel()
        getFoodsForDateJob = trackerUseCases.getFoodsForDate(state.date).onEach { foods ->
            val nutrientsResult = trackerUseCases.calculateMealNutrients(foods)
            state = state.copy(
                totalCarbs = nutrientsResult.totalCarbs,
                totalProtein = nutrientsResult.totalProtein,
                totalFat = nutrientsResult.totalFat,
                totalCalories = nutrientsResult.totalCalories,
                carbsGoal = nutrientsResult.carbsGoal,
                proteinGoal = nutrientsResult.proteinGoal,
                fatGoal = nutrientsResult.fatGoal,
                caloriesGoal = nutrientsResult.caloriesGoal,
                trackedFoods = foods.map(trackedFoodDvoMapper::toTrackedFoodDvo),
                meals = state.meals.map {
                    val nutrientsForMeal = nutrientsResult.mealNutrients[it.mealType]
                        ?: return@map it.copy(
                            carbs = 0,
                            protein = 0,
                            fat = 0,
                            calories = 0,
                        )
                    it.copy(
                        carbs = nutrientsForMeal.carbs,
                        protein = nutrientsForMeal.protein,
                        fat = nutrientsForMeal.fat,
                        calories = nutrientsForMeal.calories,
                    )
                }
            )
        }.launchIn(viewModelScope)
    }
}