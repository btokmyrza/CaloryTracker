package kz.btokmyrza.calorytracker.tracker_presentation.feature.search

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import kz.btokmyrza.calorytracker.core.R
import kz.btokmyrza.calorytracker.core.util.UiEvent
import kz.btokmyrza.calorytracker.core_ui.theme.CaloryTrackerTheme
import kz.btokmyrza.calorytracker.core_ui.theme.LocalSpacing
import kz.btokmyrza.calorytracker.tracker_presentation.feature.search.components.SearchTextField
import kz.btokmyrza.calorytracker.tracker_presentation.model.TrackableFoodDvo

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun TrackerSearchScreen(
    scaffoldState: ScaffoldState,
    mealName: String,
    dayOfMonth: Int,
    month: Int,
    year: Int,
    viewModel: TrackerSearchViewModel = hiltViewModel(),
    onNavigateUp: () -> Unit
) {
    val uiState = viewModel.uiState
    val context = LocalContext.current
    val keyboardController = LocalSoftwareKeyboardController.current
    LaunchedEffect(key1 = keyboardController) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is UiEvent.ShowSnackbar -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = event.message.asString(context),
                    )
                    keyboardController?.hide()
                }
                is UiEvent.NavigateUp -> onNavigateUp()
                else -> Unit
            }
        }
    }
    TrackerSearchScreenContent(
        uiState = uiState,
        mealName = mealName,
        dayOfMonth = dayOfMonth,
        month = month,
        year = year,
        onQueryChange = { viewModel.onEvent(TrackerSearchEvent.OnQueryChange(query = it)) },
        onSearch = { viewModel.onEvent(TrackerSearchEvent.OnSearch) },
        onSearchFocusChange = {
            viewModel.onEvent(TrackerSearchEvent.OnSearchFocusChange(isFocused = it.isFocused))
        },
    )
}

@Composable
private fun TrackerSearchScreenContent(
    uiState: TrackerSearchUiState,
    mealName: String,
    dayOfMonth: Int,
    month: Int,
    year: Int,
    onQueryChange: (String) -> Unit,
    onSearch: () -> Unit,
    onSearchFocusChange: (FocusState) -> Unit,
) {
    val spacing = LocalSpacing.current
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(spacing.spaceMedium),
    ) {
        Text(
            text = stringResource(id = R.string.add_meal, mealName),
            style = MaterialTheme.typography.h2,
        )
        Spacer(modifier = Modifier.height(spacing.spaceMedium))
        SearchTextField(
            text = uiState.query,
            onValueChange = onQueryChange,
            onSearch = onSearch,
            onFocusChanged = onSearchFocusChange,
        )
    }
}

@Preview(showSystemUi = true)
@Composable
private fun TrackerSearchScreenPreview() {
    CaloryTrackerTheme {
        TrackerSearchScreenContent(
            uiState = TrackerSearchUiState(
                query = "Burger",
                isHintVisible = true,
                trackableFood = listOf(
                    TrackerSearchUiState.TrackableFoodItem(
                        food = TrackableFoodDvo(
                            name = "Burger",
                            imageUrl = null,
                            caloriesPer100g = 40,
                            carbsPer100g = 30,
                            proteinPer100g = 30,
                            fatPer100g = 30,
                        ),
                        isExpanded = true,
                    )
                )
            ),
            mealName = "Breakfast",
            dayOfMonth = 1,
            month = 1,
            year = 2023,
            onQueryChange = {},
            onSearch = {},
            onSearchFocusChange = {},
        )
    }
}