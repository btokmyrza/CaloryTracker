package kz.btokmyrza.calorytracker.onboarding_presentation.feature.nutrient_goal

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import kz.btokmyrza.calorytracker.core.R
import kz.btokmyrza.calorytracker.core.util.UiEvent
import kz.btokmyrza.calorytracker.core_ui.theme.CaloryTrackerTheme
import kz.btokmyrza.calorytracker.core_ui.theme.LocalSpacing
import kz.btokmyrza.calorytracker.onboarding_presentation.common.components.ActionButton
import kz.btokmyrza.calorytracker.onboarding_presentation.common.components.UnitTextField

@Composable
fun NutrientGoalScreen(
    scaffoldState: ScaffoldState,
    viewModel: NutrientGoalViewModel = hiltViewModel(),
    onNavigate: (UiEvent.Navigate) -> Unit,
) {
    val context = LocalContext.current
    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is UiEvent.Navigate -> onNavigate(event)
                is UiEvent.ShowSnackbar -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = event.message.asString(context),
                    )
                }
                else -> Unit
            }
        }
    }
    NutrientGoalScreenContent(
        uiState = viewModel.uiState,
        onCarbRatioChanged = { viewModel.onEvent(NutrientGoalEvent.OnCarbRatioChanged(it)) },
        onProteinRatioChanged = { viewModel.onEvent(NutrientGoalEvent.OnProteinRatioChanged(it)) },
        onFatRatioChanged = { viewModel.onEvent(NutrientGoalEvent.OnFatRatioChanged(it)) },
        onNextClicked = { viewModel.onEvent(NutrientGoalEvent.OnNextClicked) },
    )
}

@Composable
private fun NutrientGoalScreenContent(
    uiState: NutrientGoalUiState,
    onCarbRatioChanged: (String) -> Unit,
    onProteinRatioChanged: (String) -> Unit,
    onFatRatioChanged: (String) -> Unit,
    onNextClicked: () -> Unit,
) {
    val spacing = LocalSpacing.current
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(spacing.spaceLarge),
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = stringResource(id = R.string.what_are_your_nutrient_goals),
                style = MaterialTheme.typography.h3,
            )
            Spacer(modifier = Modifier.height(spacing.spaceMedium))
            UnitTextField(
                value = uiState.carbsRatio,
                unitName = stringResource(id = R.string.percent_carbs),
                onValueChange = onCarbRatioChanged,
            )
            Spacer(modifier = Modifier.height(spacing.spaceMedium))
            UnitTextField(
                value = uiState.proteinRatio,
                onValueChange = onProteinRatioChanged,
                unitName = stringResource(id = R.string.percent_proteins),
            )
            Spacer(modifier = Modifier.height(spacing.spaceMedium))
            UnitTextField(
                value = uiState.fatRatio,
                unitName = stringResource(id = R.string.percent_fats),
                onValueChange = onFatRatioChanged,
            )
        }
        ActionButton(
            modifier = Modifier.align(Alignment.BottomEnd),
            text = stringResource(id = R.string.next),
            onClick = onNextClicked,
        )
    }
}

@Preview(showSystemUi = true)
@Composable
private fun NutrientGoalScreenPreview() {
    CaloryTrackerTheme {
        NutrientGoalScreenContent(
            uiState = NutrientGoalUiState(),
            onCarbRatioChanged = {},
            onProteinRatioChanged = {},
            onFatRatioChanged = {},
            onNextClicked = {},
        )
    }
}