package kz.btokmyrza.calorytracker.onboarding_presentation.feature.gender

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import kz.btokmyrza.calorytracker.core.model.Gender
import kz.btokmyrza.calorytracker.core.util.UiEvent
import kz.btokmyrza.calorytracker.core_ui.theme.CaloryTrackerTheme
import kz.btokmyrza.calorytracker.core_ui.theme.LocalSpacing
import kz.btokmyrza.calorytracker.onboarding_presentation.common.components.ActionButton
import kz.btokmyrza.calorytracker.onboarding_presentation.common.components.SelectableButton

@Composable
fun GenderPickerScreen(
    viewModel: GenderPickerViewModel = hiltViewModel(),
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
    GenderPickerScreenContent(
        selectedGender = viewModel.selectedGender,
        onGenderClick = viewModel::onGenderClicked,
        onNextClick = viewModel::onNextClick,
    )
}

@Composable
private fun GenderPickerScreenContent(
    selectedGender: Gender,
    onGenderClick: (Gender) -> Unit,
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
                text = stringResource(id = R.string.whats_your_gender),
                style = MaterialTheme.typography.h3,
            )
            Spacer(modifier = Modifier.height(spacing.spaceMedium))
            GenderButtons(
                selectedGender = selectedGender,
                onGenderClick = onGenderClick,
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
private fun GenderButtons(
    selectedGender: Gender,
    onGenderClick: (Gender) -> Unit,
) {
    Row {
        SelectableButton(
            text = stringResource(id = R.string.male),
            isSelected = selectedGender is Gender.Male,
            color = MaterialTheme.colors.primaryVariant,
            selectedTextColor = Color.White,
            onClick = { onGenderClick(Gender.Male) },
            textStyle = MaterialTheme.typography.button.copy(
                fontWeight = FontWeight.Normal,
            ),
        )
        Spacer(modifier = Modifier.width(LocalSpacing.current.spaceMedium))
        SelectableButton(
            text = stringResource(id = R.string.female),
            isSelected = selectedGender is Gender.Female,
            color = MaterialTheme.colors.primaryVariant,
            selectedTextColor = Color.White,
            onClick = { onGenderClick(Gender.Female) },
            textStyle = MaterialTheme.typography.button.copy(
                fontWeight = FontWeight.Normal,
            ),
        )
    }
}

@Preview(showSystemUi = true)
@Composable
private fun GenderPickerScreenPreview() {
    CaloryTrackerTheme {
        GenderPickerScreenContent(
            selectedGender = Gender.Male,
            onGenderClick = {},
            onNextClick = {},
        )
    }
}