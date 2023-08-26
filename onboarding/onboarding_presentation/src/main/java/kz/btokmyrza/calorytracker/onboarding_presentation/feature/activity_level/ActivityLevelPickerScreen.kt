package kz.btokmyrza.calorytracker.onboarding_presentation.feature.activity_level

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import kz.btokmyrza.calorytracker.core.R
import kz.btokmyrza.calorytracker.core.model.ActivityLevel
import kz.btokmyrza.calorytracker.core.util.UiEvent
import kz.btokmyrza.calorytracker.core_ui.theme.CaloryTrackerTheme
import kz.btokmyrza.calorytracker.core_ui.theme.LocalSpacing
import kz.btokmyrza.calorytracker.onboarding_presentation.common.components.ActionButton
import kz.btokmyrza.calorytracker.onboarding_presentation.common.components.SelectableButton

@Composable
fun ActivityLevelPickerScreen(
    viewModel: ActivityLevelPickerViewModel = hiltViewModel(),
    onNextClick: () -> Unit,
) {
    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is UiEvent.Success -> onNextClick()
                else -> Unit
            }
        }
    }
    ActivityLevelPickerScreenContent(
        selectedActivityLevel = viewModel.selectedActivityLevel,
        onActivityLevelClick = viewModel::onActivityLevelClicked,
        onNextClick = viewModel::onNextClick,
    )
}

@Composable
private fun ActivityLevelPickerScreenContent(
    selectedActivityLevel: ActivityLevel,
    onActivityLevelClick: (ActivityLevel) -> Unit,
    onNextClick: () -> Unit,
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
                text = stringResource(id = R.string.whats_your_activity_level),
                style = MaterialTheme.typography.h3,
            )
            Spacer(modifier = Modifier.height(spacing.spaceMedium))
            ActivityLevelButtons(
                selectedActivityLevel = selectedActivityLevel,
                onActivityLevelClick = onActivityLevelClick,
            )
        }
        ActionButton(
            text = stringResource(id = R.string.next),
            onClick = onNextClick,
            modifier = Modifier.align(Alignment.BottomEnd),
        )
    }
}

@Composable
private fun ActivityLevelButtons(
    selectedActivityLevel: ActivityLevel,
    onActivityLevelClick: (ActivityLevel) -> Unit,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly,
    ) {
        SelectableButton(
            text = stringResource(id = R.string.low),
            isSelected = selectedActivityLevel is ActivityLevel.Low,
            color = MaterialTheme.colors.primaryVariant,
            selectedTextColor = Color.White,
            onClick = { onActivityLevelClick(ActivityLevel.Low) },
            textStyle = MaterialTheme.typography.button.copy(
                fontWeight = FontWeight.Normal,
            ),
        )
        SelectableButton(
            text = stringResource(id = R.string.medium),
            isSelected = selectedActivityLevel is ActivityLevel.Medium,
            color = MaterialTheme.colors.primaryVariant,
            selectedTextColor = Color.White,
            onClick = { onActivityLevelClick(ActivityLevel.Medium) },
            textStyle = MaterialTheme.typography.button.copy(
                fontWeight = FontWeight.Normal,
            ),
        )
        SelectableButton(
            text = stringResource(id = R.string.high),
            isSelected = selectedActivityLevel is ActivityLevel.High,
            color = MaterialTheme.colors.primaryVariant,
            selectedTextColor = Color.White,
            onClick = { onActivityLevelClick(ActivityLevel.High) },
            textStyle = MaterialTheme.typography.button.copy(
                fontWeight = FontWeight.Normal,
            ),
        )
    }
}

@Preview(showSystemUi = true)
@Composable
private fun ActivityLevelPickerScreenPreview() {
    CaloryTrackerTheme {
        ActivityLevelPickerScreenContent(
            selectedActivityLevel = ActivityLevel.Medium,
            onActivityLevelClick = {},
            onNextClick = {},
        )
    }
}