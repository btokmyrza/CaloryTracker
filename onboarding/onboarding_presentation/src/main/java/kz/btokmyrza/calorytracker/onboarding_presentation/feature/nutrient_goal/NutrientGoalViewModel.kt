package kz.btokmyrza.calorytracker.onboarding_presentation.feature.nutrient_goal

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import kz.btokmyrza.calorytracker.core.navigation.Route
import kz.btokmyrza.calorytracker.core.preferences.Preferences
import kz.btokmyrza.calorytracker.core.use_case.FilterOutDigits
import kz.btokmyrza.calorytracker.core.util.UiEvent
import kz.btokmyrza.calorytracker.core.util.UiText
import kz.btokmyrza.calorytracker.onboarding_domain.use_case.ValidateNutrients
import javax.inject.Inject

@HiltViewModel
class NutrientGoalViewModel @Inject constructor(
    private val preferences: Preferences,
    private val filterOutDigits: FilterOutDigits,
    private val validateNutrients: ValidateNutrients,
) : ViewModel() {

    var uiState by mutableStateOf(NutrientGoalUiState())
        private set

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onEvent(event: NutrientGoalEvent) = when (event) {
        is NutrientGoalEvent.OnCarbRatioChanged -> onCarbRatioChanged(event.ratio)
        is NutrientGoalEvent.OnProteinRatioChanged -> onProteinRatioChanged(event.ratio)
        is NutrientGoalEvent.OnFatRatioChanged -> onFatRatioChanged(event.ratio)
        is NutrientGoalEvent.OnNextClicked -> onNextClicked()
        else -> Unit
    }

    private fun onCarbRatioChanged(ratio: String) {
        uiState = uiState.copy(carbsRatio = filterOutDigits(ratio))
    }

    private fun onProteinRatioChanged(ratio: String) {
        uiState = uiState.copy(proteinRatio = filterOutDigits(ratio))
    }

    private fun onFatRatioChanged(ratio: String) {
        uiState = uiState.copy(fatRatio = filterOutDigits(ratio))
    }

    private fun onNextClicked() {
        val result = validateNutrients(
            carbsRatioText = uiState.carbsRatio,
            proteinRatioText = uiState.proteinRatio,
            fatRatioText = uiState.fatRatio,
        )
        when (result) {
            is ValidateNutrients.Result.Success -> onNutrientsValidationSuccess(
                carbsRatio = result.carbsRatio,
                proteinRatio = result.proteinRatio,
                fatRatio = result.fatRatio,
            )
            is ValidateNutrients.Result.Error -> showErrorMessage(result.message)
            else -> Unit
        }
    }

    private fun onNutrientsValidationSuccess(
        carbsRatio: Float,
        proteinRatio: Float,
        fatRatio: Float,
    ) {
        saveRatios(carbsRatio = carbsRatio, proteinRatio = proteinRatio, fatRatio = fatRatio)
        navigateToTrackerOverview()
    }

    private fun saveRatios(
        carbsRatio: Float,
        proteinRatio: Float,
        fatRatio: Float,
    ) = preferences.run {
        saveCarbRatio(carbsRatio)
        saveProteinRatio(proteinRatio)
        saveFatRatio(fatRatio)
    }

    private fun navigateToTrackerOverview() = viewModelScope.launch {
        _uiEvent.send(UiEvent.Navigate(Route.TRACKER_OVERVIEW))
    }

    private fun showErrorMessage(message: UiText) = viewModelScope.launch {
        _uiEvent.send(UiEvent.ShowSnackbar(message = message))
    }
}