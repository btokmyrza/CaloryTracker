package kz.btokmyrza.calorytracker.onboarding_presentation.feature.weight

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
import kz.btokmyrza.calorytracker.core.navigation.Route
import kz.btokmyrza.calorytracker.core.preferences.Preferences
import kz.btokmyrza.calorytracker.core.util.UiEvent
import kz.btokmyrza.calorytracker.core.util.UiText
import javax.inject.Inject

private const val INITIAL_WEIGHT_VALUE = "75"
private const val MAX_WEIGHT_LENGTH = 5

@HiltViewModel
class WeightEnterViewModel @Inject constructor(
    private val preferences: Preferences,
) : ViewModel() {

    var weight by mutableStateOf(INITIAL_WEIGHT_VALUE)
        private set

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onWeightChanged(weight: String) {
        if (weight.length <= MAX_WEIGHT_LENGTH) {
            this.weight = weight
        }
    }

    fun onNextClick() {
        viewModelScope.launch {
            val weightNumber = weight.toFloatOrNull() ?: run {
                showErrorMessage()
                return@launch
            }
            preferences.saveWeight(weightNumber)
            _uiEvent.send(UiEvent.Navigate(Route.ONBOARDING_ACTIVITY))
        }
    }

    private suspend fun showErrorMessage() = _uiEvent.send(
        UiEvent.ShowSnackbar(
            message = UiText.StringResource(R.string.error_weight_cant_be_empty),
        ),
    )
}