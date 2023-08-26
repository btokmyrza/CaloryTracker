package kz.btokmyrza.calorytracker.tracker_presentation.feature.search

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import kz.btokmyrza.calorytracker.core.R
import kz.btokmyrza.calorytracker.core.util.UiEvent
import kz.btokmyrza.calorytracker.core_ui.theme.CaloryTrackerTheme
import kz.btokmyrza.calorytracker.core_ui.theme.LocalSpacing
import kz.btokmyrza.calorytracker.tracker_domain.model.MealType
import kz.btokmyrza.calorytracker.tracker_presentation.feature.search.components.SearchTextField
import kz.btokmyrza.calorytracker.tracker_presentation.feature.search.components.TrackableFoodItem
import kz.btokmyrza.calorytracker.tracker_presentation.model.TrackableFoodDvo
import java.time.LocalDate

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
        onQueryChange = { viewModel.onEvent(TrackerSearchEvent.OnQueryChange(query = it)) },
        onSearch = { viewModel.onEvent(TrackerSearchEvent.OnSearch) },
        onSearchFocusChange = {
            viewModel.onEvent(TrackerSearchEvent.OnSearchFocusChange(isFocused = it.isFocused))
        },
        onToggleTrackableFood = {
            viewModel.onEvent(TrackerSearchEvent.OnToggleTrackableFood(it.food))
        },
        onAmountForFoodChange = { trackableFoodItem, amount ->
            viewModel.onEvent(
                TrackerSearchEvent.OnAmountForFoodChange(
                    food = trackableFoodItem.food,
                    amount = amount,
                )
            )
        },
        onTrackFoodClick = { trackableFoodItem ->
            viewModel.onEvent(
                TrackerSearchEvent.OnTrackFoodClick(
                    food = trackableFoodItem.food,
                    mealType = MealType.fromString(mealName),
                    date = LocalDate.of(year, month, dayOfMonth)
                )
            )
        },
    )
}

@Composable
private fun TrackerSearchScreenContent(
    uiState: TrackerSearchUiState,
    mealName: String,
    onQueryChange: (String) -> Unit,
    onSearch: () -> Unit,
    onSearchFocusChange: (FocusState) -> Unit,
    onToggleTrackableFood: (TrackerSearchUiState.TrackableFoodItem) -> Unit,
    onAmountForFoodChange: (TrackerSearchUiState.TrackableFoodItem, String) -> Unit,
    onTrackFoodClick: (TrackerSearchUiState.TrackableFoodItem) -> Unit,
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
        Spacer(modifier = Modifier.height(spacing.spaceMedium))
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(uiState.trackableFoods) { trackableFoodItem ->
                TrackableFoodItem(
                    modifier = Modifier.fillMaxWidth(),
                    trackableFoodUiState = trackableFoodItem,
                    onClick = { onToggleTrackableFood(trackableFoodItem) },
                    onAmountChange = { amount ->
                        onAmountForFoodChange(trackableFoodItem, amount)
                    },
                    onTrack = { onTrackFoodClick(trackableFoodItem) },
                )
            }
        }
    }
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        when {
            uiState.isSearching -> CircularProgressIndicator()
            uiState.trackableFoods.isEmpty() -> {
                Text(
                    text = stringResource(id = R.string.no_results),
                    style = MaterialTheme.typography.body1,
                    textAlign = TextAlign.Center,
                )
            }
        }
    }
}

private val stubTrackableFoods = listOf(
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
    ),
    TrackerSearchUiState.TrackableFoodItem(
        food = TrackableFoodDvo(
            name = "Burger 2",
            imageUrl = null,
            caloriesPer100g = 40,
            carbsPer100g = 30,
            proteinPer100g = 30,
            fatPer100g = 30,
        ),
        isExpanded = false,
    ),
)

@Preview(showSystemUi = true)
@Composable
private fun TrackerSearchScreenPreview() {
    CaloryTrackerTheme {
        TrackerSearchScreenContent(
            uiState = TrackerSearchUiState(
                query = "Burger",
                isHintVisible = true,
                trackableFoods = stubTrackableFoods,
            ),
            mealName = "Breakfast",
            onQueryChange = {},
            onSearch = {},
            onSearchFocusChange = {},
            onToggleTrackableFood = {},
            onAmountForFoodChange = { _, _ -> },
            onTrackFoodClick = {},
        )
    }
}