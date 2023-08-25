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
import kz.btokmyrza.calorytracker.core.util.UiText
import kz.btokmyrza.calorytracker.tracker_domain.use_case.TrackerUseCases
import kz.btokmyrza.calorytracker.tracker_presentation.mapper.TrackedFoodDvoMapper
import kz.btokmyrza.calorytracker.tracker_presentation.model.TrackedFoodDvo
import javax.inject.Inject

@HiltViewModel
class TrackerOverviewViewModel @Inject constructor(
    preferences: Preferences,
    private val trackerUseCases: TrackerUseCases,
    private val trackedFoodDvoMapper: TrackedFoodDvoMapper,
) : ViewModel() {

    var uiState by mutableStateOf(TrackerOverviewUiState())
        private set

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    private var getFoodsForDateJob: Job? = null

    init {
        preferences.saveShouldShowOnboarding(false)
    }

    fun onEvent(event: TrackerOverviewEvent) = when (event) {
        is TrackerOverviewEvent.OnAddFoodClick -> onAddFoodClick(
            mealTypeName = event.meal.getMealTypeName(),
        )
        is TrackerOverviewEvent.OnDeleteTrackedFoodClick -> onDeleteTrackedFoodClick(
            trackedFood = event.trackedFood,
        )
        is TrackerOverviewEvent.OnNextDayClick -> onNextDayClick()
        is TrackerOverviewEvent.OnPreviousDayClick -> onPreviousDayClick()
        is TrackerOverviewEvent.OnToggleMealClick -> onToggleMealClick(mealName = event.meal.name)
    }

    private fun onAddFoodClick(mealTypeName: String) {
        viewModelScope.launch {
            _uiEvent.send(
                UiEvent.Navigate(
                    route = Route.TRACKER_SEARCH
                            + "/${mealTypeName}"
                            + "/${uiState.getDay()}"
                            + "/${uiState.getMonth()}"
                            + "/${uiState.getYear()}",
                ),
            )
        }
    }

    private fun onDeleteTrackedFoodClick(trackedFood: TrackedFoodDvo) {
        viewModelScope.launch {
            trackedFoodDvoMapper.toTrackedFood(trackedFood).also {
                trackerUseCases.deleteTrackedFood(it)
            }
            refreshFoods()
        }
    }

    private fun onNextDayClick() {
        viewModelScope.launch {
            uiState = uiState.copy(date = uiState.date.plusDays(1))
            refreshFoods()
        }
    }

    private fun onPreviousDayClick() {
        viewModelScope.launch {
            uiState = uiState.copy(date = uiState.date.minusDays(1))
            refreshFoods()
        }
    }

    private fun onToggleMealClick(mealName: UiText) {
        uiState = uiState.copy(
            meals = uiState.meals.map {
                if (it.name == mealName) {
                    it.copy(isExpanded = it.isExpanded.not())
                } else {
                    it
                }
            },
        )
    }

    private suspend fun refreshFoods() {
        getFoodsForDateJob?.cancel()
        getFoodsForDateJob = trackerUseCases.getFoodsForDate(uiState.date).onEach { foods ->
            val nutrientsResult = trackerUseCases.calculateMealNutrients(foods)
            uiState = uiState.copy(
                totalCarbs = nutrientsResult.totalCarbs,
                totalProtein = nutrientsResult.totalProtein,
                totalFat = nutrientsResult.totalFat,
                totalCalories = nutrientsResult.totalCalories,
                carbsGoal = nutrientsResult.carbsGoal,
                proteinGoal = nutrientsResult.proteinGoal,
                fatGoal = nutrientsResult.fatGoal,
                caloriesGoal = nutrientsResult.caloriesGoal,
                trackedFoods = foods.map(trackedFoodDvoMapper::toTrackedFoodDvo),
                meals = uiState.meals.map {
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
                },
            )
        }.launchIn(viewModelScope)
    }
}