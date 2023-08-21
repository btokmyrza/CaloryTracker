package kz.btokmyrza.calorytracker.onboarding_presentation.age

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
import kz.btokmyrza.calorytracker.core.use_case.FilterOutDigits
import kz.btokmyrza.calorytracker.core.util.UiEvent
import kz.btokmyrza.calorytracker.core.util.UiText
import javax.inject.Inject

private const val INITIAL_AGE_VALUE = "20"

@HiltViewModel
class AgeEnterViewModel @Inject constructor(
    private val preferences: Preferences,
    private val filterOutDigits: FilterOutDigits,
) : ViewModel() {

    var age by mutableStateOf(INITIAL_AGE_VALUE)
        private set

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onAgeChanged(age: String) {
        if (age.length <= 3) {
            this.age = filterOutDigits(age)
        }
    }

    fun onNextClick() {
        viewModelScope.launch {
            val ageNumber = age.toIntOrNull() ?: run {
                showErrorMessage()
                return@launch
            }
            preferences.saveAge(ageNumber)
            _uiEvent.send(UiEvent.Navigate(Route.ONBOARDING_HEIGHT))
        }
    }

    private suspend fun showErrorMessage() = _uiEvent.send(
        UiEvent.ShowSnackbar(
            message = UiText.StringResource(R.string.error_age_cant_be_empty),
        ),
    )
}