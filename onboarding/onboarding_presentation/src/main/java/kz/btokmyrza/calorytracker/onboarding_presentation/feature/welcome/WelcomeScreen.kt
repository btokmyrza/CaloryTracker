package kz.btokmyrza.calorytracker.onboarding_presentation.feature.welcome

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import kz.btokmyrza.calorytracker.core.R
import kz.btokmyrza.calorytracker.core_ui.theme.CaloryTrackerTheme
import kz.btokmyrza.calorytracker.core_ui.theme.LocalSpacing
import kz.btokmyrza.calorytracker.onboarding_presentation.common.components.ActionButton

@Composable
fun WelcomeScreen(
    onNextClick: () -> Unit,
) {
    val spacing = LocalSpacing.current
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(spacing.spaceMedium),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = stringResource(id = R.string.welcome_text),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.h1,
        )
        Spacer(modifier = Modifier.height(spacing.spaceMedium))
        ActionButton(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            text = stringResource(id = R.string.next),
            onClick = { onNextClick() },
        )
    }
}

@Preview(showSystemUi = true)
@Composable
fun WelcomeScreenPreview() {
    CaloryTrackerTheme {
        WelcomeScreen(onNextClick = {})
    }
}