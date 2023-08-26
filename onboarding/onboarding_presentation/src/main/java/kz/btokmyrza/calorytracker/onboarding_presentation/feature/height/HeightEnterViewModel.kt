package kz.btokmyrza.calorytracker.onboarding_presentation.feature.height

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
import kz.btokmyrza.calorytracker.core.preferences.UserPreferences
import kz.btokmyrza.calorytracker.core.use_case.FilterOutDigits
import kz.btokmyrza.calorytracker.core.util.UiEvent
import kz.btokmyrza.calorytracker.core.util.UiText
import javax.inject.Inject

private const val INITIAL_HEIGHT_VALUE = "171"
private const val MAX_HEIGHT_LENGTH = 3

@HiltViewModel
class HeightEnterViewModel @Inject constructor(
    private val userPreferences: UserPreferences,
    private val filterOutDigits: FilterOutDigits,
) : ViewModel() {

    var height by mutableStateOf(INITIAL_HEIGHT_VALUE)
        private set

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onHeightChanged(height: String) {
        if (height.length <= MAX_HEIGHT_LENGTH) {
            this.height = filterOutDigits(height)
        }
    }

    fun onNextClick() {
        viewModelScope.launch {
            val heightNumber = height.toIntOrNull() ?: run {
                showErrorMessage()
                return@launch
            }
            userPreferences.saveHeight(heightNumber)
            _uiEvent.send(UiEvent.Success)
        }
    }

    private suspend fun showErrorMessage() = _uiEvent.send(
        UiEvent.ShowSnackbar(
            message = UiText.StringResource(R.string.error_height_cant_be_empty),
        ),
    )
}