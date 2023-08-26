package kz.btokmyrza.calorytracker.onboarding_presentation.feature.gender

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import kz.btokmyrza.calorytracker.core.model.Gender
import kz.btokmyrza.calorytracker.core.preferences.UserPreferences
import kz.btokmyrza.calorytracker.core.util.UiEvent
import javax.inject.Inject

@HiltViewModel
class GenderPickerViewModel @Inject constructor(
    private val userPreferences: UserPreferences,
) : ViewModel() {

    var selectedGender by mutableStateOf<Gender>(Gender.Male)
        private set

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onGenderClicked(gender: Gender) {
        selectedGender = gender
    }

    fun onNextClick() {
        viewModelScope.launch {
            userPreferences.saveGender(selectedGender)
            _uiEvent.send(UiEvent.Success)
        }
    }
}