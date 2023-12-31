package kz.btokmyrza.calorytracker.onboarding_presentation.feature.height

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
fun HeightEnterScreen(
    scaffoldState: ScaffoldState,
    viewModel: HeightEnterViewModel = hiltViewModel(),
    onNextClick: () -> Unit,
) {
    val context = LocalContext.current
    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is UiEvent.Success -> onNextClick()
                is UiEvent.ShowSnackbar -> scaffoldState.snackbarHostState.showSnackbar(
                    message = event.message.asString(context),
                )
                else -> Unit
            }
        }
    }
    HeightEnterScreenContent(
        height = viewModel.height,
        onHeightEnter = viewModel::onHeightChanged,
        onNextClick =viewModel::onNextClick,
    )
}

@Composable
private fun HeightEnterScreenContent(
    height: String,
    onHeightEnter: (String) -> Unit,
    onNextClick: () -> Unit,
) {
    val spacing = LocalSpacing.current
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(spacing.spaceLarge)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(id = R.string.whats_your_height),
                style = MaterialTheme.typography.h3
            )
            Spacer(modifier = Modifier.height(spacing.spaceMedium))
            UnitTextField(
                value = height,
                unitName = stringResource(id = R.string.cm),
                onValueChange = onHeightEnter
            )
        }
        ActionButton(
            modifier = Modifier.align(Alignment.BottomEnd),
            text = stringResource(id = R.string.next),
            onClick = onNextClick
        )
    }
}

@Preview(showSystemUi = true)
@Composable
private fun HeightEnterScreenPreview() {
    CaloryTrackerTheme {
        HeightEnterScreenContent(
            height = "171",
            onHeightEnter = {},
            onNextClick = {},
        )
    }
}